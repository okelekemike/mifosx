/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.domain.handler.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.mifosplatform.commands.domain.CommandWrapper;
import org.mifosplatform.commands.service.CommandWrapperBuilder;
import org.mifosplatform.commands.service.PortfolioCommandSourceWritePlatformService;
import org.mifosplatform.infrastructure.dataimport.data.user.User;
import org.mifosplatform.infrastructure.dataimport.domain.handler.AbstractDataImportHandler;
import org.mifosplatform.infrastructure.dataimport.domain.handler.Result;
import org.mifosplatform.infrastructure.dataimport.services.utils.StringUtils;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class UserDataImportHandler extends AbstractDataImportHandler {

    private final Logger logger = LoggerFactory.getLogger(UserDataImportHandler.class);

    private final int OFFICE_NAME_COL = 0;
    private final int STAFF_NAME_COL = 1;
    private final int ROLE_NAME_COL = 2;
    private final int USER_NAME_COL = 3;
    private final int FIRST_NAME_COL = 4;
    private final int LAST_NAME_COL = 5;
    private final int EMAIL_COL = 6;
    private final int PASSWORD_COL = 7;
    private final int REPEAT_PASSWORD_COL = 8;
    private final int STATUS_COL = 10;
    private final int FAILURE_COL = 12;
    @SuppressWarnings("unused")
    private final int LOOKUP_OFFICE_NAME_COL = 254;
    @SuppressWarnings("unused")
    private final int LOOKUP_OFFICE_OPENING_DATE_COL = 255;

    private final String SheetName;

    private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;

    private final Workbook workbook;

    private List<User> users;

    public UserDataImportHandler(Workbook workbook, final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService) {

        this.workbook = workbook;
        this.commandsSourceWritePlatformService = commandsSourceWritePlatformService;
        users = new ArrayList<>();

        SheetName = "UsersForImport";
    }

    @Override
    public Result parse() {

        Result result = new Result();
        Sheet centersSheet = workbook.getSheet(SheetName);
        Integer noOfEntries = getNumberOfRows(centersSheet, 0);

        for (int rowIndex = 1; rowIndex < noOfEntries; rowIndex++) {
            Row row;
            try {
                row = centersSheet.getRow(rowIndex);
                if (isNotImported(row, STATUS_COL)) {
                    users.add(parseAsUser(row));
                }
            } catch (Exception e) {
                logger.error("row = " + rowIndex, e);
                result.addError("Row = " + rowIndex + " , " + e.getMessage());
            }
        }
        return result;
    }

    private User parseAsUser(Row row) {

        String officeName = readAsString(OFFICE_NAME_COL, row);
        Integer officeIdInt = getIdByName(workbook.getSheet("Offices"), officeName);
        Long officeId = officeIdInt != 0 && officeIdInt != null ? officeIdInt.longValue() : null;

        String staffName = readAsString(STAFF_NAME_COL, row);
        Integer staffIdInt = getIdByName(workbook.getSheet("Staff"), staffName);
        Long staffId = staffIdInt != 0 && staffIdInt != null ? staffIdInt.longValue() : null;

        String userRoleName = readAsString(ROLE_NAME_COL, row);
        Integer userRoleIdInt = getIdByName(workbook.getSheet("AvailableRoles"), userRoleName);
        String userRoleId = userRoleIdInt != 0 && userRoleIdInt != null ? userRoleIdInt.toString() : null;

        String userName = readAsString(USER_NAME_COL, row);
        String firstName = readAsString(FIRST_NAME_COL, row);
        String lastName = readAsString(LAST_NAME_COL, row);
        String email = readAsString(EMAIL_COL, row);
        String password = readAsString(PASSWORD_COL, row);
        String repeatPassword = readAsString(REPEAT_PASSWORD_COL, row);

        String status = readAsString(STATUS_COL, row);

        if (StringUtils.isBlank(officeName)) { throw new IllegalArgumentException("Office Name is blank"); }

        return new User(officeId, staffId, userRoleId, userName, firstName, lastName, email, password, repeatPassword, status,
                row.getRowNum());
    }

    @Override
    public Result upload() {

        Result result = new Result();
        Sheet userSheet = workbook.getSheet(SheetName);
        @SuppressWarnings("unused")
        Long userId = null;

        for (int i = 0; i < users.size(); i++) {

            Row row = userSheet.getRow(users.get(i).getRowIndex());
            Cell errorReportCell = row.createCell(FAILURE_COL);
            Cell statusCell = row.createCell(STATUS_COL);

            try {

                @SuppressWarnings("unused")
                String status = users.get(i).getStatus();

                userId = uploadUser(i).getSubResourceId();

                statusCell.setCellValue("Imported");
                statusCell.setCellStyle(getCellStyle(workbook, IndexedColors.LIGHT_GREEN));

            } catch (RuntimeException e) {

                String message = parseStatus(e.getMessage());
                String status = "";

                statusCell.setCellValue(status + " failed.");
                statusCell.setCellStyle(getCellStyle(workbook, IndexedColors.RED));

                errorReportCell.setCellValue(message);
                result.addError("Row = " + users.get(i).getRowIndex() + " ," + message);
            }
        }
        setReportHeaders(userSheet);
        return result;
    }

    private CommandProcessingResult uploadUser(int rowIndex) {

        String payload = new Gson().toJson(users.get(rowIndex));
        logger.info(payload);

        final CommandWrapper commandRequest = new CommandWrapperBuilder().createUser().withJson(payload).build();
        final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);

        return result;
    }

    private void setReportHeaders(Sheet sheet) {

        writeString(STATUS_COL, sheet.getRow(0), "Status");
        writeString(FAILURE_COL, sheet.getRow(0), "Failure Report");
    }

    public List<User> getUsers() {
        return this.users;
    }
}
