/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.domain.populator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.mifosplatform.infrastructure.dataimport.data.group.CompactGroup;
import org.mifosplatform.infrastructure.dataimport.domain.handler.Result;
import org.mifosplatform.infrastructure.core.data.PaginationParameters;
import org.mifosplatform.organisation.office.data.OfficeData;
import org.mifosplatform.organisation.office.service.OfficeReadPlatformService;
import org.mifosplatform.portfolio.group.data.GroupGeneralData;
import org.mifosplatform.portfolio.group.service.GroupReadPlatformService;
import org.mifosplatform.infrastructure.core.service.SearchParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GroupSheetPopulator extends AbstractWorkbookPopulator {

    private static final Logger logger = LoggerFactory.getLogger(GroupSheetPopulator.class);

    private final OfficeReadPlatformService officeReadPlatformService;
    private final GroupReadPlatformService groupReadPlatformService;

    private List<CompactGroup> groups;
    private ArrayList<String> officeNames;

    private Map<String, ArrayList<String>> officeToGroups;
    private Map<String, Integer> groupNameToGroupId;
    private Map<Integer, Integer[]> officeNameToBeginEndIndexesOfGroups;

    private static final int OFFICE_NAME_COL = 0;
    private static final int GROUP_NAME_COL = 1;
    private static final int GROUP_ID_COL = 2;

    public GroupSheetPopulator(final OfficeReadPlatformService officeReadPlatformService,
            final GroupReadPlatformService groupReadPlatformService) {

        this.officeReadPlatformService = officeReadPlatformService;
        this.groupReadPlatformService = groupReadPlatformService;
    }

    @Override
    public Result downloadAndParse() {

        Result result = new Result();
        try {

            final PaginationParameters parameters = PaginationParameters.instance(null, null, -1, null, null);
            final SearchParameters searchParameters = SearchParameters.forGroups(null, null, null, null, null, null, null, -1, null, null, null);

            final Collection<GroupGeneralData> groupsCollection = this.groupReadPlatformService.retrieveAll(searchParameters, parameters);

            groups = new ArrayList<>();
            groupNameToGroupId = new HashMap<>();

            for (GroupGeneralData aGroupGeneralData : groupsCollection) {

                if (aGroupGeneralData.isActive()) {

                    groups.add(new CompactGroup(aGroupGeneralData));
                    groupNameToGroupId.put(aGroupGeneralData.getName().trim(), aGroupGeneralData.getId().intValue());
                }
            }

            Collection<OfficeData> officesCollection = this.officeReadPlatformService.retrieveAllOffices(false, null);

            officeNames = new ArrayList<>();

            for (OfficeData aOfficeData : officesCollection) {

                officeNames.add(aOfficeData.name());
            }

        } catch (Exception e) {
            result.addError(e.getMessage());
            logger.error(e.getMessage());
        }
        return result;
    }

    @Override
    public Result populate(Workbook workbook) {

        Result result = new Result();
        Sheet groupSheet = workbook.createSheet("Groups");
        setLayout(groupSheet);

        try {
            setOfficeToGroupsMap();
            populateGroupsByOfficeName(groupSheet);
            groupSheet.protectSheet("");

        } catch (Exception e) {
            result.addError(e.getMessage());
            logger.error(e.getMessage());
        }
        return result;
    }

    private void setOfficeToGroupsMap() {

        officeToGroups = new HashMap<>();
        for (CompactGroup group : groups) {
            add(group.getOfficeName().trim().replaceAll("[ )(]", "_"), group.getName().trim());
        }
    }

    private void add(String key, String value) {

        ArrayList<String> values = officeToGroups.get(key);

        if (values == null) {
            values = new ArrayList<>();
        }
        values.add(value);
        officeToGroups.put(key, values);
    }

    private void populateGroupsByOfficeName(Sheet groupSheet) {

        int rowIndex = 1, officeIndex = 0, startIndex = 1;
        officeNameToBeginEndIndexesOfGroups = new HashMap<>();

        Row row = groupSheet.createRow(rowIndex);
        for (String officeName : officeNames) {

            startIndex = rowIndex + 1;
            writeString(OFFICE_NAME_COL, row, officeName);
            ArrayList<String> groupsList = new ArrayList<>();

            if (officeToGroups.containsKey(officeName)) groupsList = officeToGroups.get(officeName);

            if (!groupsList.isEmpty()) {

                for (String groupName : groupsList) {
                    writeString(GROUP_NAME_COL, row, groupName);
                    writeInt(GROUP_ID_COL, row, groupNameToGroupId.get(groupName));
                    row = groupSheet.createRow(++rowIndex);
                }
                officeNameToBeginEndIndexesOfGroups.put(officeIndex++, new Integer[] { startIndex, rowIndex });

            } else {
                officeNameToBeginEndIndexesOfGroups.put(officeIndex++, new Integer[] { startIndex, rowIndex + 1 });
            }
        }
    }

    private void setLayout(Sheet worksheet) {

        Row rowHeader = worksheet.createRow(0);
        rowHeader.setHeight((short) 500);

        for (int colIndex = 0; colIndex <= 10; colIndex++)
            worksheet.setColumnWidth(colIndex, 6000);

        writeString(OFFICE_NAME_COL, rowHeader, "Office Names");
        writeString(GROUP_NAME_COL, rowHeader, "Group Names");
        writeString(GROUP_ID_COL, rowHeader, "Group ID");
    }

    public Integer getGroupsSize() {
        return groups.size();
    }

    public List<CompactGroup> getGroups() {
        return groups;
    }

    public Map<Integer, Integer[]> getOfficeNameToBeginEndIndexesOfGroups() {
        return officeNameToBeginEndIndexesOfGroups;
    }

    public Map<String, Integer> getGroupNameToGroupId() {
        return groupNameToGroupId;
    }

    public Map<String, ArrayList<String>> getOfficeToGroups() {
        return officeToGroups;
    }

}
