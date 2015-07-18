/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.domain.populator.code;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.mifosplatform.infrastructure.dataimport.domain.handler.Result;
import org.mifosplatform.infrastructure.dataimport.domain.populator.AbstractWorkbookPopulator;

public class CodeWorkbookPopulator extends AbstractWorkbookPopulator {

    private static final int NAME_COL = 0;
    private static final int STATUS_COL = 3;
    private static final int FAILURE_COL = 7;

    public CodeWorkbookPopulator() {

    }

    @Override
    public Result downloadAndParse() {

        return new Result();
    }

    @Override
    public Result populate(Workbook workbook) {

        Sheet codeSheet = workbook.createSheet("Codes");

        setLayout(codeSheet);

        return new Result();
    }

    private void setLayout(Sheet worksheet) {

        Row rowHeader = worksheet.createRow(0);
        rowHeader.setHeight((short) 500);

        worksheet.setColumnWidth(NAME_COL, 5000);
        worksheet.setColumnWidth(STATUS_COL, 2000);
        worksheet.setColumnWidth(FAILURE_COL, 2000);

        writeString(NAME_COL, rowHeader, "Code Name*");
    }
}
