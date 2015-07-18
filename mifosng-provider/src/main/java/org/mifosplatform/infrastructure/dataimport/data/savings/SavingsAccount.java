/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.data.savings;

import java.util.Locale;

public class SavingsAccount {

    private final transient Integer rowIndex;

    private final transient String status;

    private final String clientId;

    private final String fieldOfficerId;

    private final String productId;

    private final String submittedOnDate;

    private final String nominalAnnualInterestRate;

    private final String interestCompoundingPeriodType;

    private final String interestPostingPeriodType;

    private final String interestCalculationType;

    private final String interestCalculationDaysInYearType;

    private final String minRequiredOpeningBalance;

    private final String lockinPeriodFrequency;

    private final String lockinPeriodFrequencyType;

    private final String withdrawalFeeForTransfers;

    private final String monthDayFormat;

    private final String dateFormat;

    private final Locale locale;

    public SavingsAccount(String clientId, String productId, String fieldOfficerId, String submittedOnDate,
            String nominalAnnualInterestRate, String interestCompoundingPeriodType, String interestPostingPeriodType,
            String interestCalculationType, String interestCalculationDaysInYearType, String minRequiredOpeningBalance,
            String lockinPeriodFrequency, String lockinPeriodFrequencyType, String withdrawalFeeForTransfers, Integer rowIndex,
            String status) {

        this.clientId = clientId;
        this.productId = productId;
        this.fieldOfficerId = fieldOfficerId;
        this.submittedOnDate = submittedOnDate;
        this.nominalAnnualInterestRate = nominalAnnualInterestRate;
        this.interestCompoundingPeriodType = interestCompoundingPeriodType;
        this.interestPostingPeriodType = interestPostingPeriodType;
        this.interestCalculationType = interestCalculationType;
        this.interestCalculationDaysInYearType = interestCalculationDaysInYearType;
        this.minRequiredOpeningBalance = minRequiredOpeningBalance;
        this.lockinPeriodFrequency = lockinPeriodFrequency;
        this.lockinPeriodFrequencyType = lockinPeriodFrequencyType;
        this.withdrawalFeeForTransfers = withdrawalFeeForTransfers;
        this.rowIndex = rowIndex;
        this.status = status;
        this.dateFormat = "dd MMMM yyyy";
        this.locale = Locale.ENGLISH;
        this.monthDayFormat = "dd MMM";
    }

    public String getClientId() {
        return clientId;
    }

    public String getFieldOfficerId() {
        return fieldOfficerId;
    }

    public String getProductId() {
        return productId;
    }

    public String getNominalAnnualInterestRate() {
        return nominalAnnualInterestRate;
    }

    public String getInterestCompoundingPeriodType() {
        return interestCompoundingPeriodType;
    }

    public String getInterestPostingPeriodType() {
        return interestPostingPeriodType;
    }

    public String getInterestCalculationType() {
        return interestCalculationType;
    }

    public String getInterestCalculationDaysInYearType() {
        return interestCalculationDaysInYearType;
    }

    public String getMinRequiredOpeningBalance() {
        return minRequiredOpeningBalance;
    }

    public String getLockinPeriodFrequency() {
        return lockinPeriodFrequency;
    }

    public String getLockinPeriodFrequencyType() {
        return lockinPeriodFrequencyType;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public String getMonthDayFormat() {
        return monthDayFormat;
    }

    public Locale getLocale() {
        return locale;
    }

    public Integer getRowIndex() {
        return rowIndex;
    }

    public String getStatus() {
        return status;
    }

    public String getSubmittedOnDate() {
        return submittedOnDate;
    }

    public String getWithdrawalFeeForTransfers() {
        return withdrawalFeeForTransfers;
    }
}
