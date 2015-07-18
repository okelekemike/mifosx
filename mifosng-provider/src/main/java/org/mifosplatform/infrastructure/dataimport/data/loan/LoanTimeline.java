/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.data.loan;

import java.util.ArrayList;

import org.joda.time.DateTimeFieldType;
import org.joda.time.LocalDate;
import org.mifosplatform.portfolio.loanaccount.data.LoanApplicationTimelineData;

public class LoanTimeline {

    private final ArrayList<Integer> actualDisbursementDate;

    public LoanTimeline(ArrayList<Integer> actualDisbursementDate) {
        this.actualDisbursementDate = actualDisbursementDate;
    }

    public LoanTimeline(LoanApplicationTimelineData timeline) {

        LocalDate actualDisbursementDate = timeline.getActualDisbursementDate();

        this.actualDisbursementDate = new ArrayList<>();
        if (actualDisbursementDate != null){
            this.actualDisbursementDate.add(actualDisbursementDate.getYear());
            this.actualDisbursementDate.add(actualDisbursementDate.get(DateTimeFieldType.monthOfYear()));
            this.actualDisbursementDate.add(actualDisbursementDate.getDayOfMonth());
        }
    }

    public ArrayList<Integer> getActualDisbursementDate() {
        return this.actualDisbursementDate;
    }

}
