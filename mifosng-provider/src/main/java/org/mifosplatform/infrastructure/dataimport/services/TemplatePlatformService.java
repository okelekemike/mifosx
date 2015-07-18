/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.services;

import javax.ws.rs.core.Response;

public interface TemplatePlatformService {

    public Response getClientImportTemplate(int clientTypeId);

    public Response getGroupImportTemplate();

    public Response getCenterImportTemplate();

    public Response getLoanImportTemplate();

    public Response getLoanRepaymentImportTemplate();

    public Response getSavingImportTemplate();

    public Response getSavingsTransactionImportTemplate();

    public Response getOfficeImportTemplate();

    public Response getCodeImportTemplate();

    public Response getCodeValueImportTemplate();

    public Response getStaffImportTemplate();

    public Response getUserImportTemplate();
}