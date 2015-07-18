/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.data.loan;

import java.util.Comparator;
import java.util.Locale;

import org.mifosplatform.infrastructure.dataimport.data.Status;
import org.mifosplatform.portfolio.loanaccount.data.LoanAccountData;

public class CompactLoan {

    private final String accountNo;

    private final String clientId;

    private final String clientName;

    private final String loanProductName;

    private final Double principal;

    private final LoanTimeline timeline;

    private final Status status;

    public CompactLoan(String accountNo, String clientId, String clientName, String loanProductName, Double principal,
            LoanTimeline timeline, Status status) {

        this.accountNo = accountNo;
        this.clientId = clientId;
        this.clientName = clientName;
        this.loanProductName = loanProductName;
        this.principal = principal;
        this.timeline = timeline;
        this.status = status;
    }

    public CompactLoan(LoanAccountData aLoanAccountData) {

        this.accountNo = aLoanAccountData.getAccountNo();
        this.clientId = aLoanAccountData.clientId() != null ? aLoanAccountData.clientId().toString() : null;
        this.clientName = aLoanAccountData.getClientName();
        this.loanProductName = aLoanAccountData.getLoanProductName();
        this.principal = aLoanAccountData.getPrincipal() != null ? aLoanAccountData.getPrincipal().doubleValue() : null;
        this.timeline = new LoanTimeline(aLoanAccountData.getTimeline());
        this.status = new Status(aLoanAccountData.getStatus().isActive());
    }

    public String getAccountNo() {
        return this.accountNo;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientName() {
        return this.clientName;
    }

    public String getLoanProductName() {
        return this.loanProductName;
    }

    public Double getPrincipal() {
        return this.principal;
    }

    public Boolean isActive() {
        return this.status.isActive();
    }

    public LoanTimeline getTimeline() {
        return timeline;
    }

    public static final Comparator<CompactLoan> ClientNameComparator = new Comparator<CompactLoan>() {

        @Override
        public int compare(CompactLoan loan1, CompactLoan loan2) {
            String clientOfLoan1 = loan1.getClientName().toUpperCase(Locale.ENGLISH);
            String clientOfLoan2 = loan2.getClientName().toUpperCase(Locale.ENGLISH);
            return clientOfLoan1.compareTo(clientOfLoan2);
        }
    };
}
