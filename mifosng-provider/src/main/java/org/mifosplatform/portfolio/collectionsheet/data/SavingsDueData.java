/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.portfolio.collectionsheet.data;

import java.math.BigDecimal;

import org.mifosplatform.organisation.monetary.data.CurrencyData;

/**
 * Immutable data object for representing loan with dues (example: loan is due
 * for disbursement, repayments).
 */
public class SavingsDueData {

    @SuppressWarnings("unused")
    private final Long savingsId;
    @SuppressWarnings("unused")
    private final String accountId;
    @SuppressWarnings("unused")
    private final Integer accountStatusId;
    private final Integer accountTypeId;
    private final Integer productDepositType;
    private final String productName;
    private final Long productId;
    @SuppressWarnings("unused")
    private final CurrencyData currency;
    @SuppressWarnings("unused")
    private BigDecimal dueAmount = BigDecimal.ZERO;
    @SuppressWarnings("unused")
    private BigDecimal withdrawalAmount = BigDecimal.ZERO;

    public static SavingsDueData instance(final Long savingsId, final String accountId, final Integer accountStatusId, final Integer accountTypeId, final Integer productDepositType,
            final String productName, final Long productId, final CurrencyData currency, final BigDecimal dueAmount, final BigDecimal withdrawalAmount) {
        return new SavingsDueData(savingsId, accountId, accountStatusId, accountTypeId, productDepositType, productName, productId, currency, dueAmount, withdrawalAmount);
    }

    private SavingsDueData(final Long savingsId, final String accountId, final Integer accountStatusId, final Integer accountTypeId, final Integer productDepositType, final String productName,
            final Long productId, final CurrencyData currency, final BigDecimal dueAmount, final BigDecimal withdrawalAmount) {
        this.savingsId = savingsId;
        this.accountId = accountId;
        this.accountStatusId = accountStatusId;
        this.accountTypeId = accountTypeId;
        this.productDepositType = productDepositType;
        this.productName = productName;
        this.productId = productId;
        this.currency = currency;
        this.dueAmount = dueAmount;
        this.withdrawalAmount = withdrawalAmount;
    }
    
    public Integer productDepositType() {
        return this.productDepositType;
    }
    
    public String productName() {
        return this.productName;
    }
    
    public Long productId() {
        return this.productId;
    }
    
}