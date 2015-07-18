/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.domain.populator.savings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.mifosplatform.infrastructure.dataimport.data.Currency;
import org.mifosplatform.infrastructure.dataimport.data.savings.SavingsProduct;
import org.mifosplatform.infrastructure.dataimport.domain.handler.Result;
import org.mifosplatform.infrastructure.dataimport.domain.populator.AbstractWorkbookPopulator;
import org.mifosplatform.portfolio.savings.data.SavingsProductData;
import org.mifosplatform.portfolio.savings.service.SavingsProductReadPlatformService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SavingsProductSheetPopulator extends AbstractWorkbookPopulator {

    private static final Logger logger = LoggerFactory.getLogger(SavingsProductSheetPopulator.class);

    private final SavingsProductReadPlatformService savingsProductReadPlatformService;

    private static final int ID_COL = 0;
    private static final int NAME_COL = 1;
    private static final int NOMINAL_ANNUAL_INTEREST_RATE_COL = 2;
    private static final int INTEREST_COMPOUNDING_PERIOD_COL = 3;
    private static final int INTEREST_POSTING_PERIOD_COL = 4;
    private static final int INTEREST_CALCULATION_COL = 5;
    private static final int INTEREST_CALCULATION_DAYS_IN_YEAR_COL = 6;
    private static final int MIN_OPENING_BALANCE_COL = 7;
    private static final int LOCKIN_PERIOD_COL = 8;
    private static final int LOCKIN_PERIOD_FREQUENCY_COL = 9;
    private static final int CURRENCY_COL = 10;
    private static final int DECIMAL_PLACES_COL = 11;
    private static final int IN_MULTIPLES_OF_COL = 12;

    private List<SavingsProduct> products;

    public SavingsProductSheetPopulator(final SavingsProductReadPlatformService savingsProductReadPlatformService) {
        this.savingsProductReadPlatformService = savingsProductReadPlatformService;
    }

    @Override
    public Result downloadAndParse() {

        Result result = new Result();

        try {

            final Collection<SavingsProductData> productsCollection = this.savingsProductReadPlatformService.retrieveAll();

            products = new ArrayList<>();
            for (SavingsProductData aSavingsProductData : productsCollection) {

                products.add(new SavingsProduct(aSavingsProductData));
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
            Sheet productSheet = workbook.createSheet("Products");
            setLayout(productSheet);
            CellStyle dateCellStyle = workbook.createCellStyle();
            short df = workbook.createDataFormat().getFormat("dd-mmm");
            dateCellStyle.setDataFormat(df);

            for (SavingsProduct product : products) {

                Row row = productSheet.createRow(rowIndex++);
                writeInt(ID_COL, row, product.getId());
                writeString(NAME_COL, row, product.getName().trim().replaceAll("[ )(]", "_"));
                writeDouble(NOMINAL_ANNUAL_INTEREST_RATE_COL, row, product.getNominalAnnualInterestRate());
                writeString(INTEREST_COMPOUNDING_PERIOD_COL, row, product.getInterestCompoundingPeriodType().getValue());
                writeString(INTEREST_POSTING_PERIOD_COL, row, product.getInterestPostingPeriodType().getValue());
                writeString(INTEREST_CALCULATION_COL, row, product.getInterestCalculationType().getValue());
                writeString(INTEREST_CALCULATION_DAYS_IN_YEAR_COL, row, product.getInterestCalculationDaysInYearType().getValue());

                if (product.getMinRequiredOpeningBalance() != null)
                    writeDouble(MIN_OPENING_BALANCE_COL, row, product.getMinRequiredOpeningBalance());

                if (product.getLockinPeriodFrequency() != null) writeInt(LOCKIN_PERIOD_COL, row, product.getLockinPeriodFrequency());

                if (product.getLockinPeriodFrequencyType() != null)
                    writeString(LOCKIN_PERIOD_FREQUENCY_COL, row, product.getLockinPeriodFrequencyType().getValue());

                Currency currency = product.getCurrency();
                writeString(CURRENCY_COL, row, currency.getCode());
                writeInt(DECIMAL_PLACES_COL, row, currency.getDecimalPlaces());

                if (currency.getInMultiplesOf() != null) writeInt(IN_MULTIPLES_OF_COL, row, currency.getInMultiplesOf());
            }
            productSheet.protectSheet("");

        } catch (RuntimeException re) {
            result.addError(re.getMessage());
            logger.error(re.getMessage());
        }
        return result;
    }

    private void setLayout(Sheet worksheet) {

        Row rowHeader = worksheet.createRow(0);
        rowHeader.setHeight((short) 500);

        worksheet.setColumnWidth(ID_COL, 2000);
        worksheet.setColumnWidth(NAME_COL, 5000);
        worksheet.setColumnWidth(NOMINAL_ANNUAL_INTEREST_RATE_COL, 2000);
        worksheet.setColumnWidth(INTEREST_COMPOUNDING_PERIOD_COL, 3000);
        worksheet.setColumnWidth(INTEREST_POSTING_PERIOD_COL, 3000);
        worksheet.setColumnWidth(INTEREST_CALCULATION_COL, 3000);
        worksheet.setColumnWidth(INTEREST_CALCULATION_DAYS_IN_YEAR_COL, 3000);
        worksheet.setColumnWidth(MIN_OPENING_BALANCE_COL, 3000);
        worksheet.setColumnWidth(LOCKIN_PERIOD_COL, 3000);
        worksheet.setColumnWidth(LOCKIN_PERIOD_FREQUENCY_COL, 3000);
        worksheet.setColumnWidth(CURRENCY_COL, 2000);
        worksheet.setColumnWidth(DECIMAL_PLACES_COL, 3000);
        worksheet.setColumnWidth(IN_MULTIPLES_OF_COL, 3500);

        writeString(ID_COL, rowHeader, "ID");
        writeString(NAME_COL, rowHeader, "Name");
        writeString(NOMINAL_ANNUAL_INTEREST_RATE_COL, rowHeader, "Interest");
        writeString(INTEREST_COMPOUNDING_PERIOD_COL, rowHeader, "Interest Compounding Period");
        writeString(INTEREST_POSTING_PERIOD_COL, rowHeader, "Interest Posting Period");
        writeString(INTEREST_CALCULATION_COL, rowHeader, "Interest Calculated Using");
        writeString(INTEREST_CALCULATION_DAYS_IN_YEAR_COL, rowHeader, "# Days In Year");
        writeString(MIN_OPENING_BALANCE_COL, rowHeader, "Min Opening Balance");
        writeString(LOCKIN_PERIOD_COL, rowHeader, "Locked In For");
        writeString(LOCKIN_PERIOD_FREQUENCY_COL, rowHeader, "Frequency");
        writeString(CURRENCY_COL, rowHeader, "Currency");
        writeString(DECIMAL_PLACES_COL, rowHeader, "Decimal Places");
        writeString(IN_MULTIPLES_OF_COL, rowHeader, "In Multiples Of");
    }

    public List<SavingsProduct> getProducts() {
        return products;
    }

    public Integer getProductsSize() {
        return products.size();
    }
}
