/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.data.savings;

public class GroupSavingsAccount extends SavingsAccount {

    private final String groupId;

    public GroupSavingsAccount(String groupId, String productId, String fieldOfficerId, String submittedOnDate,
            String nominalAnnualInterestRate, String interestCompoundingPeriodType, String interestPostingPeriodType,
            String interestCalculationType, String interestCalculationDaysInYearType, String minRequiredOpeningBalance,
            String lockinPeriodFrequency, String lockinPeriodFrequencyType, String withdrawalFeeForTransfers, Integer rowIndex,
            String status) {

        super(null, productId, fieldOfficerId, submittedOnDate, nominalAnnualInterestRate, interestCompoundingPeriodType,
                interestPostingPeriodType, interestCalculationType, interestCalculationDaysInYearType, minRequiredOpeningBalance,
                lockinPeriodFrequency, lockinPeriodFrequencyType, withdrawalFeeForTransfers, rowIndex, status);
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

}
