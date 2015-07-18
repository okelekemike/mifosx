/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.data.savings;

import java.util.Locale;

public class SavingsActivation {

    private final transient Integer rowIndex;

    private final String activatedOnDate;

    private final String dateFormat;

    private final Locale locale;

    public SavingsActivation(String activatedOnDate, Integer rowIndex) {

        this.activatedOnDate = activatedOnDate;
        this.rowIndex = rowIndex;
        this.dateFormat = "dd MMMM yyyy";
        this.locale = Locale.ENGLISH;
    }

    public String getActivatedOnDate() {
        return activatedOnDate;
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
}
