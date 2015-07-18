/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.data.office;

import java.util.Locale;

public final class Office {

    private final transient Integer rowIndex;

    private final transient String status;

    private final String dateFormat;

    private final Locale locale;

    private final String name;

    private final Long parentId;

    private final String openingDate;

    private final String externalId;

    public Office(final String name, final Long parentId, final String openingDate, final String externalId, final Integer rowIndex,
            final String status) {

        this.name = name;
        this.parentId = parentId;
        this.openingDate = openingDate;
        this.externalId = externalId;
        this.rowIndex = rowIndex;
        this.status = status;
        this.dateFormat = "dd MMMM yyyy";
        this.locale = Locale.ENGLISH;
    }

    public Integer getRowIndex() {
        return this.rowIndex;
    }

    public String getDateFormat() {
        return this.dateFormat;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public String getName() {
        return this.name;
    }

    public Long getParentOfficeId() {
        return this.parentId;
    }

    public String getOpeningDate() {
        return this.openingDate;
    }

    public String getExternalId() {
        return this.externalId;
    }

    public String getStatus() {
        return this.status;
    }
}
