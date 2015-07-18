/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.data.savings;

import java.util.ArrayList;

import org.joda.time.DateTimeFieldType;
import org.joda.time.LocalDate;
import org.mifosplatform.portfolio.savings.data.SavingsAccountApplicationTimelineData;

public class SavingsTimeline {

    private final ArrayList<Integer> activatedOnDate;

    public SavingsTimeline(ArrayList<Integer> activatedOnDate) {
        this.activatedOnDate = activatedOnDate;
    }

    public SavingsTimeline(SavingsAccountApplicationTimelineData timeline) {

        LocalDate activatedOnDate = timeline.getActivatedOnDate();

        this.activatedOnDate = new ArrayList<>();

        if (activatedOnDate != null) {
            this.activatedOnDate.add(activatedOnDate.getYear());
            this.activatedOnDate.add(activatedOnDate.get(DateTimeFieldType.monthOfYear()));
            this.activatedOnDate.add(activatedOnDate.getDayOfMonth());
        }
    }

    public ArrayList<Integer> getActivatedOnDate() {
        return this.activatedOnDate;
    }
}
