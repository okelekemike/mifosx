/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.data.savings;

import java.util.ArrayList;

import org.mifosplatform.infrastructure.dataimport.data.Currency;
import org.mifosplatform.infrastructure.dataimport.data.Type;
import org.mifosplatform.portfolio.savings.data.SavingsProductData;

public class SavingsProduct {

    private final Integer id;

    private final String name;

    private final Currency currency;

    private final Double nominalAnnualInterestRate;

    private final Type interestCompoundingPeriodType;

    private final Type interestPostingPeriodType;

    private final Type interestCalculationType;

    private final Type interestCalculationDaysInYearType;

    private final Double minRequiredOpeningBalance;

    private final Integer lockinPeriodFrequency;

    private final Type lockinPeriodFrequencyType;

    private final Double withdrawalFeeAmount;

    private final Type withdrawalFeeType;

    private final Double annualFeeAmount;

    private final ArrayList<Integer> annualFeeOnMonthDay;

    public SavingsProduct(Integer id, String name, Currency currency, Double nominalAnnualInterestRate, Type interestCompoundingPeriodType,
            Type interestPostingPeriodType, Type interestCalculationType, Type interestCalculationDaysInYearType,
            Double minRequiredOpeningBalance, Integer lockinPeriodFrequency, Type lockinPeriodFrequencyType, Double withdrawalFeeAmount,
            Type withdrawalFeeType, Double annualFeeAmount, ArrayList<Integer> annualFeeOnMonthDay) {

        this.id = id;
        this.name = name;
        this.currency = currency;
        this.nominalAnnualInterestRate = nominalAnnualInterestRate;
        this.interestCompoundingPeriodType = interestCompoundingPeriodType;
        this.interestPostingPeriodType = interestPostingPeriodType;
        this.interestCalculationType = interestCalculationType;
        this.interestCalculationDaysInYearType = interestCalculationDaysInYearType;
        this.minRequiredOpeningBalance = minRequiredOpeningBalance;
        this.lockinPeriodFrequency = lockinPeriodFrequency;
        this.lockinPeriodFrequencyType = lockinPeriodFrequencyType;
        this.withdrawalFeeAmount = withdrawalFeeAmount;
        this.withdrawalFeeType = withdrawalFeeType;
        this.annualFeeAmount = annualFeeAmount;
        this.annualFeeOnMonthDay = annualFeeOnMonthDay;
    }

    public SavingsProduct(final SavingsProductData savingsProductData) {

        this.id = savingsProductData.getId() != null ? savingsProductData.getId().intValue() : null;
        this.name = savingsProductData.getName();
        this.currency = new Currency(savingsProductData.getCurrency());
        this.nominalAnnualInterestRate = savingsProductData.getNominalAnnualInterestRate() != null ? savingsProductData
                .getNominalAnnualInterestRate().doubleValue() : null;
        this.interestCompoundingPeriodType = new Type(savingsProductData.getInterestCompoundingPeriodType());
        this.interestPostingPeriodType = new Type(savingsProductData.getInterestPostingPeriodType());
        this.interestCalculationType = new Type(savingsProductData.getInterestCalculationType());
        this.interestCalculationDaysInYearType = new Type(savingsProductData.getInterestCalculationDaysInYearType());
        this.minRequiredOpeningBalance = savingsProductData.getMinRequiredOpeningBalance() != null ? savingsProductData
                .getMinRequiredOpeningBalance().doubleValue() : null;
        this.lockinPeriodFrequency = savingsProductData.getLockinPeriodFrequency();
        this.lockinPeriodFrequencyType = new Type(savingsProductData.getLockinPeriodFrequencyType());

        this.withdrawalFeeAmount = null;
        this.withdrawalFeeType = null;
        this.annualFeeAmount = null;
        this.annualFeeOnMonthDay = new ArrayList<>();
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public Double getNominalAnnualInterestRate() {
        return this.nominalAnnualInterestRate;
    }

    public Type getInterestCompoundingPeriodType() {
        return this.interestCompoundingPeriodType;
    }

    public Type getInterestPostingPeriodType() {
        return this.interestPostingPeriodType;
    }

    public Type getInterestCalculationType() {
        return this.interestCalculationType;
    }

    public Type getInterestCalculationDaysInYearType() {
        return this.interestCalculationDaysInYearType;
    }

    public Double getMinRequiredOpeningBalance() {
        return this.minRequiredOpeningBalance;
    }

    public Integer getLockinPeriodFrequency() {
        return this.lockinPeriodFrequency;
    }

    public Type getLockinPeriodFrequencyType() {
        return this.lockinPeriodFrequencyType;
    }

    public Double getWithdrawalFeeAmount() {
        return this.withdrawalFeeAmount;
    }

    public Type getWithdrawalFeeType() {
        return this.withdrawalFeeType;
    }

    public Double getAnnualFeeAmount() {
        return this.annualFeeAmount;
    }

    public ArrayList<Integer> getAnnualFeeOnMonthDay() {
        return this.annualFeeOnMonthDay;
    }
}
