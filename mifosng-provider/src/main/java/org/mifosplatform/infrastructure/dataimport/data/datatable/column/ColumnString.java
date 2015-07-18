/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.data.datatable.column;

public class ColumnString extends ColumnAbstract {

    private final String length;

    public ColumnString(final String name, final String type, final Boolean mandatory, final String length) {

        super(name, type, mandatory);
        this.length = length;
    }

    public String getLenth() {
        return this.length;
    }

}
