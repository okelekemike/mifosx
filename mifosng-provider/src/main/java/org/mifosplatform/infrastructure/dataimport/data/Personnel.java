/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.data;

import org.mifosplatform.organisation.staff.data.StaffData;

public class Personnel {

    private final Integer id;

    private final String firstname;

    private final String lastname;

    private final Integer officeId;

    private final String officeName;

    private final Boolean isLoanOfficer;

    public Personnel(Integer id, String firstname, String lastname, Integer officeId, String officeName, Boolean isLoanOfficer) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.officeId = officeId;
        this.officeName = officeName;
        this.isLoanOfficer = isLoanOfficer;
    }

    public Personnel(StaffData person) {

        this.id = person.getId() != null ? person.getId().intValue() : null;
        this.firstname = person.getFirstname();
        this.lastname = person.getLastname();

        this.officeId = person.getOfficeId() != null ? person.getOfficeId().intValue() : null;
        this.officeName = person.getOfficeName();
        this.isLoanOfficer = person.getIsLoanOfficer();
    }

    @Override
    public String toString() {
        return "PersonnelObject [id=" + id + ", firstName=" + firstname + ", lastName=" + lastname + ", officeId=" + officeId
                + ", officeName=" + officeName + ", isLoanOfficer=" + isLoanOfficer + "]";
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.firstname + " " + this.lastname;
    }

    public Integer getOfficeId() {
        return this.officeId;
    }

    public String getOfficeName() {
        return this.officeName;
    }

    public Boolean isLoanOfficer() {
        return this.isLoanOfficer;
    }
}
