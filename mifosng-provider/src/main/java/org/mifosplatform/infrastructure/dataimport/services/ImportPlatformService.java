/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.services;

import java.io.InputStream;
import javax.ws.rs.core.Response;

public interface ImportPlatformService {

    public Response importClientsFromTemplate(InputStream content);

    public Response importGroupsFromTemplate(InputStream content);

    public Response importCentersFromTemplate(InputStream content);

    public Response importLoansFromTemplate(InputStream content);

    public Response importLoanRepaymentFromTemplate(InputStream content);

    public Response importSavingsFromTemplate(InputStream content);

    public Response importSavingsTransactionFromTemplate(InputStream content);

    public Response importOfficesFromTemplate(InputStream content);

    public Response importCodesFromTemplate(InputStream content);

    public Response importCodeValuesFromTemplate(InputStream content);

    public Response importStaffFromTemplate(InputStream content);

    public Response importUsersFromTemplate(InputStream content);

}
