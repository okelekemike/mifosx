/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.domain.handler.savings;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.mifosplatform.commands.domain.CommandWrapper;
import org.mifosplatform.commands.service.CommandWrapperBuilder;
import org.mifosplatform.commands.service.PortfolioCommandSourceWritePlatformService;
import org.mifosplatform.infrastructure.dataimport.data.Transaction;
import org.mifosplatform.infrastructure.dataimport.domain.handler.AbstractDataImportHandler;
import org.mifosplatform.infrastructure.dataimport.domain.handler.Result;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class SavingsTransactionDataImportHandler extends AbstractDataImportHandler {

    private static final Logger logger = LoggerFactory.getLogger(SavingsTransactionDataImportHandler.class);

    private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;

    private final Workbook workbook;

    private List<Transaction> savingsTransactions;
    private String savingsAccountId = "";

    private static final int SAVINGS_ACCOUNT_NO_COL = 2;
    private static final int TRANSACTION_TYPE_COL = 5;
    private static final int AMOUNT_COL = 6;
    private static final int TRANSACTION_DATE_COL = 7;
    private static final int PAYMENT_TYPE_COL = 8;
    private static final int ACCOUNT_NO_COL = 9;
    private static final int CHECK_NO_COL = 10;
    private static final int ROUTING_CODE_COL = 11;
    private static final int RECEIPT_NO_COL = 12;
    private static final int BANK_NO_COL = 13;
    private static final int STATUS_COL = 13;

    public SavingsTransactionDataImportHandler(Workbook workbook,
            final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService) {

        this.workbook = workbook;
        this.commandsSourceWritePlatformService = commandsSourceWritePlatformService;
        savingsTransactions = new ArrayList<>();
    }

    @Override
    public Result parse() {

        Result result = new Result();
        Sheet savingsTransactionSheet = workbook.getSheet("SavingsTransaction");
        Integer noOfEntries = getNumberOfRows(savingsTransactionSheet, AMOUNT_COL);

        for (int rowIndex = 1; rowIndex < noOfEntries; rowIndex++) {
            Row row;
            try {
                row = savingsTransactionSheet.getRow(rowIndex);
                if (isNotImported(row, STATUS_COL)) savingsTransactions.add(parseAsTransaction(row));
            } catch (Exception e) {
                logger.error("row = " + rowIndex, e);
                result.addError("Row = " + rowIndex + " , " + e.getMessage());
            }
        }
        return result;
    }

    private Transaction parseAsTransaction(Row row) {

        String savingsAccountIdCheck = readAsInt(SAVINGS_ACCOUNT_NO_COL, row);
        if (!savingsAccountIdCheck.equals("")) savingsAccountId = savingsAccountIdCheck;
        String transactionType = readAsString(TRANSACTION_TYPE_COL, row);
        String amount = readAsDouble(AMOUNT_COL, row).toString();
        String transactionDate = readAsDate(TRANSACTION_DATE_COL, row);
        String paymentType = readAsString(PAYMENT_TYPE_COL, row);
        String paymentTypeId = getIdByName(workbook.getSheet("Extras"), paymentType).toString();
        if (paymentTypeId.equals("0")) 
            paymentTypeId = "";
        String accountNumber = readAsLong(ACCOUNT_NO_COL, row);
        String checkNumber = readAsLong(CHECK_NO_COL, row);
        String routingCode = readAsLong(ROUTING_CODE_COL, row);
        String receiptNumber = readAsLong(RECEIPT_NO_COL, row);
        String bankNumber = readAsLong(BANK_NO_COL, row);

        return new Transaction(amount, transactionDate, paymentTypeId, accountNumber, checkNumber, routingCode, receiptNumber, bankNumber,
                Integer.parseInt(savingsAccountId), transactionType, row.getRowNum());
    }

    @Override
    public Result upload() {

        Result result = new Result();
        Sheet savingsTransactionSheet = workbook.getSheet("SavingsTransaction");

        for (Transaction transaction : savingsTransactions) {
            try {
                Gson gson = new Gson();
                String payload = gson.toJson(transaction);
                logger.info("ID: " + transaction.getAccountId() + " : " + payload);

                final CommandWrapperBuilder builder = new CommandWrapperBuilder().withJson(payload);

                String commandParam = transaction.getTransactionType();
                Long accountId = new Long(transaction.getAccountId());

                CommandProcessingResult commandProcessingResult = null;
                if (StringUtils.isNotBlank(commandParam) && commandParam.trim().equalsIgnoreCase("deposit")) {
                    final CommandWrapper commandRequest = builder.savingsAccountDeposit(accountId).build();
                    commandProcessingResult = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);
                } else if (StringUtils.isNotBlank(commandParam) && commandParam.trim().equalsIgnoreCase("withdrawal")) {
                    final CommandWrapper commandRequest = builder.savingsAccountWithdrawal(accountId).build();
                    commandProcessingResult = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);
                }
                if (commandProcessingResult != null) logger.info(commandProcessingResult.toString());

                Cell statusCell = savingsTransactionSheet.getRow(transaction.getRowIndex()).createCell(STATUS_COL);
                statusCell.setCellValue("Imported");
                statusCell.setCellStyle(getCellStyle(workbook, IndexedColors.LIGHT_GREEN));

            } catch (Exception e) {

                Cell savingsAccountIdCell = savingsTransactionSheet.getRow(transaction.getRowIndex()).createCell(SAVINGS_ACCOUNT_NO_COL);
                savingsAccountIdCell.setCellValue(transaction.getAccountId());
                String message = parseStatus(e.getMessage());

                Cell statusCell = savingsTransactionSheet.getRow(transaction.getRowIndex()).createCell(STATUS_COL);
                statusCell.setCellValue(message);
                statusCell.setCellStyle(getCellStyle(workbook, IndexedColors.RED));
                result.addError("Row = " + transaction.getRowIndex() + " ," + message);
            }
        }
        savingsTransactionSheet.setColumnWidth(STATUS_COL, 15000);
        writeString(STATUS_COL, savingsTransactionSheet.getRow(0), "Status");

        return result;
    }

    public List<Transaction> getSavingsTransactions() {
        return savingsTransactions;
    }

}
