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
//import org.mifosplatform.infrastructure.dataimport.services.CodeService;
import org.mifosplatform.infrastructure.codes.data.CodeValueData;
import org.mifosplatform.infrastructure.codes.service.CodeValueReadPlatformService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CodeValueSheetPopulator extends AbstractWorkbookPopulator {

    private static final Logger logger = LoggerFactory.getLogger(CodeValueSheetPopulator.class);

    private final String code;
    private final CodeValueReadPlatformService codeValueReadPlatformService;

    private Collection<CodeValueData> codeValues;
    private ArrayList<String> codeNames;

    private static final int ID_COL = 0;
    private static final int CODE_VALUE_COL = 1;

    protected CodeValueSheetPopulator(final String code, final CodeValueReadPlatformService codeValueReadPlatformService) {

        this.codeValueReadPlatformService = codeValueReadPlatformService;
        this.code = code;
    }

    @Override
    public Result downloadAndParse() {

        Result result = new Result();
        try {
            codeValues = codeValueReadPlatformService.retrieveCodeValuesByCode(code);

            codeNames = new ArrayList<>();
            for (CodeValueData codeValueData : codeValues) {

                codeNames.add(codeValueData.getName().trim().replaceAll("[ )(]", "_"));
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

            Sheet codeSheet = workbook.createSheet(code);
            setLayout(codeSheet);

            populateCodes(codeSheet, rowIndex);
            codeSheet.protectSheet("");

        } catch (Exception e) {
            result.addError(e.getMessage());
            logger.error(e.getMessage());
        }
        return result;
    }

    private void setLayout(Sheet worksheet) {

        worksheet.setColumnWidth(ID_COL, 2000);
        worksheet.setColumnWidth(CODE_VALUE_COL, 7000);

        Row rowHeader = worksheet.createRow(0);
        rowHeader.setHeight((short) 500);

        writeString(ID_COL, rowHeader, "ID");
        writeString(CODE_VALUE_COL, rowHeader, "Value");
    }

    private void populateCodes(Sheet codeSheet, int rowIndex) {

        for (CodeValueData codeValueData : codeValues) {

            Row row = codeSheet.createRow(rowIndex);
            writeInt(ID_COL, row, codeValueData.getId().intValue());
            writeString(CODE_VALUE_COL, row, codeValueData.getName().trim().replaceAll("[ )(]", "_"));
            rowIndex++;

        }
    }

    public ArrayList<String> getCodeNames() {
        return this.codeNames;
    }

}
