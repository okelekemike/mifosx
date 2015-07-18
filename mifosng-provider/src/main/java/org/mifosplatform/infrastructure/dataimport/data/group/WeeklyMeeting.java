/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.data.group;

public class WeeklyMeeting extends Meeting {

    private final String repeatsOnDay;

    public WeeklyMeeting(String startDate, String repeating, String frequency, String interval, String repeatsOnDay, Integer rowIndex) {

        super(startDate, repeating, frequency, interval, rowIndex);
        this.repeatsOnDay = repeatsOnDay;
    }

    public String getRepeatsOnDay() {
        return repeatsOnDay;
    }

}
