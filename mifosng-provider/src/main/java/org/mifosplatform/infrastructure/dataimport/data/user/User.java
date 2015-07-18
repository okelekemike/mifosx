/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.data.user;

public final class User {

    private final transient Integer rowIndex;

    private final transient String status;

    private final Long officeId;

    private final Long staffId;

    private final String[] roles;

    private final String username;

    private final String firstname;

    private final String lastname;

    private final String email;

    private final String password;

    private final String repeatPassword;

    @SuppressWarnings("unused")
    private final Boolean sendPasswordToEmail;

    public User(final Long officeId, final Long staffId, final String role, final String username, final String firstname,
            final String lastname, final String email, final String password, final String repeatPassword, final String status,
            final Integer rowIndex) {

        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.officeId = officeId;
        this.staffId = staffId;
        this.status = status;
        this.rowIndex = rowIndex;
        this.roles = role != null ? new String[] { role } : null;
        sendPasswordToEmail = false;
    }

    public Integer getRowIndex() {
        return this.rowIndex;
    }

    public String getStatus() {
        return this.status;
    }

    public String getUsername() {
        return this.username;
    }

    public String getFirstName() {
        return this.firstname;
    }

    public String getLastName() {
        return this.lastname;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPasswordRepeat() {
        return this.repeatPassword;
    }

    public Long getOfficeId() {
        return this.officeId;
    }

    public Long getStaffId() {
        return this.staffId;
    }

    public String[] getRole() {
        return this.roles;
    }
}
