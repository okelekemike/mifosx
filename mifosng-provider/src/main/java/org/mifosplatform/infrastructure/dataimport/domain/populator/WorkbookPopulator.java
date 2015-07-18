/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.domain.populator;

import org.apache.poi.ss.usermodel.Workbook;
import org.mifosplatform.infrastructure.dataimport.domain.handler.Result;

public interface WorkbookPopulator {

    Result downloadAndParse();

    Result populate(Workbook workbook);

}