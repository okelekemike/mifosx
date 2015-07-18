/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.data.datatable.column;

public class ColumnDropDown extends ColumnAbstract {

    private final String code;

    public ColumnDropDown(final String name, final String type, final Boolean mandatory, final String code) {

        super(name, type, mandatory);
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

}
