/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.services.utils;

public class StringUtils {

    public static final boolean isBlank(String input) {
        return input == null || input.trim().equals("");
    }

}
