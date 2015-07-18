/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.data.center;

import java.util.Locale;

public final class Center {

    private final String name;

    private final Long officeId;

    private final Long staffId;

    private final String active;

    private final String activationDate;

    private final String externalId;

    private final String dateFormat;

    private final Locale locale;

    private final transient Integer rowIndex;

    private final transient String status;

    private transient Boolean isValid;

    private transient String validationStatus;

    public Center(final String name, final Long officeId, final Long staffId, final String active, final String activationDate,
            final String externalId, final Integer rowIndex, final String status) {

        this.name = name;
        this.officeId = officeId;
        this.staffId = staffId;
        this.active = active;
        this.activationDate = activationDate;
        this.externalId = externalId;
        this.rowIndex = rowIndex;
        this.status = status;
        this.dateFormat = "dd MMMM yyyy";
        this.locale = Locale.ENGLISH;
    }

    public String getName() {
        return this.name;
    }

    public Long getOfficeId() {
        return this.officeId;
    }

    public Long getStaffId() {
        return this.staffId;
    }

    public String isActive() {
        return this.active;
    }

    public String getActivationDate() {
        return this.activationDate;
    }

    public String getExternalId() {
        return this.externalId;
    }

    public String getDateFormat() {
        return this.dateFormat;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public Integer getRowIndex() {
        return this.rowIndex;
    }

    public String getStatus() {
        return this.status;
    }

    public String getValidateStatus() {
        return this.validationStatus;
    }

    public Boolean isValidated() {

        if (this.officeId.equals("") || this.officeId == null) {
            this.isValid = false;
            this.validationStatus = "Office name must be selected";
        } else if (this.staffId.equals("") || this.staffId == null) {
            this.isValid = false;
            this.validationStatus = "Staff name must be selected";
        } else if (this.active.equals("") || this.active == null) {
            this.isValid = false;
            this.validationStatus = "Activation must be selected";
        } else if (this.active.equalsIgnoreCase("true") && (this.activationDate.equals("") || this.activationDate == null)) {
            this.isValid = false;
            this.validationStatus = "Activation date must be provided";
        } else {
            this.isValid = true;
            this.validationStatus = "Required fields are provided";
        }

        return this.isValid;
    }
}
