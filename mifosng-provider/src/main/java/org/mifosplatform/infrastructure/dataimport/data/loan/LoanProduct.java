/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.data.loan;

import java.util.ArrayList;

import org.joda.time.DateTimeFieldType;
import org.joda.time.LocalDate;
import org.mifosplatform.infrastructure.dataimport.data.Type;
import org.mifosplatform.portfolio.loanproduct.data.LoanProductData;

public class LoanProduct {

    private final Integer id;

    private final String name;

    private final String fundName;

    private final Integer principal;

    private final Integer minPrincipal;

    private final Integer maxPrincipal;

    private final Integer numberOfRepayments;

    private final Integer minNumberOfRepayments;

    private final Integer maxNumberOfRepayments;

    private final Integer repaymentEvery;

    private final Type repaymentFrequencyType;

    private final Integer interestRatePerPeriod;

    private final Integer minInterestRatePerPeriod;

    private final Integer maxInterestRatePerPeriod;

    private final Type interestRateFrequencyType;

    private final Type amortizationType;

    private final Type interestType;

    private final Type interestCalculationPeriodType;

    private final Integer inArrearsTolerance;

    private final String transactionProcessingStrategyName;

    private final Integer graceOnPrincipalPayment;

    private final Integer graceOnInterestPayment;

    private final Integer graceOnInterestCharged;

    private final String status;

    private final ArrayList<Integer> startDate;

    private final ArrayList<Integer> closeDate;

    public LoanProduct(Integer id, String name, String fundName, String status, Integer principal, Integer minPrincipal,
            Integer maxPrincipal, Integer numberOfRepayments, Integer minNumberOfRepayments, Integer maxNumberOfRepayments,
            Integer repaymentEvery, Type repaymentFrequencyType, Integer interestRatePerPeriod, Integer minInterestRatePerPeriod,
            Integer maxInterestRatePerPeriod, Type interestRateFrequencyType, Type amortizationType, Type interestType,
            Type interestCalculationPeriodType, Integer inArrearsTolerance, String transactionProcessingStrategyName,
            Integer graceOnPrincipalPayment, Integer graceOnInterestPayment, Integer graceOnInterestCharged, ArrayList<Integer> startDate,
            ArrayList<Integer> closeDate) {

        this.id = id;
        this.name = name;
        this.fundName = fundName;
        this.status = status;
        this.principal = principal;
        this.minPrincipal = minPrincipal;
        this.maxPrincipal = maxPrincipal;
        this.numberOfRepayments = numberOfRepayments;
        this.minNumberOfRepayments = minNumberOfRepayments;
        this.maxNumberOfRepayments = maxNumberOfRepayments;
        this.repaymentEvery = repaymentEvery;
        this.repaymentFrequencyType = repaymentFrequencyType;
        this.interestRatePerPeriod = interestRatePerPeriod;
        this.minInterestRatePerPeriod = minInterestRatePerPeriod;
        this.maxInterestRatePerPeriod = maxInterestRatePerPeriod;
        this.interestRateFrequencyType = interestRateFrequencyType;
        this.amortizationType = amortizationType;
        this.interestType = interestType;
        this.interestCalculationPeriodType = interestCalculationPeriodType;
        this.inArrearsTolerance = inArrearsTolerance;
        this.transactionProcessingStrategyName = transactionProcessingStrategyName;
        this.graceOnPrincipalPayment = graceOnPrincipalPayment;
        this.graceOnInterestPayment = graceOnInterestPayment;
        this.graceOnInterestCharged = graceOnInterestCharged;
        this.startDate = startDate;
        this.closeDate = closeDate;
    }

    public LoanProduct(LoanProductData aLoanProductData) {

        this.id = aLoanProductData.getId() != null ? aLoanProductData.getId().intValue() : null;
        this.name = aLoanProductData.getName();
        this.fundName = aLoanProductData.getFundName();
        this.status = aLoanProductData.getStatus();
        this.principal = aLoanProductData.getPrincipal() != null ? aLoanProductData.getPrincipal().intValue() : null;
        this.minPrincipal = aLoanProductData.getMinPrincipal() != null ? aLoanProductData.getMinPrincipal().intValue() : null;
        this.maxPrincipal = aLoanProductData.getMaxPrincipal() != null ? aLoanProductData.getMaxPrincipal().intValue() : null;
        this.numberOfRepayments = aLoanProductData.getNumberOfRepayments();
        this.minNumberOfRepayments = aLoanProductData.getMinNumberOfRepayments();
        this.maxNumberOfRepayments = aLoanProductData.getMaxNumberOfRepayments();
        this.repaymentEvery = aLoanProductData.getRepaymentEvery();
        this.repaymentFrequencyType = new Type(aLoanProductData.getRepaymentFrequencyType());
        this.interestRatePerPeriod = aLoanProductData.getInterestRatePerPeriod() != null ? aLoanProductData.getInterestRatePerPeriod()
                .intValue() : null;
        this.minInterestRatePerPeriod = aLoanProductData.getMinInterestRatePerPeriod() != null ? aLoanProductData
                .getMinInterestRatePerPeriod().intValue() : null;
        this.maxInterestRatePerPeriod = aLoanProductData.getMaxInterestRatePerPeriod() != null ? aLoanProductData
                .getMaxInterestRatePerPeriod().intValue() : null;
        this.interestRateFrequencyType = new Type(aLoanProductData.getInterestRateFrequencyType());
        this.amortizationType = new Type(aLoanProductData.getAmortizationType());
        this.interestType = new Type(aLoanProductData.getInterestType());
        this.interestCalculationPeriodType = new Type(aLoanProductData.getInterestCalculationPeriodType());
        this.inArrearsTolerance = aLoanProductData.getInArrearsTolerance() != null ? aLoanProductData.getInArrearsTolerance().intValue()
                : null;
        this.transactionProcessingStrategyName = aLoanProductData.getTransactionProcessingStrategyName();
        this.graceOnPrincipalPayment = aLoanProductData.getGraceOnPrincipalPayment();
        this.graceOnInterestPayment = aLoanProductData.getGraceOnInterestPayment();
        this.graceOnInterestCharged = aLoanProductData.getGraceOnInterestCharged();

        LocalDate startDate = aLoanProductData.getStartDate();
        this.startDate = new ArrayList<>();
        if (startDate != null) {
            this.startDate.add(startDate.getYear());
            this.startDate.add(startDate.get(DateTimeFieldType.monthOfYear()));
            this.startDate.add(startDate.getDayOfMonth());
        }

        LocalDate closeDate = aLoanProductData.getCloseDate();
        this.closeDate = new ArrayList<>();
        if (closeDate != null) {
            this.closeDate.add(closeDate.getYear());
            this.closeDate.add(closeDate.get(DateTimeFieldType.monthOfYear()));
            this.closeDate.add(closeDate.getDayOfMonth());
        }
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getFundName() {
        return this.fundName;
    }

    public String getStatus() {
        return this.status;
    }

    public Integer getPrincipal() {
        return this.principal;
    }

    public Integer getMinPrincipal() {
        return this.minPrincipal;
    }

    public Integer getMaxPrincipal() {
        return this.maxPrincipal;
    }

    public Integer getNumberOfRepayments() {
        return this.numberOfRepayments;
    }

    public Integer getMinNumberOfRepayments() {
        return this.minNumberOfRepayments;
    }

    public Integer getMaxNumberOfRepayments() {
        return this.maxNumberOfRepayments;
    }

    public Integer getRepaymentEvery() {
        return this.repaymentEvery;
    }

    public Type getRepaymentFrequencyType() {
        return this.repaymentFrequencyType;
    }

    public Integer getInterestRatePerPeriod() {
        return this.interestRatePerPeriod;
    }

    public Integer getMinInterestRatePerPeriod() {
        return this.minInterestRatePerPeriod;
    }

    public Integer getMaxInterestRatePerPeriod() {
        return this.maxInterestRatePerPeriod;
    }

    public Type getInterestRateFrequencyType() {
        return this.interestRateFrequencyType;
    }

    public Type getAmortizationType() {
        return this.amortizationType;
    }

    public Type getInterestType() {
        return this.interestType;
    }

    public Type getInterestCalculationPeriodType() {
        return this.interestCalculationPeriodType;
    }

    public Integer getInArrearsTolerance() {
        return this.inArrearsTolerance;
    }

    public String getTransactionProcessingStrategyName() {
        return this.transactionProcessingStrategyName;
    }

    public Integer getGraceOnPrincipalPayment() {
        return this.graceOnPrincipalPayment;
    }

    public Integer getGraceOnInterestPayment() {
        return this.graceOnInterestPayment;
    }

    public Integer getGraceOnInterestCharged() {
        return this.graceOnInterestCharged;
    }

    public ArrayList<Integer> getStartDate() {
        return this.startDate;
    }

    public ArrayList<Integer> getCloseDate() {
        return this.closeDate;
    }
}
