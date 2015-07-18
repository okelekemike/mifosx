/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.portfolio.client.api;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.mifosplatform.infrastructure.codes.data.CodeValueData;

import org.mifosplatform.portfolio.client.data.ClientData;

public class ClientApiConstants {

    public static final String CLIENT_RESOURCE_NAME = "client";
    public static final String CLIENT_CLOSURE_REASON = "ClientClosureReason";
    public static final String CLIENT_ACTION_REASON = "ClientActionReason";
    public static final String CLIENT_REJECT_REASON = "ClientRejectReason";
    public static final String CLIENT_WITHDRAW_REASON = "ClientWithdrawReason";

	
    public static final String GENDER = "Gender";
    public static final String CLIENT_TYPE = "ClientType";
    public static final String CLIENT_CLASSIFICATION = "ClientClassification";
    public static final String KIN_RELATIONSHIP = "KinRelationship";
    public static final String MARITAL = "Marital";
    // general
    public static final String localeParamName = "locale";
    public static final String dateFormatParamName = "dateFormat";

    // request parameters
    public static final String idParamName = "id";
    public static final String groupIdParamName = "groupId";
    public static final String accountNoParamName = "accountNo";
    public static final String externalIdParamName = "externalId";
    public static final String mobileNoParamName = "mobileNo";
    public static final String firstnameParamName = "firstname";
    public static final String middlenameParamName = "middlename";
    public static final String lastnameParamName = "lastname";
    public static final String fullnameParamName = "fullname";
    public static final String dependentsParamName = "dependents";
    public static final String addressLine1ParamName = "addressLine1";
    public static final String addressLine2ParamName = "addressLine2";
    public static final String townParamName = "town";
    public static final String cityParamName = "city";
    public static final String stateParamName = "state";
    public static final String zipParamName = "zip";
    public static final String countryParamName = "country";
    public static final String residenceNoParamName = "residenceNo";
    public static final String emailParamName = "email";
    public static final String stateOfOriginParamName = "stateOfOrigin";
    public static final String lgaOfOriginParamName = "lgaOfOrigin";
    public static final String longitudeParamName = "longitude";
    public static final String latitudeParamName = "latitude";
    public static final String kinFirstnameParamName = "kinfirstname";
    public static final String kinLastnameParamName = "kinlastname";
    public static final String kinAddressLine1ParamName = "kinaddressLine1";
    public static final String kinAddressLine2ParamName = "kinaddressLine2";
    public static final String kinTownParamName = "kintown";
    public static final String kinCityParamName = "kincity";
    public static final String kinStateParamName = "kinstate";
    public static final String kinZipParamName = "kinzip";
    public static final String kinCountryParamName = "kincountry";
    public static final String kinResidenceNoParamName = "kinresidenceNo";
    public static final String kinEmailParamName = "kinemail";
    public static final String kinLongitudeParamName = "kinlongitude";
    public static final String kinLatitudeParamName = "kinlatitude";
    public static final String kinRelationshipParamName = "kinRelationship";
    public static final String kinRelationshipIdParamName = "kinRelationshipId";
    public static final String officeIdParamName = "officeId";
    public static final String transferOfficeIdParamName = "transferOfficeIdParamName";
    public static final String activeParamName = "active";
    public static final String activationDateParamName = "activationDate";
    public static final String reactivationDateParamName = "reactivationDate";
    public static final String staffIdParamName = "staffId";
    public static final String closureDateParamName = "closureDate";
    public static final String closureReasonIdParamName = "closureReasonId";
    
    public static final String rejectionDateParamName = "rejectionDate";
    public static final String rejectionReasonIdParamName ="rejectionReasonId";
    public static final String withdrawalDateParamName = "withdrawalDate";
    public static final String withdrawalReasonIdParamName ="withdrawalReasonId";
        
    public static final String submittedOnDateParamName = "submittedOnDate";
    public static final String savingsProductIdParamName = "savingsProductId";
    public static final String savingsAccountIdParamName = "savingsAccountId";
    public static final String dateOfBirthParamName = "dateOfBirth";
    public static final String genderIdParamName = "genderId";
    public static final String genderParamName = "gender";
    public static final String maritalIdParamName = "maritalId";
    public static final String maritalParamName = "marital";
    public static final String clientTypeIdParamName = "clientTypeId";
    public static final String clientTypeParamName = "clientType";
    public static final String clientClassificationIdParamName = "clientClassificationId";
    public static final String clientClassificationParamName = "clientClassification";
    // response parameters
    public static final String statusParamName = "status";
    public static final String hierarchyParamName = "hierarchy";
    public static final String displayNameParamName = "displayName";
    public static final String officeNameParamName = "officeName";
    public static final String staffNameParamName = "staffName";
    public static final String trasnferOfficeNameParamName = "transferOfficeName";
    public static final String transferToOfficeNameParamName = "transferToOfficeName";
    public static final String transferToOfficeIdParamName = "transferToOfficeId";
    public static final String imageKeyParamName = "imageKey";
    public static final String imageIdParamName = "imageId";
    public static final String imagePresentParamName = "imagePresent";
    public static final String timelineParamName = "timeline";

    // associations related part of response
    public static final String groupsParamName = "groups";

    // template related part of response
    public static final String officeOptionsParamName = "officeOptions";
    public static final String staffOptionsParamName = "staffOptions";

    public static final Set<String> CLIENT_CREATE_REQUEST_DATA_PARAMETERS = new HashSet<>(Arrays.asList(localeParamName,
            dateFormatParamName, groupIdParamName, accountNoParamName, externalIdParamName, mobileNoParamName, firstnameParamName,
            middlenameParamName, lastnameParamName, fullnameParamName, dependentsParamName, addressLine1ParamName, addressLine2ParamName, townParamName,
            cityParamName, stateParamName, zipParamName, countryParamName, residenceNoParamName, emailParamName, stateOfOriginParamName, 
            lgaOfOriginParamName, longitudeParamName, latitudeParamName, kinFirstnameParamName, kinLastnameParamName, kinAddressLine1ParamName, 
            kinAddressLine2ParamName, kinTownParamName, kinCityParamName, kinStateParamName, kinZipParamName, kinCountryParamName, kinResidenceNoParamName, 
            kinEmailParamName, kinLongitudeParamName, kinLatitudeParamName, officeIdParamName, activeParamName, activationDateParamName, 
            staffIdParamName, submittedOnDateParamName, savingsProductIdParamName, dateOfBirthParamName, genderIdParamName, maritalIdParamName,
            clientTypeIdParamName, clientClassificationIdParamName, kinRelationshipIdParamName));

    public static final Set<String> CLIENT_UPDATE_REQUEST_DATA_PARAMETERS = new HashSet<>(Arrays.asList(localeParamName,
            dateFormatParamName, accountNoParamName, externalIdParamName, mobileNoParamName, firstnameParamName, middlenameParamName,
            lastnameParamName, fullnameParamName, dependentsParamName, addressLine1ParamName, addressLine2ParamName, townParamName, cityParamName, stateParamName,
            zipParamName, countryParamName, residenceNoParamName, emailParamName, stateOfOriginParamName, lgaOfOriginParamName, longitudeParamName, 
            latitudeParamName, kinFirstnameParamName, kinLastnameParamName, kinAddressLine1ParamName, kinAddressLine2ParamName, kinTownParamName, 
            kinCityParamName, kinStateParamName, kinZipParamName, kinCountryParamName, kinResidenceNoParamName, kinEmailParamName, kinLongitudeParamName, 
            kinLatitudeParamName, activeParamName, activationDateParamName, staffIdParamName, savingsProductIdParamName, dateOfBirthParamName, 
            genderIdParamName, maritalIdParamName, clientTypeIdParamName, clientClassificationIdParamName, kinRelationshipIdParamName,submittedOnDateParamName));


    /**
     * These parameters will match the class level parameters of
     * {@link ClientData}. Where possible, we try to get response parameters to
     * match those of request parameters.
     */
    public static final Set<String> CLIENT_RESPONSE_DATA_PARAMETERS = new HashSet<>(Arrays.asList(idParamName, accountNoParamName,
            externalIdParamName, statusParamName, activeParamName, activationDateParamName, firstnameParamName, middlenameParamName,
            lastnameParamName, fullnameParamName, dependentsParamName, displayNameParamName, mobileNoParamName, officeIdParamName, officeNameParamName,
            addressLine1ParamName, addressLine2ParamName, townParamName, cityParamName, stateParamName, zipParamName, countryParamName, 
            residenceNoParamName, emailParamName, stateOfOriginParamName, lgaOfOriginParamName, longitudeParamName, latitudeParamName, 
            kinFirstnameParamName, kinLastnameParamName, kinAddressLine1ParamName, kinAddressLine2ParamName, kinTownParamName, kinCityParamName, 
            kinStateParamName, kinZipParamName, kinCountryParamName, kinResidenceNoParamName, kinEmailParamName, kinLongitudeParamName, 
            kinLatitudeParamName, transferToOfficeIdParamName, transferToOfficeNameParamName, hierarchyParamName, imageIdParamName, imagePresentParamName,
            staffIdParamName, staffNameParamName, timelineParamName, groupsParamName, officeOptionsParamName, staffOptionsParamName,
            dateOfBirthParamName, genderParamName, maritalParamName, clientTypeParamName, clientClassificationParamName, kinRelationshipParamName));

    public static final Set<String> ACTIVATION_REQUEST_DATA_PARAMETERS = new HashSet<>(Arrays.asList(localeParamName,
            dateFormatParamName, activationDateParamName));
    public static final Set<String> REACTIVATION_REQUEST_DATA_PARAMETERS = new HashSet<>(Arrays.asList(localeParamName,
            dateFormatParamName, reactivationDateParamName));

    public static final Set<String> CLIENT_CLOSE_REQUEST_DATA_PARAMETERS = new HashSet<>(Arrays.asList(localeParamName,
            dateFormatParamName, closureDateParamName, closureReasonIdParamName));
	
    public static final Set<String> CLIENT_REJECT_DATA_PARAMETERS =	new HashSet<>(Arrays.asList(localeParamName,dateFormatParamName,rejectionDateParamName,rejectionReasonIdParamName));

    public static final Set<String> CLIENT_WITHDRAW_DATA_PARAMETERS =	new HashSet<>(Arrays.asList(localeParamName,dateFormatParamName,withdrawalDateParamName,withdrawalReasonIdParamName));

}