/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.domain.populator;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.mifosplatform.infrastructure.dataimport.domain.handler.Result;
import org.mifosplatform.useradministration.data.RoleData;
import org.mifosplatform.useradministration.service.RoleReadPlatformService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserRoleSheetPopulator extends AbstractWorkbookPopulator {

    private static final Logger logger = LoggerFactory.getLogger(UserRoleSheetPopulator.class);

    private final RoleReadPlatformService roleReadPlatformService;

    private Collection<RoleData> userRoles;
    private ArrayList<String> roleNames;

    private final int ID_COL = 0;
    private final int CODE_COL = 1;

    private final String SHEET_NAME;

    protected UserRoleSheetPopulator(final RoleReadPlatformService roleReadPlatformService) {

        this.roleReadPlatformService = roleReadPlatformService;

        SHEET_NAME = "AvailableRoles";
        roleNames = new ArrayList<>();
    }

    @Override
    public Result downloadAndParse() {

        Result result = new Result();
        try {
            userRoles = roleReadPlatformService.retrieveAll();

            for (RoleData roleData : userRoles) {

                roleNames.add(roleData.getName().trim().replaceAll("[ )(]", "_"));
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
        try {
            int rowIndex = 1;

            Sheet userRoleSheet = workbook.createSheet(SHEET_NAME);
            setLayout(userRoleSheet);

            populateRoles(userRoleSheet, rowIndex);
            userRoleSheet.protectSheet("");

        } catch (Exception e) {
            result.addError(e.getMessage());
            logger.error(e.getMessage());
        }
        return result;
    }

    private void setLayout(Sheet worksheet) {

        worksheet.setColumnWidth(ID_COL, 2000);
        worksheet.setColumnWidth(CODE_COL, 7000);

        Row rowHeader = worksheet.createRow(0);
        rowHeader.setHeight((short) 500);

        writeString(ID_COL, rowHeader, "ID");
        writeString(CODE_COL, rowHeader, "Value");
    }

    private void populateRoles(Sheet codeSheet, int rowIndex) {

        for (RoleData roleData : userRoles) {

            Row row = codeSheet.createRow(rowIndex);
            writeInt(ID_COL, row, roleData.getId().intValue());
            writeString(CODE_COL, row, roleData.getName().trim().replaceAll("[ )(]", "_"));
            rowIndex++;

        }
    }

    public ArrayList<String> getUserRoleNames() {
        return this.roleNames;
    }

}
