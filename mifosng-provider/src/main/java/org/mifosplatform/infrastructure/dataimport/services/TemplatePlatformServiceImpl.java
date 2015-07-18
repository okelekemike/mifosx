/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.mifosplatform.infrastructure.dataimport.domain.handler.Result;
import org.mifosplatform.infrastructure.dataimport.domain.populator.WorkbookPopulator;
import org.mifosplatform.infrastructure.dataimport.domain.populator.WorkbookPopulatorFactoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplatePlatformServiceImpl implements TemplatePlatformService {

    private final WorkbookPopulatorFactoryService workbookPopulatorFactoryService;
    private static final Logger logger = LoggerFactory.getLogger(TemplatePlatformServiceImpl.class);

    @Autowired
    public TemplatePlatformServiceImpl(final WorkbookPopulatorFactoryService workbookPopulatorFactoryService) {
        this.workbookPopulatorFactoryService = workbookPopulatorFactoryService;
    }

    @Override
    public Response getClientImportTemplate(int clientTypeId) {

        String fileName = "client";
        String parameter = "";

        switch (clientTypeId) {

            case 0:
                parameter = "individual";
            break;

            case 1:
                parameter = "corporate";
            break;

            default:
                parameter = "individual";
            break;
        }

        return getTemplate(parameter, fileName);
    }

    @Override
    public Response getGroupImportTemplate() {

        String fileName = "groups";

        return getTemplate("", fileName);
    }

    @Override
    public Response getCenterImportTemplate() {

        String fileName = "center";

        return getTemplate("", fileName);
    }

    @Override
    public Response getLoanImportTemplate() {

        String fileName = "loan";

        return getTemplate("", fileName);
    }

    @Override
    public Response getLoanRepaymentImportTemplate() {

        String fileName = "loanRepaymentHistory";

        return getTemplate("", fileName);
    }

    @Override
    public Response getSavingImportTemplate() {

        String fileName = "savings";

        return getTemplate("", fileName);
    }

    @Override
    public Response getSavingsTransactionImportTemplate() {

        String fileName = "savingsTransactionHistory";

        return getTemplate("", fileName);
    }

    @Override
    public Response getOfficeImportTemplate() {

        String fileName = "office";

        return getTemplate("", fileName);
    }

    @Override
    public Response getCodeImportTemplate() {

        String fileName = "codes";

        return getTemplate("", fileName);
    }

    @Override
    public Response getCodeValueImportTemplate() {

        String fileName = "codevalues";

        return getTemplate("", fileName);
    }

    @Override
    public Response getStaffImportTemplate() {

        String fileName = "staff";

        return getTemplate("", fileName);
    }

    @Override
    public Response getUserImportTemplate() {

        String fileName = "users";

        return getTemplate("", fileName);
    }

    private Response getTemplate(final String parameter, String fileName) {

        Response response = null;

        try {

            WorkbookPopulator populator = workbookPopulatorFactoryService.createWorkbookPopulator(parameter, fileName);
            Workbook workbook = new HSSFWorkbook();
            Result result = downloadAndPopulate(workbook, populator);

            if (result.isSuccess()) {
                fileName = fileName + ".xls";
                response = getOutput(workbook, fileName);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return response;
    }

    private Result downloadAndPopulate(Workbook workbook, WorkbookPopulator populator) throws IOException {
        Result result = populator.downloadAndParse();
        if (result.isSuccess()) {
            result = populator.populate(workbook);
        }
        return result;
    }

    private Response getOutput(Workbook workbook, String fileName) throws IOException {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        workbook.write(stream);
        ResponseBuilder response = Response.ok(new ByteArrayInputStream(stream.toByteArray()));

        response.header("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.header("Content-Type", "application/vnd.ms-excel");

        return response.build();
    }

}