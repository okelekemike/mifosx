/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.domain.populator.staff;

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

public class StaffWorkbookPopulator extends AbstractWorkbookPopulator {

    private final int OFFICE_NAME_COL = 0;
    private final int FIRST_NAME_COL = 1;
    private final int LAST_NAME_COL = 2;
    private final int IS_LOAN_OFFICER_COL = 3;
    private final int MOBILE_NO_COL = 4;
    private final int STATUS_COL = 7;
    private final int FAILURE_COL = 9;
    private final int LOOKUP_OFFICE_NAME_COL = 254;
    private final int LOOKUP_OFFICE_OPENING_DATE_COL = 255;

    private final OfficeSheetPopulator officeSheetPopulator;
    private final String SheetName;

    public StaffWorkbookPopulator(final OfficeSheetPopulator officeSheetPopulator) {

        this.officeSheetPopulator = officeSheetPopulator;

        SheetName = "StaffForImport";
    }

    @Override
    public Result downloadAndParse() {

        Result result = officeSheetPopulator.downloadAndParse();

        return result;
    }

    @Override
    public Result populate(Workbook workbook) {

        Sheet centerSheet = workbook.createSheet(SheetName);

        Result result = officeSheetPopulator.populate(workbook);

        setLayout(centerSheet);
        setLookupTable(centerSheet);

        if (result.isSuccess()) result = setRules(centerSheet);
        return result;
    }

    private void setLayout(Sheet worksheet) {

        Row rowHeader = worksheet.createRow(0);
        rowHeader.setHeight((short) 500);

        worksheet.setColumnWidth(OFFICE_NAME_COL, 6000);
        worksheet.setColumnWidth(FIRST_NAME_COL, 6000);
        worksheet.setColumnWidth(LAST_NAME_COL, 6000);
        worksheet.setColumnWidth(IS_LOAN_OFFICER_COL, 2500);
        worksheet.setColumnWidth(MOBILE_NO_COL, 5000);
        worksheet.setColumnWidth(STATUS_COL, 6000);
        worksheet.setColumnWidth(FAILURE_COL, 6000);
        worksheet.setColumnWidth(LOOKUP_OFFICE_NAME_COL, 6000);
        worksheet.setColumnWidth(LOOKUP_OFFICE_OPENING_DATE_COL, 4000);

        writeString(OFFICE_NAME_COL, rowHeader, "Office Name*");
        writeString(FIRST_NAME_COL, rowHeader, "First Name*");
        writeString(LAST_NAME_COL, rowHeader, "Last Name*");
        writeString(IS_LOAN_OFFICER_COL, rowHeader, "Is Loan Officer*");
        writeString(MOBILE_NO_COL, rowHeader, "Mobile No");
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
            CellRangeAddressList isLoanOfficerRange = new CellRangeAddressList(1, SpreadsheetVersion.EXCEL97.getLastRowIndex(),
                    IS_LOAN_OFFICER_COL, IS_LOAN_OFFICER_COL);

            DataValidationHelper validationHelper = new HSSFDataValidationHelper((HSSFSheet) worksheet);
            ArrayList<String> officeNames = new ArrayList<>(Arrays.asList(officeSheetPopulator.getOfficeNames()));
            setNames(worksheet, officeNames);

            DataValidationConstraint officeNameConstraint = validationHelper.createFormulaListConstraint("Office");
            DataValidationConstraint booleanConstraint = validationHelper.createExplicitListConstraint(new String[] { "True", "False" });

            DataValidation officeValidation = validationHelper.createValidation(officeNameConstraint, officeNameRange);
            DataValidation isLoanOfficerValidation = validationHelper.createValidation(booleanConstraint, isLoanOfficerRange);

            worksheet.addValidationData(officeValidation);
            worksheet.addValidationData(isLoanOfficerValidation);

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
    }
}
