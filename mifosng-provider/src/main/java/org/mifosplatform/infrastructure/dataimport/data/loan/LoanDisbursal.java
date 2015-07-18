/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.data.loan;

import java.util.Locale;

public class LoanDisbursal {

    private final transient Integer rowIndex;

    private final String actualDisbursementDate;

    private final String paymentTypeId;

    private final String dateFormat;

    private final Locale locale;

    private final String note;

    private final String accountNumber;

    private final String routingCode;

    private final String receiptNumber;

    private final String bankNumber;

    private final String checkNumber;

    public LoanDisbursal(String actualDisbursementDate, String paymentTypeId, Integer rowIndex) {

        this.actualDisbursementDate = actualDisbursementDate;
        this.paymentTypeId = paymentTypeId;
        this.rowIndex = rowIndex;
        this.dateFormat = "dd MMMM yyyy";
        this.locale = Locale.ENGLISH;
        this.note = "";
        this.accountNumber = "";
        this.routingCode = "";
        this.receiptNumber = "";
        this.bankNumber = "";
        this.checkNumber = "";
    }

    public String getActualDisbursementDate() {
        return actualDisbursementDate;
    }

    public String getPaymentTypeId() {
        return paymentTypeId;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public Integer getRowIndex() {
        return rowIndex;
    }

    public String getNote() {
        return note;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getRoutingCode() {
        return routingCode;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public String getCheckNumber() {
        return checkNumber;
    }
}
