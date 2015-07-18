/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.domain.populator.code;

import java.util.ArrayList;

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
import org.mifosplatform.infrastructure.dataimport.domain.populator.CodeSheetPopulator;

public class CodeValueWorkbookPopulator extends AbstractWorkbookPopulator {

    private final int NAME_COL = 0;
    private final int CODE_NAME_COL = 1;
    private final int STATUS_COL = 3;
    private final int FAILURE_COL = 7;
    
    private final CodeSheetPopulator codeSheetPopulator;

    public CodeValueWorkbookPopulator(final CodeSheetPopulator codeSheetPopulator) {

        this.codeSheetPopulator = codeSheetPopulator;
    }

    @Override
    public Result downloadAndParse() {
        
        Result results = codeSheetPopulator.downloadAndParse();
                
        return results;
    }

    @Override
    public Result populate(Workbook workbook) {

        Sheet codeSheet = workbook.createSheet("CodeValues");

        setLayout(codeSheet);
        
        Result result = codeSheetPopulator.populate(workbook);
        if (result.isSuccess()) result = setRules(codeSheet);
        
        return new Result();
    }

    private void setLayout(Sheet worksheet) {

        Row rowHeader = worksheet.createRow(0);
        rowHeader.setHeight((short) 500);

        worksheet.setColumnWidth(NAME_COL, 5000);
        worksheet.setColumnWidth(CODE_NAME_COL, 5000);
        worksheet.setColumnWidth(STATUS_COL, 2000);
        worksheet.setColumnWidth(FAILURE_COL, 2000);

        writeString(NAME_COL, rowHeader, "Code Value Name*");
        writeString(CODE_NAME_COL, rowHeader, "Code Name*");
    }
    
    private Result setRules(Sheet worksheet) {

        Result result = new Result();

        try {
            CellRangeAddressList codeNameRange = new CellRangeAddressList(1, SpreadsheetVersion.EXCEL97.getLastRowIndex(),
                    CODE_NAME_COL, CODE_NAME_COL);

            DataValidationHelper validationHelper = new HSSFDataValidationHelper((HSSFSheet) worksheet);
            ArrayList<String> codeNames = codeSheetPopulator.getCodeNames();
            setNames(worksheet, codeNames);

            DataValidationConstraint codeNameConstraint = validationHelper.createFormulaListConstraint("CodeNames");
            
            DataValidation codeValidation = validationHelper.createValidation(codeNameConstraint, codeNameRange);
            
            worksheet.addValidationData(codeValidation);

        } catch (RuntimeException re) {
            result.addError(re.getMessage());
        }
        return result;
    }

    private void setNames(Sheet worksheet, ArrayList<String> codeNames) {

        Workbook centerWorkbook = worksheet.getWorkbook();
        Name officeGroup = centerWorkbook.createName();
        officeGroup.setNameName("CodeNames");
        officeGroup.setRefersToFormula("AvailableCodes!$B$2:$B$" + Math.max(codeNames.size()+1, 2));

    }
}
