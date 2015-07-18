/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.domain.handler.staff;

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
import org.mifosplatform.infrastructure.dataimport.data.staff.Employee;
import org.mifosplatform.infrastructure.dataimport.domain.handler.AbstractDataImportHandler;
import org.mifosplatform.infrastructure.dataimport.domain.handler.Result;
import org.mifosplatform.infrastructure.dataimport.services.utils.StringUtils;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class StaffDataImportHandler extends AbstractDataImportHandler {

    private static final Logger logger = LoggerFactory.getLogger(StaffDataImportHandler.class);

    private final int OFFICE_NAME_COL = 0;
    private final int FIRST_NAME_COL = 1;
    private final int LAST_NAME_COL = 2;
    private final int IS_LOAN_OFFICER_COL = 3;
    private final int MOBILE_NO_COL = 4;
    private final int STATUS_COL = 7;
    private final int FAILURE_COL = 9;
    @SuppressWarnings("unused")
    private final int LOOKUP_OFFICE_NAME_COL = 254;
    @SuppressWarnings("unused")
    private final int LOOKUP_OFFICE_OPENING_DATE_COL = 255;

    private final String SheetName;

    private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;

    private final Workbook workbook;

    private List<Employee> employees;

    public StaffDataImportHandler(Workbook workbook, final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService) {

        this.workbook = workbook;
        this.commandsSourceWritePlatformService = commandsSourceWritePlatformService;
        employees = new ArrayList<>();

        SheetName = "StaffForImport";
    }

    @Override
    public Result parse() {

        Result result = new Result();
        Sheet centersSheet = workbook.getSheet(SheetName);
        Integer noOfEntries = getNumberOfRows(centersSheet, 0);

        for (int rowIndex = 1; rowIndex < noOfEntries; rowIndex++) {
            Row row;
            try {
                row = centersSheet.getRow(rowIndex);
                if (isNotImported(row, STATUS_COL)) {
                    employees.add(parseAsEmployee(row));
                }
            } catch (Exception e) {
                logger.error("row = " + rowIndex, e);
                result.addError("Row = " + rowIndex + " , " + e.getMessage());
            }
        }
        return result;
    }

    private Employee parseAsEmployee(Row row) {

        String officeName = readAsString(OFFICE_NAME_COL, row);
        Integer officeIdInt = getIdByName(workbook.getSheet("Offices"), officeName);
        Long officeId = officeIdInt != 0 && officeIdInt != null ? officeIdInt.longValue() : null;

        String firstName = readAsString(FIRST_NAME_COL, row);
        String lastName = readAsString(LAST_NAME_COL, row);
        String isLoanOfficer = readAsBoolean(IS_LOAN_OFFICER_COL, row).toString();
        String moblileNo = readAsString(MOBILE_NO_COL, row);

        String status = readAsString(STATUS_COL, row);

        if (StringUtils.isBlank(officeName)) { throw new IllegalArgumentException("Office Name is blank"); }

        return new Employee(officeId, firstName, lastName, isLoanOfficer, moblileNo, status, row.getRowNum());
    }

    @Override
    public Result upload() {

        Result result = new Result();
        Sheet employeeSheet = workbook.getSheet(SheetName);
        @SuppressWarnings("unused")
        Long employeeId = null;

        for (int i = 0; i < employees.size(); i++) {

            Row row = employeeSheet.getRow(employees.get(i).getRowIndex());
            Cell errorReportCell = row.createCell(FAILURE_COL);
            Cell statusCell = row.createCell(STATUS_COL);

            try {

                @SuppressWarnings("unused")
                String status = employees.get(i).getStatus();

                employeeId = uploadEmployee(i).getSubResourceId();

                statusCell.setCellValue("Imported");
                statusCell.setCellStyle(getCellStyle(workbook, IndexedColors.LIGHT_GREEN));

            } catch (RuntimeException e) {

                String message = parseStatus(e.getMessage());
                String status = "";

                statusCell.setCellValue(status + " failed.");
                statusCell.setCellStyle(getCellStyle(workbook, IndexedColors.RED));

                errorReportCell.setCellValue(message);
                result.addError("Row = " + employees.get(i).getRowIndex() + " ," + message);
            }
        }
        setReportHeaders(employeeSheet);
        return result;
    }

    private CommandProcessingResult uploadEmployee(int rowIndex) {

        String payload = new Gson().toJson(employees.get(rowIndex));
        logger.info(payload);

        final CommandWrapper commandRequest = new CommandWrapperBuilder().createStaff().withJson(payload).build();
        final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);

        return result;
    }

    private void setReportHeaders(Sheet sheet) {

        writeString(STATUS_COL, sheet.getRow(0), "Status");
        writeString(FAILURE_COL, sheet.getRow(0), "Failure Report");
    }

    public List<Employee> getCenters() {
        return this.employees;
    }
}
