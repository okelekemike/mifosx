/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.data.client;

import java.util.Locale;

public final class Client {

    private final transient Integer rowIndex;

    private final String dateFormat;

    private final Locale locale;

    private final Long officeId;

    private final Long staffId;

    private final String firstname;

    private final String middlename;

    private final String lastname;

    private final String mobileNo;

    private final String genderId;

    private final String dateOfBirth;

    private final String clientTypeId;

    private final String clientClassificationId;

    private final String externalId;

    private final String active;

    private final String activationDate;

    private transient Integer groupId;

    private transient Boolean isValid;

    private transient String validationStatus;

    public Client(final String firstname, final String lastname, final String middlename, final String mobileNo, final String genderId,
            final String dateOfBirth, final String clientTypeId, final String clientClassificationId, final String activationDate,
            final String active, final String externalId, final Long officeId, final Long staffId, Integer rowIndex) {

        this.firstname = firstname;
        this.lastname = lastname;
        this.middlename = middlename;
        this.mobileNo = mobileNo;
        this.genderId = genderId;
        this.dateOfBirth = dateOfBirth;
        this.clientTypeId = clientTypeId;
        this.clientClassificationId = clientClassificationId;
        this.activationDate = activationDate;
        this.active = active;
        this.externalId = externalId;
        this.officeId = officeId;
        this.staffId = staffId;
        this.rowIndex = rowIndex;
        this.dateFormat = "dd MMMM yyyy";
        this.locale = Locale.ENGLISH;
    }

    public String getFirstName() {
        return this.firstname;
    }

    public String getLastName() {
        return this.lastname;
    }

    public String getMiddleName() {
        return this.middlename;
    }

    public String getMobileNo() {
        return this.mobileNo;
    }

    public String getGender() {
        return this.genderId;
    }

    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    public String getClientType() {
        return this.clientTypeId;
    }

    public String getClientClassification() {
        return this.clientClassificationId;
    }

    public String getActivationDate() {
        return this.activationDate;
    }

    public String isActive() {
        return this.active;
    }

    public String getExternalId() {
        return this.externalId;
    }

    public Long getOfficeId() {
        return this.officeId;
    }

    public Long getStaffId() {
        return this.staffId;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public String getDateFormat() {
        return this.dateFormat;
    }

    public Integer getRowIndex() {
        return this.rowIndex;
    }

    public Integer getGroupId() {
        return this.groupId;
    }

    public void setGruopID(Integer groupId) {
        this.groupId = groupId;
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
        } else if (this.firstname.equals("") || this.firstname == null) {
            this.isValid = false;
            this.validationStatus = "First name must be selected";
        } else if (this.lastname.equals("") || this.lastname == null) {
            this.isValid = false;
            this.validationStatus = "Last name must be selected";
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
