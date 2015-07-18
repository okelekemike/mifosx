/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.data;

public class Status {

    private Boolean active;

    public Status(Boolean active) {
        this.active = active;
    }

    public Boolean isActive() {
        return this.active;
    }
}
