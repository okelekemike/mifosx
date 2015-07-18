/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.data;

import org.mifosplatform.infrastructure.core.data.EnumOptionData;

public class Type {

    private final Integer id;

    private final String code;

    private final String value;

    public Type(Integer id, String code, String value) {
        this.id = id;
        this.code = code;
        this.value = value;
    }

    public Type(final EnumOptionData type) {
        this.id = type.getId() != null ? type.getId().intValue() : null;
        this.code = type.getCode();
        this.value = type.getValue();
    }

    public Integer getId() {
        return this.id;
    }

    public String getCode() {
        return this.code;
    }

    public String getValue() {
        return this.value;
    }

}
