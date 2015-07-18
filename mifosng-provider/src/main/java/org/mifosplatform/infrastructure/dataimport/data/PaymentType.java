/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.data;

import org.mifosplatform.infrastructure.codes.data.CodeValueData;

public class PaymentType {

    private final Integer id;

    private final String name;

    public PaymentType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public PaymentType(CodeValueData aCodeValueData) {
        this.id = aCodeValueData.getId().intValue();
        this.name = aCodeValueData.getName();
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
