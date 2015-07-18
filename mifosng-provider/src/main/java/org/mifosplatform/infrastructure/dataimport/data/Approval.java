/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.data;

import java.util.Locale;

public class Approval {

    private final transient Integer rowIndex;

    private final String approvedOnDate;

    private final String dateFormat;

    private final Locale locale;

    private final String note;

    public Approval(String approvedOnDate, Integer rowIndex) {
        this.approvedOnDate = approvedOnDate;
        this.rowIndex = rowIndex;
        this.dateFormat = "dd MMMM yyyy";
        this.locale = Locale.ENGLISH;
        this.note = "";
    }

    public String getApprovedOnDate() {
        return approvedOnDate;
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

}
