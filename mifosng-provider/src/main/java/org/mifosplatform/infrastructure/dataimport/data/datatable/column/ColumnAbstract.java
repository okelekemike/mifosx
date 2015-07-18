/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.data.datatable.column;

public abstract class ColumnAbstract {

    final String name;
    final String type;
    final Boolean mandatory;

    protected ColumnAbstract(final String name, final String type, final Boolean mandatory) {
        this.name = name;
        this.type = type;
        this.mandatory = mandatory;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public Boolean isMandatory() {
        return this.mandatory;
    }
}
