/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.codes.data;

import java.io.Serializable;

/**
 * Immutable data object representing a code.
 */
public class CodeData implements Serializable {

    private final Long id;
    @SuppressWarnings("unused")
    private final String name;
    @SuppressWarnings("unused")
    private final boolean systemDefined;

    public static CodeData instance(final Long id, final String name, final boolean systemDefined) {
        return new CodeData(id, name, systemDefined);
    }

    private CodeData(final Long id, final String name, final boolean systemDefined) {
        this.id = id;
        this.name = name;
        this.systemDefined = systemDefined;
    }

    public Long getCodeId() {
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public Boolean isSystemDefined(){
        return this.systemDefined;
    }
}