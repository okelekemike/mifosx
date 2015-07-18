/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.data.loan;

import org.mifosplatform.portfolio.fund.data.FundData;

public class Fund {

    private final Integer id;

    private final String name;

    public Fund(Integer id, String name) {

        this.id = id;
        this.name = name;
    }

    public Fund(FundData aFundData) {

        this.id = aFundData.getId() != null ? aFundData.getId().intValue() : null;
        this.name = aFundData.getName();
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
