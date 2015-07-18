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
import org.mifosplatform.infrastructure.dataimport.data.code.Code;
import org.mifosplatform.infrastructure.dataimport.domain.handler.AbstractDataImportHandler;
import org.mifosplatform.infrastructure.dataimport.domain.handler.Result;
import org.mifosplatform.infrastructure.dataimport.services.utils.StringUtils;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class CodeDataImportHandler extends AbstractDataImportHandler {

    private static final Logger logger = LoggerFactory.getLogger(CodeDataImportHandler.class);

    private static final int NAME_COL = 0;
    private static final int STATUS_COL = 3;
    private static final int FAILURE_COL = 7;

    private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;

    private final Workbook workbook;

    private List<Code> codes;

    public CodeDataImportHandler(Workbook workbook, final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService) {

        this.workbook = workbook;
        this.commandsSourceWritePlatformService = commandsSourceWritePlatformService;
        codes = new ArrayList<>();
    }

    @Override
    public Result parse() {

        Result result = new Result();
        Sheet officeSheet = workbook.getSheet("Codes");
        Integer noOfEntries = getNumberOfRows(officeSheet, 0);

        for (int rowIndex = 1; rowIndex < noOfEntries; rowIndex++) {
            Row row;
            try {
                row = officeSheet.getRow(rowIndex);
                if (isNotImported(row, STATUS_COL)) {
                    codes.add(parseAsCode(row));
                }
            } catch (Exception e) {
                logger.error("row = " + rowIndex, e);
                result.addError("Row = " + rowIndex + " , " + e.getMessage());
            }
        }
        return result;
    }

    private Code parseAsCode(Row row) {

        String codeName = readAsString(NAME_COL, row);

        String status = readAsString(STATUS_COL, row);

        if (StringUtils.isBlank(codeName)) { throw new IllegalArgumentException("Name is blank"); }
        
        return new Code(codeName, row.getRowNum(), status);
    }

    @Override
    public Result upload() {

        Result result = new Result();
        Sheet officesSheet = workbook.getSheet("Codes");
        @SuppressWarnings("unused")
        Long centerId = null;

        for (int i = 0; i < codes.size(); i++) {

            Row row = officesSheet.getRow(codes.get(i).getRowIndex());
            Cell errorReportCell = row.createCell(FAILURE_COL);
            Cell statusCell = row.createCell(STATUS_COL);

            try {

                @SuppressWarnings("unused")
                String status = codes.get(i).getStatus();

                centerId = uploadCode(i).getSubResourceId();

                statusCell.setCellValue("Imported");
                statusCell.setCellStyle(getCellStyle(workbook, IndexedColors.LIGHT_GREEN));

            } catch (RuntimeException e) {

                String message = parseStatus(e.getMessage());
                String status = "";

                statusCell.setCellValue(status + " failed.");
                statusCell.setCellStyle(getCellStyle(workbook, IndexedColors.RED));

                errorReportCell.setCellValue(message);
                result.addError("Row = " + codes.get(i).getRowIndex() + " ," + message);
            }
        }
        setReportHeaders(officesSheet);
        return result;
    }

    private CommandProcessingResult uploadCode(int rowIndex) {

        String payload = new Gson().toJson(codes.get(rowIndex));
        logger.info(payload);

        final CommandWrapper commandRequest = new CommandWrapperBuilder().createCode().withJson(payload).build();
        final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);

        return result;
    }

    private void setReportHeaders(Sheet sheet) {

        writeString(STATUS_COL, sheet.getRow(0), "Status");
        writeString(FAILURE_COL, sheet.getRow(0), "Failure Report");
    }

    public List<Code> getOffices() {
        return this.codes;
    }
}
