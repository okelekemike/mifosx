/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.domain.handler.center;

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
import org.mifosplatform.infrastructure.dataimport.data.center.Center;
import org.mifosplatform.infrastructure.dataimport.domain.handler.AbstractDataImportHandler;
import org.mifosplatform.infrastructure.dataimport.domain.handler.Result;
import org.mifosplatform.infrastructure.dataimport.services.utils.StringUtils;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class CenterDataImportHandler extends AbstractDataImportHandler {

    private static final Logger logger = LoggerFactory.getLogger(CenterDataImportHandler.class);

    private static final int NAME_COL = 0;
    private static final int OFFICE_NAME_COL = 1;
    private static final int STAFF_NAME_COL = 2;
    private static final int ACTIVE_COL = 3;
    private static final int ACTIVATION_DATE_COL = 4;
    private static final int EXTERNAL_ID_COL = 5;
    private static final int STATUS_COL = 7;
    private static final int FAILURE_COL = 9;
    @SuppressWarnings("unused")
    private static final int LOOKUP_OFFICE_NAME_COL = 251;
    @SuppressWarnings("unused")
    private static final int LOOKUP_OFFICE_OPENING_DATE_COL = 252;

    private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;

    private final Workbook workbook;

    private List<Center> centers;

    public CenterDataImportHandler(Workbook workbook, final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService) {

        this.workbook = workbook;
        this.commandsSourceWritePlatformService = commandsSourceWritePlatformService;
        centers = new ArrayList<>();
    }

    @Override
    public Result parse() {

        Result result = new Result();
        Sheet centersSheet = workbook.getSheet("Centers");
        Integer noOfEntries = getNumberOfRows(centersSheet, 0);

        for (int rowIndex = 1; rowIndex < noOfEntries; rowIndex++) {
            Row row;
            try {
                row = centersSheet.getRow(rowIndex);
                if (isNotImported(row, STATUS_COL)) {
                    centers.add(parseAsCenter(row));
                }
            } catch (Exception e) {
                logger.error("row = " + rowIndex, e);
                result.addError("Row = " + rowIndex + " , " + e.getMessage());
            }
        }
        return result;
    }

    private Center parseAsCenter(Row row) {

        String centerName = readAsString(NAME_COL, row);

        String officeName = readAsString(OFFICE_NAME_COL, row);
        Integer officeIdInt = getIdByName(workbook.getSheet("Offices"), officeName);
        Long officeId = officeIdInt != 0 && officeIdInt != null ? officeIdInt.longValue() : null;

        String staffName = readAsString(STAFF_NAME_COL, row);
        Integer staffIdInt = getIdByName(workbook.getSheet("Staff"), staffName);
        Long staffId = staffIdInt != 0 && staffIdInt != null ? staffIdInt.longValue() : null;

        String active = readAsBoolean(ACTIVE_COL, row).toString();
        String activationDate = readAsDate(ACTIVATION_DATE_COL, row);
        String externalId = readAsString(EXTERNAL_ID_COL, row);

        String status = readAsString(STATUS_COL, row);

        if (StringUtils.isBlank(centerName)) { throw new IllegalArgumentException("Name is blank"); }

        return new Center(centerName, officeId, staffId, active, activationDate, externalId, row.getRowNum(), status);
    }

    @Override
    public Result upload() {

        Result result = new Result();
        Sheet centerSheet = workbook.getSheet("Centers");
        @SuppressWarnings("unused")
        Long centerId = null;

        for (int i = 0; i < centers.size(); i++) {

            Row row = centerSheet.getRow(centers.get(i).getRowIndex());
            Cell errorReportCell = row.createCell(FAILURE_COL);
            Cell statusCell = row.createCell(STATUS_COL);

            try {

                @SuppressWarnings("unused")
                String status = centers.get(i).getStatus();

                centerId = uploadCenter(i).getGroupId();

                statusCell.setCellValue("Imported");
                statusCell.setCellStyle(getCellStyle(workbook, IndexedColors.LIGHT_GREEN));

            } catch (RuntimeException e) {

                String message = parseStatus(e.getMessage());
                String status = "";
                
                statusCell.setCellValue(status + " failed.");
                statusCell.setCellStyle(getCellStyle(workbook, IndexedColors.RED));

                errorReportCell.setCellValue(message);
                result.addError("Row = " + centers.get(i).getRowIndex() + " ," + message);
            }
        }
        setReportHeaders(centerSheet);
        return result;
    }

    private CommandProcessingResult uploadCenter(int rowIndex) {

        String payload = new Gson().toJson(centers.get(rowIndex));
        logger.info(payload);

        final CommandWrapper commandRequest = new CommandWrapperBuilder().createCenter().withJson(payload).build();
        final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);

        return result;
    }

    private void setReportHeaders(Sheet sheet) {

        writeString(STATUS_COL, sheet.getRow(0), "Status");
        writeString(FAILURE_COL, sheet.getRow(0), "Failure Report");
    }

    public List<Center> getCenters() {
        return this.centers;
    }
}
