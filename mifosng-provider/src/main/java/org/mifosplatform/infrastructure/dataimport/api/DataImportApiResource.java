/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.api;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.mifosplatform.infrastructure.dataimport.services.ImportPlatformService;
import org.mifosplatform.infrastructure.dataimport.services.TemplatePlatformService;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.mifosplatform.portfolio.client.api.ClientApiConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataParam;

@Path("/{entityType}/import")
@Component
@Scope("singleton")
public class DataImportApiResource {

    private final PlatformSecurityContext context;
    private final TemplatePlatformService templatePlatformService;
    private final ImportPlatformService importPlatformService;

    @Autowired
    public DataImportApiResource(final PlatformSecurityContext context, final TemplatePlatformService templatePlatformService,
            ImportPlatformService importPlatformService) {

        this.context = context;
        this.templatePlatformService = templatePlatformService;
        this.importPlatformService = importPlatformService;
    }

    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_OCTET_STREAM })
    public Response getTemplate(@PathParam("entityType") final String entityType) {

        this.context.authenticatedUser().validateHasReadPermission(ClientApiConstants.CLIENT_RESOURCE_NAME);

        Response response = null;

        if (entityType.equals("clients"))
            response = this.templatePlatformService.getClientImportTemplate(0);
        else if (entityType.equals("clientsofgroup"))
            response = this.templatePlatformService.getClientImportTemplate(1);
        else if (entityType.equals("groups"))
            response = this.templatePlatformService.getGroupImportTemplate();
        else if (entityType.equals("centers"))
            response = this.templatePlatformService.getCenterImportTemplate();
        else if (entityType.equals("savings"))
            response = this.templatePlatformService.getSavingImportTemplate();
        else if (entityType.equals("savingtransactions"))
            response = this.templatePlatformService.getSavingsTransactionImportTemplate();
        else if (entityType.equals("loans"))
            response = this.templatePlatformService.getLoanImportTemplate();
        else if (entityType.equals("loanrepayments"))
            response = this.templatePlatformService.getLoanRepaymentImportTemplate();
        else if (entityType.equals("offices"))
            response = this.templatePlatformService.getOfficeImportTemplate();
        else if (entityType.equals("codes"))
            response = this.templatePlatformService.getCodeImportTemplate();
        else if (entityType.equals("codevalues"))
            response = this.templatePlatformService.getCodeValueImportTemplate();
        else if (entityType.equals("staff"))
            response = this.templatePlatformService.getStaffImportTemplate();
        else if (entityType.equals("users"))
            response = this.templatePlatformService.getUserImportTemplate();
        else {

        }

        return response;
    }

    @POST
    @Consumes({ MediaType.MULTIPART_FORM_DATA })
    @Produces({ MediaType.APPLICATION_OCTET_STREAM })
    public Response importFromTemplate(@PathParam("entityType") final String entityType,
            @FormDataParam("file") final InputStream uploadedInputStream,
            @SuppressWarnings("unused") @FormDataParam("file") final FormDataContentDisposition fileDetails,
            @SuppressWarnings("unused") @FormDataParam("file") final FormDataBodyPart bodyPart) {

        this.context.authenticatedUser().validateHasReadPermission(ClientApiConstants.CLIENT_RESOURCE_NAME);

        Response response = null;

        if (entityType.equals("clients") || entityType.equals("clientsofgroup"))
            response = this.importPlatformService.importClientsFromTemplate(uploadedInputStream);
        else if (entityType.equals("groups"))
            response = this.importPlatformService.importGroupsFromTemplate(uploadedInputStream);
        else if (entityType.equals("centers"))
            response = this.importPlatformService.importCentersFromTemplate(uploadedInputStream);
        else if (entityType.equals("savings"))
            response = this.importPlatformService.importSavingsFromTemplate(uploadedInputStream);
        else if (entityType.equals("savingtransactions"))
            response = this.importPlatformService.importSavingsTransactionFromTemplate(uploadedInputStream);
        else if (entityType.equals("loans"))
            response = this.importPlatformService.importLoansFromTemplate(uploadedInputStream);
        else if (entityType.equals("loanrepayments"))
            response = this.importPlatformService.importLoanRepaymentFromTemplate(uploadedInputStream);
        else if (entityType.equals("offices"))
            response = this.importPlatformService.importOfficesFromTemplate(uploadedInputStream);
        else if (entityType.equals("codes"))
            response = this.importPlatformService.importCodesFromTemplate(uploadedInputStream);
        else if (entityType.equals("codevalues"))
            response = this.importPlatformService.importCodeValuesFromTemplate(uploadedInputStream);
        else if (entityType.equals("staff"))
            response = this.importPlatformService.importStaffFromTemplate(uploadedInputStream);
        else if (entityType.equals("users"))
            response = this.importPlatformService.importUsersFromTemplate(uploadedInputStream);
        else {

        }

        return response;
    }

    @GET
    @Path("test")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String importTemplate(@PathParam("entityType") final String entityType) {

        return entityType;
    }

}