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
import org.mifosplatform.infrastructure.dataimport.data.Office;
import org.mifosplatform.infrastructure.dataimport.data.Personnel;
import org.mifosplatform.infrastructure.dataimport.domain.handler.Result;
import org.mifosplatform.organisation.office.data.OfficeData;
import org.mifosplatform.organisation.office.service.OfficeReadPlatformService;
import org.mifosplatform.organisation.staff.data.StaffData;
import org.mifosplatform.organisation.staff.service.StaffReadPlatformService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonnelSheetPopulator extends AbstractWorkbookPopulator {

    private static final Logger logger = LoggerFactory.getLogger(PersonnelSheetPopulator.class);

    private final Boolean onlyLoanOfficers;

    private List<Personnel> personnel;
    private List<Office> offices;

    private Map<String, ArrayList<String>> officeToPersonnel;
    private Map<String, Integer> staffNameToStaffId;

    private Map<Integer, Integer[]> officeNameToBeginEndIndexesOfStaff;
    private Map<Integer, String> officeIdToOfficeName;

    private static final int OFFICE_NAME_COL = 0;
    private static final int STAFF_NAME_COL = 1;
    private static final int STAFF_ID_COL = 2;

    private final OfficeReadPlatformService officeReadPlatformService;
    private final StaffReadPlatformService staffReadPlatformService;

    public PersonnelSheetPopulator(Boolean onlyLoanOfficers, final OfficeReadPlatformService officeReadPlatformService,
            final StaffReadPlatformService staffReadPlatformService) {

        this.onlyLoanOfficers = onlyLoanOfficers;
        this.officeReadPlatformService = officeReadPlatformService;
        this.staffReadPlatformService = staffReadPlatformService;
    }

    @Override
    public Result downloadAndParse() {

        Result result = new Result();

        try {

            final Collection<StaffData> staffCollection = this.staffReadPlatformService.retrieveAllStaff(null, null, onlyLoanOfficers,
                    "active");

            final Collection<OfficeData> officesCollection = this.officeReadPlatformService.retrieveAllOffices(false, null);

            staffNameToStaffId = new HashMap<>();
            personnel = new ArrayList<>();
            offices = new ArrayList<>();

            for (StaffData aStaffData : staffCollection) {

                Personnel person = new Personnel(aStaffData);
                if (!onlyLoanOfficers)
                    personnel.add(person);
                else {
                    if (person.isLoanOfficer()) personnel.add(person);
                }
                staffNameToStaffId.put(person.getName(), person.getId());

            }

            offices = new ArrayList<>();
            officeIdToOfficeName = new HashMap<>();

            for (OfficeData aOfficeData : officesCollection) {

                Office office = new Office(aOfficeData);
                officeIdToOfficeName.put(office.getId(), office.getName().trim().replaceAll("[ )(]", "_"));
                offices.add(office);
            }

        } catch (RuntimeException re) {
            result.addError(re.getMessage());
            logger.error(re.getMessage());
        }
        return result;
    }

    @Override
    public Result populate(Workbook workbook) {

        Result result = new Result();
        Sheet staffSheet = workbook.createSheet("Staff");
        setLayout(staffSheet);

        try {
            setOfficeToPersonnelMap();
            populateStaffByOfficeName(staffSheet);
            staffSheet.protectSheet("");

        } catch (RuntimeException re) {
            result.addError(re.getMessage());
            logger.error(re.getMessage());
        }
        return result;
    }

    private void populateStaffByOfficeName(Sheet staffSheet) {

        int rowIndex = 1, startIndex = 1, officeIndex = 0;
        officeNameToBeginEndIndexesOfStaff = new HashMap<>();
        Row row = staffSheet.createRow(rowIndex);

        for (Office office : offices) {
            startIndex = rowIndex + 1;
            writeString(OFFICE_NAME_COL, row, office.getName().trim().replaceAll("[ )(]", "_"));

            ArrayList<String> fullStaffList = getStaffList(office.getHierarchy());

            if (!fullStaffList.isEmpty()) {

                for (String staffName : fullStaffList) {
                    int staffId = staffNameToStaffId.get(staffName);
                    writeString(STAFF_NAME_COL, row, staffName);
                    writeInt(STAFF_ID_COL, row, staffId);
                    row = staffSheet.createRow(++rowIndex);
                }
                officeNameToBeginEndIndexesOfStaff.put(officeIndex++, new Integer[] { startIndex, rowIndex });

            } else
                officeIndex++;
        }
    }

    private void setOfficeToPersonnelMap() {

        officeToPersonnel = new HashMap<>();
        for (Personnel person : personnel) {
            add(person.getOfficeName().trim().replaceAll("[ )(]", "_"), person.getName().trim());
        }
    }

    private void add(String key, String value) {

        ArrayList<String> values = officeToPersonnel.get(key);
        if (values == null) {
            values = new ArrayList<>();
        }
        values.add(value);
        officeToPersonnel.put(key, values);
    }

    private ArrayList<String> getStaffList(String hierarchy) {

        ArrayList<String> fullStaffList = new ArrayList<>();
        Integer hierarchyLength = hierarchy.length();
        String[] officeIds = hierarchy.substring(1, hierarchyLength).split("\\.");
        String headOffice = offices.get(0).getName().trim().replaceAll("[ )(]", "_");

        if (officeToPersonnel.containsKey(headOffice)) fullStaffList.addAll(officeToPersonnel.get(headOffice));

        if (officeIds[0].isEmpty()) return fullStaffList;

        for (int i = 0; i < officeIds.length; i++) {

            String officeName = getOfficeNameFromOfficeId(Integer.parseInt(officeIds[i]));

            if (officeToPersonnel.containsKey(officeName)) fullStaffList.addAll(officeToPersonnel.get(officeName));
        }

        return fullStaffList;
    }

    private String getOfficeNameFromOfficeId(Integer officeId) {

        return officeIdToOfficeName.get(officeId);
    }

    private void setLayout(Sheet worksheet) {

        for (Integer i = 0; i < 3; i++)
            worksheet.setColumnWidth(i, 6000);

        Row rowHeader = worksheet.createRow(0);
        rowHeader.setHeight((short) 500);

        writeString(OFFICE_NAME_COL, rowHeader, "Office Name");
        writeString(STAFF_NAME_COL, rowHeader, "Staff List");
        writeString(STAFF_ID_COL, rowHeader, "Staff ID");
    }

    public List<Personnel> getPersonnel() {
        return personnel;
    }

    public List<Office> getOffices() {
        return offices;
    }

    public Map<String, ArrayList<String>> getOfficeToPersonnel() {
        return officeToPersonnel;
    }

    public Map<Integer, Integer[]> getOfficeNameToBeginEndIndexesOfStaff() {
        return officeNameToBeginEndIndexesOfStaff;
    }

    public Map<Integer, String> getOfficeIdToOfficeName() {
        return officeIdToOfficeName;
    }

    public Map<String, Integer> getStaffNameToStaffId() {
        return staffNameToStaffId;
    }
}
