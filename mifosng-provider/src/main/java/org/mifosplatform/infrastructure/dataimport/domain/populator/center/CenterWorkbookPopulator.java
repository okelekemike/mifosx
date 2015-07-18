/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.domain.populator.center;

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

public class CenterWorkbookPopulator extends AbstractWorkbookPopulator {

    private static final int NAME_COL = 0;
    private static final int OFFICE_NAME_COL = 1;
    private static final int STAFF_NAME_COL = 2;
    private static final int ACTIVE_COL = 3;
    private static final int ACTIVATION_DATE_COL = 4;
    private static final int EXTERNAL_ID_COL = 5;
    private static final int STATUS_COL = 7;
    private static final int FAILURE_COL = 9;
    private static final int LOOKUP_OFFICE_NAME_COL = 251;
    private static final int LOOKUP_OFFICE_OPENING_DATE_COL = 252;

    private OfficeSheetPopulator officeSheetPopulator;
    private PersonnelSheetPopulator personnelSheetPopulator;

    public CenterWorkbookPopulator(OfficeSheetPopulator officeSheetPopulator, PersonnelSheetPopulator personnelSheetPopulator) {

        this.officeSheetPopulator = officeSheetPopulator;
        this.personnelSheetPopulator = personnelSheetPopulator;
    }

    @Override
    public Result downloadAndParse() {

        Result result = officeSheetPopulator.downloadAndParse();
        if (result.isSuccess()) result = personnelSheetPopulator.downloadAndParse();

        return result;
    }

    @Override
    public Result populate(Workbook workbook) {

        Sheet centerSheet = workbook.createSheet("Centers");

        Result result = personnelSheetPopulator.populate(workbook);
        if (result.isSuccess()) result = officeSheetPopulator.populate(workbook);

        setLayout(centerSheet);
        setLookupTable(centerSheet);

        if (result.isSuccess()) result = setRules(centerSheet);
        return result;
    }

    private void setLayout(Sheet worksheet) {

        Row rowHeader = worksheet.createRow(0);
        rowHeader.setHeight((short) 500);

        worksheet.setColumnWidth(NAME_COL, 5000);
        worksheet.setColumnWidth(OFFICE_NAME_COL, 5000);
        worksheet.setColumnWidth(STAFF_NAME_COL, 5000);
        worksheet.setColumnWidth(ACTIVE_COL, 2000);
        worksheet.setColumnWidth(ACTIVATION_DATE_COL, 3500);
        worksheet.setColumnWidth(EXTERNAL_ID_COL, 2500);
        worksheet.setColumnWidth(STATUS_COL, 2000);
        worksheet.setColumnWidth(FAILURE_COL, 2000);
        worksheet.setColumnWidth(LOOKUP_OFFICE_NAME_COL, 6000);
        worksheet.setColumnWidth(LOOKUP_OFFICE_OPENING_DATE_COL, 4000);

        writeString(NAME_COL, rowHeader, "Center Name*");
        writeString(OFFICE_NAME_COL, rowHeader, "Office Name*");
        writeString(STAFF_NAME_COL, rowHeader, "Staff Name*");
        writeString(ACTIVE_COL, rowHeader, "Active*");
        writeString(ACTIVATION_DATE_COL, rowHeader, "Activation Date*");
        writeString(EXTERNAL_ID_COL, rowHeader, "External ID");
        writeString(LOOKUP_OFFICE_NAME_COL, rowHeader, "Office Name");
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
            CellRangeAddressList dateRange = new CellRangeAddressList(1, SpreadsheetVersion.EXCEL97.getLastRowIndex(), ACTIVATION_DATE_COL,
                    ACTIVATION_DATE_COL);
            CellRangeAddressList activeRange = new CellRangeAddressList(1, SpreadsheetVersion.EXCEL97.getLastRowIndex(), ACTIVE_COL,
                    ACTIVE_COL);

            DataValidationHelper validationHelper = new HSSFDataValidationHelper((HSSFSheet) worksheet);
            ArrayList<String> officeNames = new ArrayList<>(Arrays.asList(officeSheetPopulator.getOfficeNames()));
            setNames(worksheet, officeNames);

            DataValidationConstraint officeNameConstraint = validationHelper.createFormulaListConstraint("Office");
            DataValidationConstraint staffNameConstraint = validationHelper
                    .createFormulaListConstraint("INDIRECT(CONCATENATE(\"Staff_\",$B1))");
            DataValidationConstraint activationDateConstraint = validationHelper.createDateConstraint(
                    DataValidationConstraint.OperatorType.BETWEEN, "=VLOOKUP($B1,$IR$2:$IS" + (officeNames.size() + 1) + ",2,FALSE)",
                    "=TODAY()", "dd/mm/yy");
            DataValidationConstraint booleanConstraint = validationHelper.createExplicitListConstraint(new String[] { "True", "False" });
            
            DataValidation officeValidation = validationHelper.createValidation(officeNameConstraint, officeNameRange);
            DataValidation staffValidation = validationHelper.createValidation(staffNameConstraint, staffNameRange);
            DataValidation activationDateValidation = validationHelper.createValidation(activationDateConstraint, dateRange);
            DataValidation activeValidation = validationHelper.createValidation(booleanConstraint, activeRange);

            worksheet.addValidationData(activeValidation);
            worksheet.addValidationData(officeValidation);
            worksheet.addValidationData(staffValidation);
            worksheet.addValidationData(activationDateValidation);

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
    }
}
