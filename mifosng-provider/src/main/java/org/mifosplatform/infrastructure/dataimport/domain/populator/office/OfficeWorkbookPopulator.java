/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.domain.populator.office;

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

public class OfficeWorkbookPopulator extends AbstractWorkbookPopulator {

    private static final int NAME_COL = 0;
    private static final int PARENT_OFFICE_COL = 1;
    private static final int OPENING_DATE_COL = 2;
    private static final int EXTERNAL_ID_COL = 3;
    private static final int STATUS_COL = 5;
    private static final int FAILURE_COL = 8;

    private final OfficeSheetPopulator officeSheetPopulator;

    public OfficeWorkbookPopulator(final OfficeSheetPopulator officeSheetPopulator) {

        this.officeSheetPopulator = officeSheetPopulator;
    }

    @Override
    public Result downloadAndParse() {

        Result result = officeSheetPopulator.downloadAndParse();
        return result;
    }

    @Override
    public Result populate(Workbook workbook) {

        Sheet groupSheet = workbook.createSheet("OfficesForImport");

        setLayout(groupSheet);

        Result result = officeSheetPopulator.populate(workbook);
        if (result.isSuccess()) result = setRules(groupSheet);

        return result;
    }

    private void setLayout(Sheet worksheet) {

        Row rowHeader = worksheet.createRow(0);
        rowHeader.setHeight((short) 500);

        worksheet.setColumnWidth(NAME_COL, 5000);
        worksheet.setColumnWidth(PARENT_OFFICE_COL, 5000);
        worksheet.setColumnWidth(OPENING_DATE_COL, 5000);
        worksheet.setColumnWidth(EXTERNAL_ID_COL, 2500);
        worksheet.setColumnWidth(STATUS_COL, 2000);
        worksheet.setColumnWidth(FAILURE_COL, 2000);

        writeString(NAME_COL, rowHeader, "Office Name*");
        writeString(PARENT_OFFICE_COL, rowHeader, "Parent Office*");
        writeString(OPENING_DATE_COL, rowHeader, "Opening Date*");
        writeString(EXTERNAL_ID_COL, rowHeader, "External ID");
    }

    private Result setRules(Sheet worksheet) {

        Result result = new Result();

        try {
            CellRangeAddressList officeNameRange = new CellRangeAddressList(1, SpreadsheetVersion.EXCEL97.getLastRowIndex(),
                    PARENT_OFFICE_COL, PARENT_OFFICE_COL);
            CellRangeAddressList dateRange = new CellRangeAddressList(1, SpreadsheetVersion.EXCEL97.getLastRowIndex(), OPENING_DATE_COL,
                    OPENING_DATE_COL);

            DataValidationHelper validationHelper = new HSSFDataValidationHelper((HSSFSheet) worksheet);
            ArrayList<String> officeNames = new ArrayList<>(Arrays.asList(officeSheetPopulator.getOfficeNames()));
            setNames(worksheet, officeNames);

            DataValidationConstraint officeNameConstraint = validationHelper.createFormulaListConstraint("ParentOffice");
            DataValidationConstraint dateConstraint = validationHelper.createDateConstraint(
                    DataValidationConstraint.OperatorType.LESS_OR_EQUAL, "=TODAY()", "=TODAY()", "dd/mm/yy");

            DataValidation officeValidation = validationHelper.createValidation(officeNameConstraint, officeNameRange);
            DataValidation dateValidation = validationHelper.createValidation(dateConstraint, dateRange);

            worksheet.addValidationData(officeValidation);
            worksheet.addValidationData(dateValidation);

        } catch (RuntimeException re) {
            result.addError(re.getMessage());
        }
        return result;
    }

    private void setNames(Sheet worksheet, ArrayList<String> officeNames) {

        Workbook officeWorkbook = worksheet.getWorkbook();
        Name officeGroup = officeWorkbook.createName();
        officeGroup.setNameName("ParentOffice");
        officeGroup.setRefersToFormula("Offices!$B$2:$B$" + Math.max(officeNames.size() + 1, 2));

    }

}
