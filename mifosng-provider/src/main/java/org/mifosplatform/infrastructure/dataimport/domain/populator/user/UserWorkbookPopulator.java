/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.domain.populator.user;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.poi.hssf.usermodel.HSSFDataValidationHelper;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.mifosplatform.infrastructure.dataimport.domain.handler.Result;
import org.mifosplatform.infrastructure.dataimport.domain.populator.AbstractWorkbookPopulator;
import org.mifosplatform.infrastructure.dataimport.domain.populator.OfficeSheetPopulator;
import org.mifosplatform.infrastructure.dataimport.domain.populator.PersonnelSheetPopulator;
import org.mifosplatform.infrastructure.dataimport.domain.populator.UserRoleSheetPopulator;

public class UserWorkbookPopulator extends AbstractWorkbookPopulator {

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
    private final int LOOKUP_OFFICE_NAME_COL = 254;
    private final int LOOKUP_OFFICE_OPENING_DATE_COL = 255;

    private final OfficeSheetPopulator officeSheetPopulator;
    private final PersonnelSheetPopulator personnelSheetPopulator;
    private final UserRoleSheetPopulator userRoleSheetPopulator;
    private final String SheetName;

    public UserWorkbookPopulator(final OfficeSheetPopulator officeSheetPopulator, final PersonnelSheetPopulator personnelSheetPopulator,
            final UserRoleSheetPopulator userRoleSheetPopulator) {

        this.officeSheetPopulator = officeSheetPopulator;
        this.personnelSheetPopulator = personnelSheetPopulator;
        this.userRoleSheetPopulator = userRoleSheetPopulator;

        SheetName = "UsersForImport";
    }

    @Override
    public Result downloadAndParse() {

        Result result = officeSheetPopulator.downloadAndParse();
        if (result.isSuccess()) result = personnelSheetPopulator.downloadAndParse();
        if (result.isSuccess()) result = userRoleSheetPopulator.downloadAndParse();

        return result;
    }

    @Override
    public Result populate(Workbook workbook) {

        Sheet userSheet = workbook.createSheet(SheetName);

        Result result = officeSheetPopulator.populate(workbook);
        if (result.isSuccess()) result = personnelSheetPopulator.populate(workbook);
        if (result.isSuccess()) result = userRoleSheetPopulator.populate(workbook);

        setLayout(userSheet);
        setLookupTable(userSheet);

        if (result.isSuccess()) result = setRules(userSheet);
        return result;
    }

    private void setLayout(Sheet worksheet) {

        Row rowHeader = worksheet.createRow(0);
        rowHeader.setHeight((short) 500);

        worksheet.setColumnWidth(OFFICE_NAME_COL, 5000);
        worksheet.setColumnWidth(STAFF_NAME_COL, 5000);
        worksheet.setColumnWidth(ROLE_NAME_COL, 5000);
        worksheet.setColumnWidth(USER_NAME_COL, 5000);
        worksheet.setColumnWidth(FIRST_NAME_COL, 5000);
        worksheet.setColumnWidth(LAST_NAME_COL, 5000);
        worksheet.setColumnWidth(EMAIL_COL, 5000);
        worksheet.setColumnWidth(PASSWORD_COL, 5000);
        worksheet.setColumnWidth(REPEAT_PASSWORD_COL, 5000);
        worksheet.setColumnWidth(STATUS_COL, 6000);
        worksheet.setColumnWidth(FAILURE_COL, 6000);
        worksheet.setColumnWidth(LOOKUP_OFFICE_NAME_COL, 6000);
        worksheet.setColumnWidth(LOOKUP_OFFICE_OPENING_DATE_COL, 4000);

        writeString(OFFICE_NAME_COL, rowHeader, "Office Name*");
        writeString(STAFF_NAME_COL, rowHeader, "Staff Name");
        writeString(ROLE_NAME_COL, rowHeader, "Role Name*");
        writeString(USER_NAME_COL, rowHeader, "User Name*");
        writeString(FIRST_NAME_COL, rowHeader, "First Name*");
        writeString(LAST_NAME_COL, rowHeader, "Last Name*");
        writeString(EMAIL_COL, rowHeader, "Email*");
        writeString(PASSWORD_COL, rowHeader, "Password*");
        writeString(REPEAT_PASSWORD_COL, rowHeader, "Re-Password*");
        writeString(LOOKUP_OFFICE_OPENING_DATE_COL, rowHeader, "Opening Date");
    }

    private void setLookupTable(Sheet centerSheet) {

        setOfficeDateLookupTable(centerSheet, officeSheetPopulator.getOffices(), LOOKUP_OFFICE_NAME_COL, LOOKUP_OFFICE_OPENING_DATE_COL);
    }

    private Result setRules(Sheet worksheet) {

        Result result = new Result();

        try {
            CellRangeAddressList officeNameRange = new CellRangeAddressList(1, SpreadsheetVersion.EXCEL97.getLastRowIndex(),
                    OFFICE_NAME_COL, OFFICE_NAME_COL);
            CellRangeAddressList staffNameRange = new CellRangeAddressList(1, SpreadsheetVersion.EXCEL97.getLastRowIndex(), STAFF_NAME_COL,
                    STAFF_NAME_COL);
            CellRangeAddressList userRoleRange = new CellRangeAddressList(1, SpreadsheetVersion.EXCEL97.getLastRowIndex(), ROLE_NAME_COL,
                    ROLE_NAME_COL);

            DataValidationHelper validationHelper = new HSSFDataValidationHelper((HSSFSheet) worksheet);
            ArrayList<String> officeNames = new ArrayList<>(Arrays.asList(officeSheetPopulator.getOfficeNames()));
            setNames(worksheet, officeNames);

            DataValidationConstraint officeNameConstraint = validationHelper.createFormulaListConstraint("Office");
            DataValidationConstraint staffNameConstraint = validationHelper
                    .createFormulaListConstraint("INDIRECT(CONCATENATE(\"Staff_\",$A1))");
            DataValidationConstraint userRoleConstraint = validationHelper.createFormulaListConstraint("UserRole");

            DataValidation officeValidation = validationHelper.createValidation(officeNameConstraint, officeNameRange);
            DataValidation staffValidation = validationHelper.createValidation(staffNameConstraint, staffNameRange);
            DataValidation userRoleValidation = validationHelper.createValidation(userRoleConstraint, userRoleRange);

            worksheet.addValidationData(officeValidation);
            worksheet.addValidationData(staffValidation);
            worksheet.addValidationData(userRoleValidation);

        } catch (RuntimeException re) {
            result.addError(re.getMessage());
        }
        return result;
    }

    private void setNames(Sheet worksheet, ArrayList<String> officeNames) {

        Workbook centerWorkbook = worksheet.getWorkbook();
        Name officeGroup = centerWorkbook.createName();
        officeGroup.setNameName("Office");
        officeGroup.setRefersToFormula("Offices!$B$2:$B$" + (officeNames.size() + 1));

        for (Integer i = 0; i < officeNames.size(); i++) {

            Integer[] officeNameToBeginEndIndexesOfStaff = personnelSheetPopulator.getOfficeNameToBeginEndIndexesOfStaff().get(i);

            Name loanOfficerName = centerWorkbook.createName();

            if (officeNameToBeginEndIndexesOfStaff != null) {
                loanOfficerName.setNameName("Staff_" + officeNames.get(i));
                loanOfficerName.setRefersToFormula("Staff!$B$" + officeNameToBeginEndIndexesOfStaff[0] + ":$B$"
                        + officeNameToBeginEndIndexesOfStaff[1]);
            }
        }

        Name userRoles = centerWorkbook.createName();
        userRoles.setNameName("UserRole");
        userRoles.setRefersToFormula("AvailableRoles!$B$2:$B$" + Math.max(userRoleSheetPopulator.getUserRoleNames().size() + 1, 2));
    }
}
