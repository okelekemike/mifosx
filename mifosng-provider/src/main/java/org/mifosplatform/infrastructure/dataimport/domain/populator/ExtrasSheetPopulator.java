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
import org.mifosplatform.infrastructure.dataimport.data.PaymentType;
import org.mifosplatform.infrastructure.dataimport.data.loan.Fund;
import org.mifosplatform.infrastructure.dataimport.domain.handler.Result;
import org.mifosplatform.infrastructure.codes.data.CodeValueData;
import org.mifosplatform.infrastructure.codes.service.CodeValueReadPlatformService;
import org.mifosplatform.portfolio.fund.data.FundData;
import org.mifosplatform.portfolio.fund.service.FundReadPlatformService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtrasSheetPopulator extends AbstractWorkbookPopulator {

    private static final Logger logger = LoggerFactory.getLogger(ExtrasSheetPopulator.class);

    private final FundReadPlatformService fundReadPlatformService;
    private final CodeValueReadPlatformService codeValueReadPlatformService;

    private final Long paymentTypeCodeId;

    private List<Fund> funds;
    private List<PaymentType> paymentTypes;

    private static final int FUND_ID_COL = 0;
    private static final int FUND_NAME_COL = 1;
    private static final int PAYMENT_TYPE_ID_COL = 2;
    private static final int PAYMENT_TYPE_NAME_COL = 3;

    public ExtrasSheetPopulator(final FundReadPlatformService fundReadPlatformService,
            final CodeValueReadPlatformService codeValueReadPlatformService) {

        this.fundReadPlatformService = fundReadPlatformService;
        this.codeValueReadPlatformService = codeValueReadPlatformService;

        paymentTypeCodeId = new Long(12);
    }

    @Override
    public Result downloadAndParse() {
        Result result = new Result();
        try {

            final Collection<FundData> fundsCollection = this.fundReadPlatformService.retrieveAllFunds();

            funds = new ArrayList<>();
            for (FundData aFundData : fundsCollection) {

                funds.add(new Fund(aFundData));
            }

            final Collection<CodeValueData> paymentCodeValues = this.codeValueReadPlatformService.retrieveAllCodeValues(paymentTypeCodeId);

            paymentTypes = new ArrayList<>();
            for (CodeValueData aCodeValueData : paymentCodeValues) {

                paymentTypes.add(new PaymentType(aCodeValueData));
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
            int fundRowIndex = 1;
            Sheet extrasSheet = workbook.createSheet("Extras");
            setLayout(extrasSheet);

            for (Fund fund : funds) {
                Row row = extrasSheet.createRow(fundRowIndex++);
                writeInt(FUND_ID_COL, row, fund.getId());
                writeString(FUND_NAME_COL, row, fund.getName());
            }

            int paymentTypeRowIndex = 1;

            for (PaymentType paymentType : paymentTypes) {
                Row row;

                if (paymentTypeRowIndex < fundRowIndex)
                    row = extrasSheet.getRow(paymentTypeRowIndex++);
                else
                    row = extrasSheet.createRow(paymentTypeRowIndex++);

                writeInt(PAYMENT_TYPE_ID_COL, row, paymentType.getId());
                writeString(PAYMENT_TYPE_NAME_COL, row, paymentType.getName().trim().replaceAll("[ )(]", "_"));
            }

            extrasSheet.protectSheet("");

        } catch (Exception e) {
            result.addError(e.getMessage());
            logger.error(e.getMessage());
        }
        return result;
    }

    private void setLayout(Sheet worksheet) {

        worksheet.setColumnWidth(FUND_ID_COL, 4000);
        worksheet.setColumnWidth(FUND_NAME_COL, 7000);
        worksheet.setColumnWidth(PAYMENT_TYPE_ID_COL, 4000);
        worksheet.setColumnWidth(PAYMENT_TYPE_NAME_COL, 7000);

        Row rowHeader = worksheet.createRow(0);
        rowHeader.setHeight((short) 500);

        writeString(FUND_ID_COL, rowHeader, "Fund ID");
        writeString(FUND_NAME_COL, rowHeader, "Name");
        writeString(PAYMENT_TYPE_ID_COL, rowHeader, "Payment Type ID");
        writeString(PAYMENT_TYPE_NAME_COL, rowHeader, "Payment Type Name");
    }

    public Integer getFundsSize() {
        return funds.size();
    }

    public Integer getPaymentTypesSize() {
        return paymentTypes.size();
    }

    public List<Fund> getFunds() {
        return funds;
    }

    public List<PaymentType> getPaymentTypes() {
        return paymentTypes;
    }
}
