/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.domain.handler.office;

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
import org.mifosplatform.infrastructure.dataimport.data.office.Office;
import org.mifosplatform.infrastructure.dataimport.domain.handler.AbstractDataImportHandler;
import org.mifosplatform.infrastructure.dataimport.domain.handler.Result;
import org.mifosplatform.infrastructure.dataimport.services.utils.StringUtils;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class OfficeDataImportHandler extends AbstractDataImportHandler {

    private static final Logger logger = LoggerFactory.getLogger(OfficeDataImportHandler.class);

    private static final int NAME_COL = 0;
    private static final int PARENT_OFFICE_COL = 1;
    private static final int OPENING_DATE_COL = 2;
    private static final int EXTERNAL_ID_COL = 3;
    private static final int STATUS_COL = 5;
    private static final int FAILURE_COL = 8;

    private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;

    private final Workbook workbook;

    private List<Office> offices;

    public OfficeDataImportHandler(Workbook workbook, final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService) {

        this.workbook = workbook;
        this.commandsSourceWritePlatformService = commandsSourceWritePlatformService;
        offices = new ArrayList<>();
    }

    @Override
    public Result parse() {

        Result result = new Result();
        Sheet officeSheet = workbook.getSheet("OfficesForImport");
        Integer noOfEntries = getNumberOfRows(officeSheet, 0);

        for (int rowIndex = 1; rowIndex < noOfEntries; rowIndex++) {
            Row row;
            try {
                row = officeSheet.getRow(rowIndex);
                if (isNotImported(row, STATUS_COL)) {
                    offices.add(parseAsOffice(row));
                }
            } catch (Exception e) {
                logger.error("row = " + rowIndex, e);
                result.addError("Row = " + rowIndex + " , " + e.getMessage());
            }
        }
        return result;
    }

    private Office parseAsOffice(Row row) {

        String officeName = readAsString(NAME_COL, row);
        
        String parentOfficeName = readAsString(PARENT_OFFICE_COL, row);
        Integer parentOfficeIdInt = getIdByName(workbook.getSheet("Offices"), parentOfficeName);
        Long parentOfficeId = parentOfficeIdInt != 0 && parentOfficeIdInt != null ? parentOfficeIdInt.longValue() : null;
        
        String openingDate = readAsDate(OPENING_DATE_COL, row);
        String externalId = readAsString(EXTERNAL_ID_COL, row);

        String status = readAsString(STATUS_COL, row);

        if (StringUtils.isBlank(officeName)) { throw new IllegalArgumentException("Name is blank"); }
        
        return new Office(officeName, parentOfficeId, openingDate, externalId, row.getRowNum(), status);
    }

    @Override
    public Result upload() {

        Result result = new Result();
        Sheet officesSheet = workbook.getSheet("OfficesForImport");
        @SuppressWarnings("unused")
        Long centerId = null;

        for (int i = 0; i < offices.size(); i++) {

            Row row = officesSheet.getRow(offices.get(i).getRowIndex());
            Cell errorReportCell = row.createCell(FAILURE_COL);
            Cell statusCell = row.createCell(STATUS_COL);

            try {

                @SuppressWarnings("unused")
                String status = offices.get(i).getStatus();

                centerId = uploadCenter(i).getOfficeId();

                statusCell.setCellValue("Imported");
                statusCell.setCellStyle(getCellStyle(workbook, IndexedColors.LIGHT_GREEN));

            } catch (RuntimeException e) {

                String message = parseStatus(e.getMessage());
                String status = "";

                statusCell.setCellValue(status + " failed.");
                statusCell.setCellStyle(getCellStyle(workbook, IndexedColors.RED));

                errorReportCell.setCellValue(message);
                result.addError("Row = " + offices.get(i).getRowIndex() + " ," + message);
            }
        }
        setReportHeaders(officesSheet);
        return result;
    }

    private CommandProcessingResult uploadCenter(int rowIndex) {

        String payload = new Gson().toJson(offices.get(rowIndex));
        logger.info(payload);

        final CommandWrapper commandRequest = new CommandWrapperBuilder().createOffice().withJson(payload).build();
        final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);

        return result;
    }

    private void setReportHeaders(Sheet sheet) {

        writeString(STATUS_COL, sheet.getRow(0), "Status");
        writeString(FAILURE_COL, sheet.getRow(0), "Failure Report");
    }

    public List<Office> getOffices() {
        return this.offices;
    }
}
