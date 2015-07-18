/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.domain.handler.code;

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
import org.mifosplatform.infrastructure.dataimport.data.code.CodeValue;
import org.mifosplatform.infrastructure.dataimport.domain.handler.AbstractDataImportHandler;
import org.mifosplatform.infrastructure.dataimport.domain.handler.Result;
import org.mifosplatform.infrastructure.dataimport.services.utils.StringUtils;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class CodeValueDataImportHandler extends AbstractDataImportHandler {

    private static final Logger logger = LoggerFactory.getLogger(CodeValueDataImportHandler.class);

    private final int NAME_COL = 0;
    private final int CODE_NAME_COL = 1;
    private final int STATUS_COL = 3;
    private final int FAILURE_COL = 7;

    private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;

    private final Workbook workbook;

    private List<CodeValue> codeValues;

    public CodeValueDataImportHandler(Workbook workbook, final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService) {

        this.workbook = workbook;
        this.commandsSourceWritePlatformService = commandsSourceWritePlatformService;
        codeValues = new ArrayList<>();
    }

    @Override
    public Result parse() {

        Result result = new Result();
        Sheet codeValueSheet = workbook.getSheet("CodeValues");
        Integer noOfEntries = getNumberOfRows(codeValueSheet, 0);

        for (int rowIndex = 1; rowIndex < noOfEntries; rowIndex++) {
            Row row;
            try {
                row = codeValueSheet.getRow(rowIndex);
                if (isNotImported(row, STATUS_COL)) {
                    codeValues.add(parseAsCodeValue(row));
                }
            } catch (Exception e) {
                logger.error("row = " + rowIndex, e);
                result.addError("Row = " + rowIndex + " , " + e.getMessage());
            }
        }
        return result;
    }

    private CodeValue parseAsCodeValue(Row row) {

        String codeValueName = readAsString(NAME_COL, row);

        String codeName = readAsString(CODE_NAME_COL, row);
        Integer codeIdInt = getIdByName(workbook.getSheet("AvailableCodes"), codeName);
        Long codeId = codeIdInt != 0 ? codeIdInt.longValue() : null;

        String status = readAsString(STATUS_COL, row);

        if (StringUtils.isBlank(codeValueName)) { throw new IllegalArgumentException("Name is blank"); }

        return new CodeValue(codeValueName, codeId, row.getRowNum(), status);
    }

    @Override
    public Result upload() {

        Result result = new Result();
        Sheet officesSheet = workbook.getSheet("CodeValues");
        @SuppressWarnings("unused")
        Long centerId = null;

        for (int i = 0; i < codeValues.size(); i++) {

            Row row = officesSheet.getRow(codeValues.get(i).getRowIndex());
            Cell errorReportCell = row.createCell(FAILURE_COL);
            Cell statusCell = row.createCell(STATUS_COL);

            try {

                @SuppressWarnings("unused")
                String status = codeValues.get(i).getStatus();

                centerId = uploadCode(i).getSubResourceId();

                statusCell.setCellValue("Imported");
                statusCell.setCellStyle(getCellStyle(workbook, IndexedColors.LIGHT_GREEN));

            } catch (RuntimeException e) {

                String message = parseStatus(e.getMessage());
                String status = "";

                statusCell.setCellValue(status + " failed.");
                statusCell.setCellStyle(getCellStyle(workbook, IndexedColors.RED));

                errorReportCell.setCellValue(message);
                result.addError("Row = " + codeValues.get(i).getRowIndex() + " ," + message);
            }
        }
        setReportHeaders(officesSheet);
        return result;
    }

    private CommandProcessingResult uploadCode(int rowIndex) {

        CodeValue codeValue = codeValues.get(rowIndex);
        String payload = new Gson().toJson(codeValue);
        logger.info(payload);

        final CommandWrapper commandRequest = new CommandWrapperBuilder().createCodeValue(codeValue.getCodeId()).withJson(payload).build();
        final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);

        return result;
    }

    private void setReportHeaders(Sheet sheet) {

        writeString(STATUS_COL, sheet.getRow(0), "Status");
        writeString(FAILURE_COL, sheet.getRow(0), "Failure Report");
    }

    public List<CodeValue> getOffices() {
        return this.codeValues;
    }
}
