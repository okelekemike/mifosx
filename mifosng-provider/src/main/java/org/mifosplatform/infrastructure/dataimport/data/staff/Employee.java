/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.data.staff;

import java.util.Locale;

public final class Employee {

    private final transient Integer rowIndex;

    private final transient String status;
    private final Long officeId;

    private final String firstname;

    private final String lastname;

    private final String isLoanOfficer;

    private final String mobileNo;

    public Employee(final Long officeId, final String firstname, final String lastname, final String isLoanOfficer, final String mobileNo,
            final String status, final Integer rowIndex) {

        this.officeId = officeId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.isLoanOfficer = isLoanOfficer;
        this.mobileNo = mobileNo;
        this.status = status;
        this.rowIndex = rowIndex;
    }

    public Integer getRowIndex() {
        return this.rowIndex;
    }

    public String getStatus() {
        return this.status;
    }

    public Long getOfficeId() {
        return this.officeId;
    }

    public String getFirstName() {
        return this.firstname;
    }

    public String getLastName() {
        return this.lastname;
    }

    public String isLoanOfficer() {
        return this.isLoanOfficer;
    }

    public String getMobileNo() {
        return this.mobileNo;
    }
}
