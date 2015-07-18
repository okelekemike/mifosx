/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.domain.populator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.mifosplatform.infrastructure.dataimport.data.Office;
import org.mifosplatform.infrastructure.dataimport.domain.handler.Result;
import org.mifosplatform.organisation.office.data.OfficeData;
import org.mifosplatform.organisation.office.service.OfficeReadPlatformService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfficeSheetPopulator extends AbstractWorkbookPopulator {

    private static final Logger logger = LoggerFactory.getLogger(OfficeSheetPopulator.class);

    private List<Office> offices;
    private ArrayList<String> officeNames;

    private static final int ID_COL = 0;
    private static final int OFFICE_NAME_COL = 1;

    private final OfficeReadPlatformService officeReadPlatformService;

    public OfficeSheetPopulator(final OfficeReadPlatformService officeReadPlatformService) {

        this.officeReadPlatformService = officeReadPlatformService;
    }

    @Override
    public Result downloadAndParse() {

        Result result = new Result();

        try {

            Collection<OfficeData> officesCollection = this.officeReadPlatformService.retrieveAllOffices(false, null);

            offices = new ArrayList<>();
            officeNames = new ArrayList<>();

            for (OfficeData aOfficeData : officesCollection) {
                offices.add(new Office(aOfficeData));
                officeNames.add(aOfficeData.name());
            }

        } catch (Exception e) {
            result.addError(e.getMessage());
            logger.error(e.getMessage());
        }
        return result;
    }

    @Override
    public Result populate(Workbook workbook) {

        Result result = new Result();

        try {
            int rowIndex = 1;

            Sheet officeSheet = workbook.createSheet("Offices");
            setLayout(officeSheet);

            populateOffices(officeSheet, rowIndex);
            officeSheet.protectSheet("");

        } catch (Exception e) {
            result.addError(e.getMessage());
            logger.error(e.getMessage());
        }
        return result;
    }

    private void populateOffices(Sheet officeSheet, int rowIndex) {

        for (Office office : offices) {
            Row row = officeSheet.createRow(rowIndex);
            writeInt(ID_COL, row, office.getId());
            writeString(OFFICE_NAME_COL, row, office.getName().trim().replaceAll("[ )(]", "_"));
            rowIndex++;
        }
    }

    private void setLayout(Sheet worksheet) {

        worksheet.setColumnWidth(ID_COL, 2000);
        worksheet.setColumnWidth(OFFICE_NAME_COL, 7000);

        Row rowHeader = worksheet.createRow(0);
        rowHeader.setHeight((short) 500);

        writeString(ID_COL, rowHeader, "ID");
        writeString(OFFICE_NAME_COL, rowHeader, "Name");
    }

    public List<Office> getOffices() {
        return offices;
    }

    public String[] getOfficeNames() {
        return officeNames.toArray(new String[officeNames.size()]);
    }

}
