/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.data.datatable;

import java.util.ArrayList;

import org.mifosplatform.infrastructure.dataimport.data.datatable.column.ColumnAbstract;

public final class DataTable {

    private final transient Integer rowIndex;

    private final transient String status;

    private final String datatableName;

    private final String apptableName;

    private final Boolean multiRow;

    private final ColumnAbstract[] columns;

    public DataTable(final String datatableName, final String apptableName, final Boolean multiRow,
            final ArrayList<ColumnAbstract> columns, final Integer rowIndex, final String status) {

        this.datatableName = datatableName;
        this.apptableName = apptableName;
        this.multiRow = multiRow;
        if (columns != null)
            this.columns = columns.toArray(new ColumnAbstract[columns.size()]);
        else
            this.columns = new ColumnAbstract[0];
        this.rowIndex = rowIndex;
        this.status = status;
    }

    public Integer getRowIndex() {
        return rowIndex;
    }

    public String getStatus() {
        return this.status;
    }

    public String getDataTableName() {
        return this.datatableName;
    }

    public String getApplicationTableName() {
        return this.apptableName;
    }

    public Boolean isMultiRow() {
        return this.multiRow;
    }

    public ColumnAbstract[] getColumns() {
        return this.columns;
    }
}
