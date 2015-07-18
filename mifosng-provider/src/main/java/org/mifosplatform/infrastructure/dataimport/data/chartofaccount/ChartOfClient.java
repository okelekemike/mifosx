/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.data.chartofaccount;

public final class ChartOfClient {

    private final transient Integer rowIndex;

    private final transient String status;

    private final String accountName;

    private final String glCode;

    private final Long usage;

    private final Boolean manualEntriesAllowed;

    private final Long type;

    private final Long tagId;

    private final Long parentId;

    private final String description;

    public ChartOfClient(final String accountName, final String glCode, final Long usage, final Boolean manualEntriesAllowed,
            final Long type, final Long tagId, final Long parentId, final String description, final Integer rowIndex,
            final String status) {

        this.accountName = accountName;
        this.glCode = glCode;
        this.usage = usage;
        this.manualEntriesAllowed = manualEntriesAllowed;
        this.type = type;
        this.tagId = tagId;
        this.parentId = parentId;
        this.description = description;
        this.rowIndex = rowIndex;
        this.status = status;
    }

    public Integer getRowIndex() {
        return rowIndex;
    }

    public String getStatus() {
        return this.status;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public String getGlCode() {
        return this.glCode;
    }

    public Long getAccountUsage() {
        return this.usage;
    }

    public Boolean isManualEntriesAllowed() {
        return this.manualEntriesAllowed;
    }

    public Long getAccountTypeId() {
        return this.type;
    }

    public Long getTagId() {
        return this.tagId;
    }
    
    public Long getParentId() {
        return this.parentId;
    }

    public String getDescription() {
        return this.description;
    }
}
