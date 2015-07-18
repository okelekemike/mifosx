/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.data.group;

import java.util.Locale;

public class Meeting {

    private final transient Integer rowIndex;

    private transient String groupId;

    private final String dateFormat;

    private final Locale locale;

    private final String description;

    private final Integer typeId;

    private String title;

    private final String startDate;

    private final String repeating;

    private final String frequency;

    private final String interval;

    public Meeting(String startDate, String repeating, String frequency, String interval, Integer rowIndex) {

        this.startDate = startDate;
        this.repeating = repeating;
        this.frequency = frequency;
        this.interval = interval;
        this.rowIndex = rowIndex;
        this.dateFormat = "dd MMMM yyyy";
        this.locale = Locale.ENGLISH;
        this.description = "";
        this.typeId = 1;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRowIndex() {
        return rowIndex;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getDescription() {
        return description;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public String getTitle() {
        return title;
    }

    public String getStartDate() {
        return startDate;
    }

    public String isRepeating() {
        return repeating;
    }

    public String getFrequency() {
        return frequency;
    }

    public String getInterval() {
        return interval;
    }

    public String getGroupId() {
        return groupId;
    }

}
