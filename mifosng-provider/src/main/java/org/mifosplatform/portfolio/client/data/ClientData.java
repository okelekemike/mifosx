/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.portfolio.client.data;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.joda.time.LocalDate;
import org.mifosplatform.infrastructure.codes.data.CodeValueData;
import org.mifosplatform.infrastructure.core.data.EnumOptionData;
import org.mifosplatform.organisation.office.data.OfficeData;
import org.mifosplatform.organisation.staff.data.StaffData;
import org.mifosplatform.portfolio.group.data.GroupGeneralData;
import org.mifosplatform.portfolio.savings.data.SavingsAccountData;
import org.mifosplatform.portfolio.savings.data.SavingsProductData;

/**
 * Immutable data object representing client data.
 */
final public class ClientData implements Comparable<ClientData> {

    private final Long id;
    private final String accountNo;
    private final String externalId;

    private final EnumOptionData status;
    private final CodeValueData subStatus;

    @SuppressWarnings("unused")
    private final Boolean active;
    private final LocalDate activationDate;

    private final String firstname;
    private final String middlename;
    private final String lastname;
    private final String fullname;
    private final Long dependents;
    private final String displayName;
    private final String mobileNo;
    private final String addressLine1;
    private final String addressLine2;
    private final String town;
    private final String city;
    private final String state;
    private final String zip;
    private final String country;
    private final String residenceNo;
    private final String email;
    private final String stateOfOrigin;
    private final String lgaOfOrigin;
    private final String latitude;
    private final String longitude;
    private final String kinfirstname;
    private final String kinlastname;
    private final String kinaddressLine1;
    private final String kinaddressLine2;
    private final String kintown;
    private final String kincity;
    private final String kinstate;
    private final String kinzip;
    private final String kincountry;
    private final String kinresidenceNo;
    private final String kinemail;
    private final String kinlatitude;
    private final String kinlongitude;
    private final LocalDate dateOfBirth;
    private final CodeValueData gender;
    private final CodeValueData marital;
    private final CodeValueData clientType;
    private final CodeValueData clientClassification;
    private final CodeValueData kinRelationship;

    private final Long officeId;
    private final String officeName;
    private final Long transferToOfficeId;
    private final String transferToOfficeName;

    private final Long imageId;
    private final Boolean imagePresent;
    private final Long staffId;
    private final String staffName;
    private final ClientTimelineData timeline;

    private final Long savingsProductId;
    private final String savingsProductName;

    private final Long savingsAccountId;

    // associations
    private final Collection<GroupGeneralData> groups;

    // template
    private final Collection<OfficeData> officeOptions;
    private final Collection<StaffData> staffOptions;
    private final Collection<CodeValueData> narrations;
    private final Collection<SavingsProductData> savingProductOptions;
    private final Collection<SavingsAccountData> savingAccountOptions;
    private final Collection<CodeValueData> genderOptions;
    private final Collection<CodeValueData> maritalOptions;
    private final Collection<CodeValueData> clientTypeOptions;
    private final Collection<CodeValueData> clientClassificationOptions;
    private final Collection<CodeValueData> kinRelationshipOptions;

    public static ClientData template(final Long officeId, final LocalDate joinedDate, final Collection<OfficeData> officeOptions,
            final Collection<StaffData> staffOptions, final Collection<CodeValueData> narrations,
            final Collection<CodeValueData> genderOptions, final Collection<CodeValueData> maritalOptions, 
            final Collection<SavingsProductData> savingProductOptions, final Collection<CodeValueData> clientTypeOptions, 
            final Collection<CodeValueData> clientClassificationOptions, final Collection<CodeValueData> kinRelationshipOptions) {
        final String accountNo = null;
        final EnumOptionData status = null;
        final CodeValueData subStatus = null;
        final String officeName = null;
        final Long transferToOfficeId = null;
        final String transferToOfficeName = null;
        final Long id = null;
        final String firstname = null;
        final String middlename = null;
        final String lastname = null;
        final String fullname = null;
        final Long dependents = null;
        final String displayName = null;
        final String externalId = null;
        final String mobileNo = null;
        final String addressLine1 = null;
        final String addressLine2 = null;
        final String town = null;
        final String city = null;
        final String state = null;
        final String zip = null;
        final String country = null;
        final String residenceNo = null;
        final String email = null;
        final String stateOfOrigin = null;
        final String lgaOfOrigin = null;
        final String latitude = null;
        final String longitude = null;
        final String kinfirstname = null;
        final String kinlastname = null;
        final String kinaddressLine1 = null;
        final String kinaddressLine2 = null;
        final String kintown = null;
        final String kincity = null;
        final String kinstate = null;
        final String kinzip = null;
        final String kincountry = null;
        final String kinresidenceNo = null;
        final String kinemail = null;
        final String kinlatitude = null;
        final String kinlongitude = null;
        final LocalDate dateOfBirth = null;
        final CodeValueData gender = null;
        final CodeValueData marital = null;
        final Long imageId = null;
        final Long staffId = null;
        final String staffName = null;
        final Collection<GroupGeneralData> groups = null;
        final ClientTimelineData timeline = null;
        final Long savingsProductId = null;
        final String savingsProductName = null;
        final Long savingsAccountId = null;
        final Collection<SavingsAccountData> savingAccountOptions = null;
        final CodeValueData clientType = null;
        final CodeValueData clientClassification = null;
        final CodeValueData kinRelationship = null;
        return new ClientData(accountNo, status, subStatus, officeId, officeName, transferToOfficeId, transferToOfficeName, id, firstname,
                middlename, lastname, fullname, dependents, displayName, externalId, mobileNo, addressLine1, addressLine2, town, city, state, zip, country,
                residenceNo, email, stateOfOrigin, lgaOfOrigin, latitude, longitude, kinfirstname, kinlastname, kinaddressLine1, kinaddressLine2, 
                kintown, kincity, kinstate, kinzip, kincountry, kinresidenceNo, kinemail, kinlatitude, kinlongitude, dateOfBirth, gender, marital,
                joinedDate, imageId, staffId, staffName, officeOptions, groups, staffOptions, narrations, genderOptions, maritalOptions, timeline,
                savingProductOptions, savingsProductId, savingsProductName, savingsAccountId, savingAccountOptions, 
                clientType, clientClassification, clientTypeOptions, clientClassificationOptions, kinRelationship, kinRelationshipOptions);

    }

    public static ClientData templateOnTop(final ClientData clientData, final ClientData templateData) {

        return new ClientData(clientData.accountNo, clientData.status, clientData.subStatus, clientData.officeId, clientData.officeName,
                clientData.transferToOfficeId, clientData.transferToOfficeName, clientData.id, clientData.firstname, clientData.middlename,
                clientData.lastname, clientData.fullname, clientData.dependents, clientData.displayName, clientData.externalId, clientData.mobileNo,
                clientData.addressLine1, clientData.addressLine2, clientData.town, clientData.city, clientData.state, clientData.zip,
                clientData.country, clientData.residenceNo, clientData.email, clientData.stateOfOrigin, clientData.lgaOfOrigin, clientData.latitude, 
                clientData.longitude, clientData.kinfirstname, clientData.kinlastname, clientData.kinaddressLine1, clientData.kinaddressLine2, 
                clientData.kintown, clientData.kincity, clientData.kinstate, clientData.kinzip, clientData.kincountry, clientData.kinresidenceNo, 
                clientData.kinemail, clientData.kinlatitude, clientData.kinlongitude, clientData.dateOfBirth, 
                clientData.gender, clientData.marital, clientData.activationDate, clientData.imageId, clientData.staffId, clientData.staffName,
                templateData.officeOptions, clientData.groups, templateData.staffOptions, templateData.narrations,
                templateData.genderOptions, templateData.maritalOptions, clientData.timeline, templateData.savingProductOptions, clientData.savingsProductId,
                clientData.savingsProductName, clientData.savingsAccountId, clientData.savingAccountOptions, clientData.clientType,
                clientData.clientClassification, templateData.clientTypeOptions, templateData.clientClassificationOptions, 
                clientData.kinRelationship, templateData.kinRelationshipOptions);

    }

    public static ClientData templateWithSavingAccountOptions(final ClientData clientData,
            final Collection<SavingsAccountData> savingAccountOptions) {

        return new ClientData(clientData.accountNo, clientData.status, clientData.subStatus, clientData.officeId, clientData.officeName,
                clientData.transferToOfficeId, clientData.transferToOfficeName, clientData.id, clientData.firstname, clientData.middlename,
                clientData.lastname, clientData.fullname, clientData.dependents, clientData.displayName, clientData.externalId, clientData.mobileNo,
                clientData.addressLine1, clientData.addressLine2, clientData.town, clientData.city, clientData.state, clientData.zip,
                clientData.country, clientData.residenceNo, clientData.email, clientData.stateOfOrigin, clientData.lgaOfOrigin, 
                clientData.latitude, clientData.longitude, clientData.kinfirstname, clientData.kinlastname, clientData.kinaddressLine1, 
                clientData.kinaddressLine2, clientData.kintown, clientData.kincity, clientData.kinstate, clientData.kinzip, 
                clientData.kincountry, clientData.kinresidenceNo, clientData.kinemail, clientData.kinlatitude, clientData.kinlongitude, 
                clientData.dateOfBirth, clientData.gender, clientData.marital, clientData.activationDate, clientData.imageId, clientData.staffId, clientData.staffName,
                clientData.officeOptions, clientData.groups, clientData.staffOptions, clientData.narrations,
                clientData.genderOptions, clientData.maritalOptions, clientData.timeline, clientData.savingProductOptions, clientData.savingsProductId,
                clientData.savingsProductName, clientData.savingsAccountId, savingAccountOptions, clientData.clientType,
                clientData.clientClassification, clientData.clientTypeOptions, clientData.clientClassificationOptions, 
                clientData.kinRelationship, clientData.kinRelationshipOptions);

    }

    public static ClientData setParentGroups(final ClientData clientData, final Collection<GroupGeneralData> parentGroups) {
        return new ClientData(clientData.accountNo, clientData.status, clientData.subStatus, clientData.officeId, clientData.officeName,
                clientData.transferToOfficeId, clientData.transferToOfficeName, clientData.id, clientData.firstname, clientData.middlename,
                clientData.lastname, clientData.fullname, clientData.dependents, clientData.displayName, clientData.externalId, clientData.mobileNo,
                clientData.addressLine1, clientData.addressLine2, clientData.town, clientData.city, clientData.state, clientData.zip,
                clientData.country, clientData.residenceNo, clientData.email, clientData.stateOfOrigin, clientData.lgaOfOrigin, 
                clientData.latitude, clientData.longitude, clientData.kinfirstname, clientData.kinlastname, clientData.kinaddressLine1, 
                clientData.kinaddressLine2, clientData.kintown, clientData.kincity, clientData.kinstate, clientData.kinzip, 
                clientData.kincountry, clientData.kinresidenceNo, clientData.kinemail, clientData.kinlatitude, clientData.kinlongitude, 
                clientData.dateOfBirth, clientData.gender, clientData.marital, clientData.activationDate, clientData.imageId, clientData.staffId,
                clientData.staffName, clientData.officeOptions, parentGroups, clientData.staffOptions, null, clientData.genderOptions, 
                clientData.maritalOptions, clientData.timeline, clientData.savingProductOptions, clientData.savingsProductId, 
                clientData.savingsProductName, clientData.savingsAccountId, clientData.savingAccountOptions, clientData.clientType, 
                clientData.clientClassification, clientData.clientTypeOptions, clientData.clientClassificationOptions, clientData.kinRelationship, 
                clientData.kinRelationshipOptions);

    }

    public static ClientData clientIdentifier(final Long id, final String accountNo, final String firstname, final String middlename,
            final String lastname, final String fullname, final Long dependents, final String displayName, final Long officeId, final String officeName) {

        final Long transferToOfficeId = null;
        final String transferToOfficeName = null;
        final String externalId = null;
        final String mobileNo = null;
        final String addressLine1 = null;
        final String addressLine2 = null;
        final String town = null;
        final String city = null;
        final String state = null;
        final String zip = null;
        final String country = null;
        final String residenceNo = null;
        final String email = null;
        final String stateOfOrigin = null;
        final String lgaOfOrigin = null;
        final String latitude = null;
        final String longitude = null;
        final String kinfirstname = null;
        final String kinlastname = null;
        final String kinaddressLine1 = null;
        final String kinaddressLine2 = null;
        final String kintown = null;
        final String kincity = null;
        final String kinstate = null;
        final String kinzip = null;
        final String kincountry = null;
        final String kinresidenceNo = null;
        final String kinemail = null;
        final String kinlatitude = null;
        final String kinlongitude = null;
        final LocalDate dateOfBirth = null;
        final CodeValueData gender = null;
        final CodeValueData marital = null;
        final LocalDate activationDate = null;
        final Long imageId = null;
        final Long staffId = null;
        final String staffName = null;
        final Collection<OfficeData> allowedOffices = null;
        final Collection<GroupGeneralData> groups = null;
        final Collection<StaffData> staffOptions = null;
        final Collection<CodeValueData> closureReasons = null;
        final Collection<CodeValueData> genderOptions = null;
        final Collection<CodeValueData> maritalOptions = null;
        final ClientTimelineData timeline = null;
        final Collection<SavingsProductData> savingProductOptions = null;
        final Long savingsProductId = null;
        final String savingsProductName = null;
        final Long savingsAccountId = null;
        final Collection<SavingsAccountData> savingAccountOptions = null;
        final CodeValueData clientType = null;
        final CodeValueData clientClassification = null;
         final CodeValueData kinRelationship = null;
        final Collection<CodeValueData> clientTypeOptions = null;
        final Collection<CodeValueData> clientClassificationOptions = null;
        final Collection<CodeValueData> kinRelationshipOptions = null;
        final EnumOptionData status = null;
        final CodeValueData subStatus = null;
        return new ClientData(accountNo, status, subStatus, officeId, officeName, transferToOfficeId, transferToOfficeName, id, firstname,
                middlename, lastname, fullname, dependents, displayName, externalId, mobileNo, addressLine1, addressLine2, town, city, state, zip, country,
                residenceNo, email, stateOfOrigin, lgaOfOrigin, latitude, longitude, kinfirstname, kinlastname, kinaddressLine1, kinaddressLine2, 
                kintown, kincity, kinstate, kinzip, kincountry, kinresidenceNo, kinemail, kinlatitude, kinlongitude, dateOfBirth, gender, marital,
                activationDate, imageId, staffId, staffName, allowedOffices, groups, staffOptions, closureReasons, genderOptions, maritalOptions, timeline,
                savingProductOptions, savingsProductId, savingsProductName, savingsAccountId, savingAccountOptions, clientType, clientClassification,
                clientTypeOptions, clientClassificationOptions, kinRelationship, kinRelationshipOptions);
    }

    public static ClientData lookup(final Long id, final String displayName, final Long officeId, final String officeName) {
        final String accountNo = null;
        final EnumOptionData status = null;
        final CodeValueData subStatus = null;
        final Long transferToOfficeId = null;
        final String transferToOfficeName = null;
        final String firstname = null;
        final String middlename = null;
        final String lastname = null;
        final String fullname = null;
        final Long dependents = null;
        final String externalId = null;
        final String mobileNo = null;
        final String addressLine1 = null;
        final String addressLine2 = null;
        final String town = null;
        final String city = null;
        final String state = null;
        final String zip = null;
        final String country = null;
        final String residenceNo = null;
        final String email = null;
        final String stateOfOrigin = null;
        final String lgaOfOrigin = null;
        final String latitude = null;
        final String longitude = null;
        final String kinfirstname = null;
        final String kinlastname = null;
        final String kinaddressLine1 = null;
        final String kinaddressLine2 = null;
        final String kintown = null;
        final String kincity = null;
        final String kinstate = null;
        final String kinzip = null;
        final String kincountry = null;
        final String kinresidenceNo = null;
        final String kinemail = null;
        final String kinlatitude = null;
        final String kinlongitude = null;
        final LocalDate dateOfBirth = null;
        final CodeValueData gender = null;
        final CodeValueData marital = null;
        final LocalDate activationDate = null;
        final Long imageId = null;
        final Long staffId = null;
        final String staffName = null;
        final Collection<OfficeData> allowedOffices = null;
        final Collection<GroupGeneralData> groups = null;
        final Collection<StaffData> staffOptions = null;
        final Collection<CodeValueData> closureReasons = null;
        final Collection<CodeValueData> genderOptions = null;
        final Collection<CodeValueData> maritalOptions = null;
        final ClientTimelineData timeline = null;
        final Collection<SavingsProductData> savingProductOptions = null;
        final Long savingsProductId = null;
        final String savingsProductName = null;
        final Long savingsAccountId = null;
        final Collection<SavingsAccountData> savingAccountOptions = null;
        final CodeValueData clientType = null;
        final CodeValueData clientClassification = null;
        final CodeValueData kinRelationship = null;
        final Collection<CodeValueData> clientTypeOptions = null;
        final Collection<CodeValueData> clientClassificationOptions = null;
        final Collection<CodeValueData> kinRelationshipOptions = null;
        return new ClientData(accountNo, status, subStatus, officeId, officeName, transferToOfficeId, transferToOfficeName, id, firstname,
                middlename, lastname, fullname, dependents, displayName, externalId, mobileNo, addressLine1, addressLine2, town, city, state, zip, country,
                residenceNo, email, stateOfOrigin, lgaOfOrigin, latitude, longitude, kinfirstname, kinlastname, kinaddressLine1, kinaddressLine2, 
                kintown, kincity, kinstate, kinzip, kincountry, kinresidenceNo, kinemail, kinlatitude, kinlongitude, dateOfBirth, gender, marital,
                activationDate, imageId, staffId, staffName, allowedOffices, groups, staffOptions, closureReasons, genderOptions, maritalOptions, timeline,
                savingProductOptions, savingsProductId, savingsProductName, savingsAccountId, savingAccountOptions, clientType, clientClassification,
                clientTypeOptions, clientClassificationOptions, kinRelationship, kinRelationshipOptions);

    }

    public static ClientData instance(final String accountNo, final EnumOptionData status, final CodeValueData subStatus,
            final Long officeId, final String officeName, final Long transferToOfficeId, final String transferToOfficeName, final Long id,
            final String firstname, final String middlename, final String lastname, final String fullname, final Long dependents, final String displayName,
            final String externalId, final String mobileNo, final String addressLine1, final String addressLine2, final String town, 
            final String city, final String state, final String zip, final String country, final String residenceNo, final String email, 
            final String stateOfOrigin, final String lgaOfOrigin, final String latitude, final String longitude, final String kinfirstname, 
            final String kinlastname, final String kinaddressLine1, final String kinaddressLine2, final String kintown, final String kincity, 
            final String kinstate, final String kinzip, final String kincountry, final String kinresidenceNo, final String kinemail, 
            final String kinlatitude, final String kinlongitude, final LocalDate dateOfBirth, final CodeValueData gender, final CodeValueData marital,
            final LocalDate activationDate, final Long imageId, final Long staffId, final String staffName,
            final ClientTimelineData timeline, final Long savingsProductId, final String savingsProductName, final Long savingsAccountId,
            final CodeValueData clientType, final CodeValueData clientClassification, final CodeValueData kinRelationship) {

        final Collection<OfficeData> allowedOffices = null;
        final Collection<GroupGeneralData> groups = null;
        final Collection<StaffData> staffOptions = null;
        final Collection<CodeValueData> closureReasons = null;
        final Collection<CodeValueData> genderOptions = null;
        final Collection<CodeValueData> maritalOptions = null;
        final Collection<SavingsProductData> savingProductOptions = null;
        final Collection<CodeValueData> clientTypeOptions = null;
        final Collection<CodeValueData> clientClassificationOptions = null;
        final Collection<CodeValueData> kinRelationshipOptions = null;
        return new ClientData(accountNo, status, subStatus, officeId, officeName, transferToOfficeId, transferToOfficeName, id, firstname,
                middlename, lastname, fullname, dependents, displayName, externalId, mobileNo, addressLine1, addressLine2, town, city, state, zip, country,
                residenceNo, email, stateOfOrigin, lgaOfOrigin, latitude, longitude, kinfirstname, kinlastname, kinaddressLine1, kinaddressLine2, 
                kintown, kincity, kinstate, kinzip, kincountry, kinresidenceNo, kinemail, kinlatitude, kinlongitude,
                dateOfBirth, gender, marital, activationDate, imageId, staffId, staffName, allowedOffices, groups, staffOptions,
                closureReasons, genderOptions, maritalOptions, timeline, savingProductOptions, savingsProductId, savingsProductName, savingsAccountId, null,
                clientType, clientClassification, clientTypeOptions, clientClassificationOptions, kinRelationship, kinRelationshipOptions);

    }

    private ClientData(final String accountNo, final EnumOptionData status, final CodeValueData subStatus, final Long officeId,
            final String officeName, final Long transferToOfficeId, final String transferToOfficeName, final Long id,
            final String firstname, final String middlename, final String lastname, final String fullname, final Long dependents, final String displayName,
            final String externalId, final String mobileNo, final String addressLine1, final String addressLine2, final String town, 
            final String city, final String state, final String zip, final String country, final String residenceNo, final String email, 
            final String stateOfOrigin, final String lgaOfOrigin, final String latitude, final String longitude, final String kinfirstname, 
            final String kinlastname, final String kinaddressLine1, final String kinaddressLine2, final String kintown, final String kincity, 
            final String kinstate, final String kinzip, final String kincountry, final String kinresidenceNo, final String kinemail, 
            final String kinlatitude, final String kinlongitude, final LocalDate dateOfBirth, final CodeValueData gender,
            final CodeValueData marital, final LocalDate activationDate, final Long imageId, final Long staffId, final String staffName,
            final Collection<OfficeData> allowedOffices, final Collection<GroupGeneralData> groups,
            final Collection<StaffData> staffOptions, final Collection<CodeValueData> narrations,
            final Collection<CodeValueData> genderOptions, final Collection<CodeValueData> maritalOptions, final ClientTimelineData timeline,
            final Collection<SavingsProductData> savingProductOptions, final Long savingsProductId, final String savingsProductName,
            final Long savingsAccountId, final Collection<SavingsAccountData> savingAccountOptions, final CodeValueData clientType,
            final CodeValueData clientClassification, final Collection<CodeValueData> clientTypeOptions,
            final Collection<CodeValueData> clientClassificationOptions, 
            final CodeValueData kinRelationship, final Collection<CodeValueData> kinRelationshipOptions) {
        this.accountNo = accountNo;
        this.status = status;
        if (status != null) {
            this.active = status.getId().equals(300L);
        } else {
            this.active = null;
        }
        this.subStatus = subStatus;
        this.officeId = officeId;
        this.officeName = officeName;
        this.transferToOfficeId = transferToOfficeId;
        this.transferToOfficeName = transferToOfficeName;
        this.id = id;
        this.firstname = StringUtils.defaultIfEmpty(firstname, null);
        this.middlename = StringUtils.defaultIfEmpty(middlename, null);
        this.lastname = StringUtils.defaultIfEmpty(lastname, null);
        this.fullname = StringUtils.defaultIfEmpty(fullname, null);
        this.dependents = dependents;
        this.displayName = StringUtils.defaultIfEmpty(displayName, null);
        this.externalId = StringUtils.defaultIfEmpty(externalId, null);
        this.mobileNo = StringUtils.defaultIfEmpty(mobileNo, null);
        this.addressLine1 = StringUtils.defaultIfEmpty(addressLine1, null);
        this.addressLine2 = StringUtils.defaultIfEmpty(addressLine2, null);
        this.town = StringUtils.defaultIfEmpty(town, null);
        this.city = StringUtils.defaultIfEmpty(city, null);
        this.state = StringUtils.defaultIfEmpty(state, null);
        this.zip = StringUtils.defaultIfEmpty(zip, null);
        this.country = StringUtils.defaultIfEmpty(country, null);
        this.residenceNo = StringUtils.defaultIfEmpty(residenceNo, null);
        this.email = StringUtils.defaultIfEmpty(email, null);
        this.stateOfOrigin = StringUtils.defaultIfEmpty(stateOfOrigin, null);
        this.lgaOfOrigin = StringUtils.defaultIfEmpty(lgaOfOrigin, null);
        this.latitude = StringUtils.defaultIfEmpty(latitude, null);
        this.longitude = StringUtils.defaultIfEmpty(longitude, null);
        this.kinfirstname = StringUtils.defaultIfEmpty(kinfirstname, null);
        this.kinlastname = StringUtils.defaultIfEmpty(kinlastname, null);
        this.kinaddressLine1 = StringUtils.defaultIfEmpty(kinaddressLine1, null);
        this.kinaddressLine2 = StringUtils.defaultIfEmpty(kinaddressLine2, null);
        this.kintown = StringUtils.defaultIfEmpty(kintown, null);
        this.kincity = StringUtils.defaultIfEmpty(kincity, null);
        this.kinstate = StringUtils.defaultIfEmpty(kinstate, null);
        this.kinzip = StringUtils.defaultIfEmpty(kinzip, null);
        this.kincountry = StringUtils.defaultIfEmpty(kincountry, null);
        this.kinresidenceNo = StringUtils.defaultIfEmpty(kinresidenceNo, null);
        this.kinemail = StringUtils.defaultIfEmpty(kinemail, null);
        this.kinlatitude = StringUtils.defaultIfEmpty(kinlatitude, null);
        this.kinlongitude = StringUtils.defaultIfEmpty(kinlongitude, null);
        this.kinRelationship = kinRelationship;
        this.activationDate = activationDate;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.marital = marital;
        this.clientClassification = clientClassification;
        this.clientType = clientType;
        this.imageId = imageId;
        if (imageId != null) {
            this.imagePresent = Boolean.TRUE;
        } else {
            this.imagePresent = null;
        }
        this.staffId = staffId;
        this.staffName = staffName;

        // associations
        this.groups = groups;

        // template
        this.officeOptions = allowedOffices;
        this.staffOptions = staffOptions;
        this.narrations = narrations;

        this.genderOptions = genderOptions;
        this.maritalOptions = maritalOptions;
        this.clientClassificationOptions = clientClassificationOptions;
        this.clientTypeOptions = clientTypeOptions;
        this.kinRelationshipOptions = kinRelationshipOptions;

        this.timeline = timeline;
        this.savingProductOptions = savingProductOptions;
        this.savingsProductId = savingsProductId;
        this.savingsProductName = savingsProductName;
        this.savingsAccountId = savingsAccountId;
        this.savingAccountOptions = savingAccountOptions;

    }

    public Long id() {
        return this.id;
    }

    public String displayName() {
        return this.displayName;
    }

    public Long officeId() {
        return this.officeId;
    }

    public String officeName() {
        return this.officeName;
    }

    public Long getImageId() {
        return this.imageId;
    }

    public Boolean getImagePresent() {
        return this.imagePresent;
    }

    public ClientTimelineData getTimeline() {
        return this.timeline;
    }

    @Override
    public int compareTo(final ClientData obj) {
        if (obj == null) { return -1; }
        return new CompareToBuilder() //
                .append(this.id, obj.id) //
                .append(this.displayName, obj.displayName) //
                .append(this.mobileNo, obj.mobileNo) //
                .toComparison();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) { return false; }
        final ClientData rhs = (ClientData) obj;
        return new EqualsBuilder() //
                .append(this.id, rhs.id) //
                .append(this.displayName, rhs.displayName) //
                .append(this.mobileNo, rhs.mobileNo) //
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37) //
                .append(this.id) //
                .append(this.displayName) //
                .toHashCode();
    }

    public String getExternalId() {
        return this.externalId;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public LocalDate getActivationDate() {
        return this.activationDate;
    }

    public Boolean isActive() {
        return this.active;
    }
}
