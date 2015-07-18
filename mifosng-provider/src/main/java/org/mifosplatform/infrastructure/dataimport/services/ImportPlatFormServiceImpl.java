/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.mifosplatform.infrastructure.dataimport.domain.handler.DataImportHandler;
import org.mifosplatform.infrastructure.dataimport.domain.handler.ImportHandlerFactoryService;
import org.mifosplatform.infrastructure.dataimport.domain.handler.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImportPlatFormServiceImpl implements ImportPlatformService {

    private final ImportHandlerFactoryService importHandlerFactoryService;
    private static final Logger logger = LoggerFactory.getLogger(TemplatePlatformServiceImpl.class);

    @Autowired
    public ImportPlatFormServiceImpl(final ImportHandlerFactoryService importHandlerFactoryService) {

        this.importHandlerFactoryService = importHandlerFactoryService;
    }

    @Override
    public Response importClientsFromTemplate(InputStream content) {

        return importFromTemplate(content);
    }

    @Override
    public Response importGroupsFromTemplate(InputStream content) {

        return importFromTemplate(content);
    }

    @Override
    public Response importCentersFromTemplate(InputStream content) {

        return importFromTemplate(content);
    }

    @Override
    public Response importLoansFromTemplate(InputStream content) {

        return importFromTemplate(content);
    }

    @Override
    public Response importLoanRepaymentFromTemplate(InputStream content) {

        return importFromTemplate(content);
    }

    @Override
    public Response importSavingsFromTemplate(InputStream content) {

        return importFromTemplate(content);
    }

    @Override
    public Response importSavingsTransactionFromTemplate(InputStream content) {

        return importFromTemplate(content);
    }

    @Override
    public Response importOfficesFromTemplate(InputStream content) {

        return importFromTemplate(content);
    }

    @Override
    public Response importCodesFromTemplate(InputStream content) {

        return importFromTemplate(content);
    }

    @Override
    public Response importCodeValuesFromTemplate(InputStream content) {

        return importFromTemplate(content);
    }

    @Override
    public Response importStaffFromTemplate(InputStream content) {

        return importFromTemplate(content);
    }

    @Override
    public Response importUsersFromTemplate(InputStream content) {

        return importFromTemplate(content);
    }

    private Response importFromTemplate(InputStream content) {
        Response response = null;
        Workbook workbook;

        try {
            workbook = new HSSFWorkbook(content);
            DataImportHandler handler = importHandlerFactoryService.createImportHandler(workbook);
            Result result = parseAndUpload(handler);
            response = writeResult(workbook, result);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return response;
    }

    private Result parseAndUpload(DataImportHandler handler) throws IOException {
        Result result = handler.parse();
        if (result.isSuccess()) {
            result = handler.upload();
        }
        return result;
    }

    private Response writeResult(Workbook workbook, Result result) throws IOException {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ResponseBuilder response = null;

        if (result.isSuccess()) {

            String fileName = "Results.xls";

            workbook.write(stream);

            response = Response.ok(new ByteArrayInputStream(stream.toByteArray()));
            response.header("Success", "true");
            response.header("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            response.header("Access-Control-Expose-Headers", "Success");

        } else {
            for (String e : result.getErrors())
                logger.debug("Failed: " + e);

            String fileName = "Re-Upload.xls";

            workbook.write(stream);

            response = Response.ok(new ByteArrayInputStream(stream.toByteArray()));
            response.header("Success", "false");
            response.header("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            response.header("Access-Control-Expose-Headers", "Success");
        }

        response.header("Content-Type", "application/vnd.ms-excel");

        return response.build();
    }

}
