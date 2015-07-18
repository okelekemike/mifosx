/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.portfolio.client.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.mifosplatform.infrastructure.codes.domain.CodeValue;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.ApiParameterError;
import org.mifosplatform.infrastructure.core.data.DataValidatorBuilder;
import org.mifosplatform.infrastructure.core.exception.PlatformApiDataValidationException;
import org.mifosplatform.infrastructure.core.service.DateUtils;
import org.mifosplatform.infrastructure.documentmanagement.domain.Image;
import org.mifosplatform.infrastructure.security.service.RandomPasswordGenerator;
import org.mifosplatform.organisation.office.domain.Office;
import org.mifosplatform.organisation.staff.domain.Staff;
import org.mifosplatform.portfolio.client.api.ClientApiConstants;
import org.mifosplatform.portfolio.group.domain.Group;
import org.mifosplatform.portfolio.savings.domain.SavingsAccount;
import org.mifosplatform.portfolio.savings.domain.SavingsProduct;
import org.mifosplatform.useradministration.domain.AppUser;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "m_client", uniqueConstraints = { @UniqueConstraint(columnNames = { "account_no" }, name = "account_no_UNIQUE"), //
        @UniqueConstraint(columnNames = { "mobile_no" }, name = "mobile_no_UNIQUE") })
public final class Client extends AbstractPersistable<Long> {

    @Column(name = "account_no", length = 20, unique = true, nullable = false)
    private String accountNumber;

    @ManyToOne
    @JoinColumn(name = "office_id", nullable = false)
    private Office office;

    @ManyToOne
    @JoinColumn(name = "transfer_to_office_id", nullable = true)
    private Office transferToOffice;

    @OneToOne(optional = true)
    @JoinColumn(name = "image_id", nullable = true)
    private Image image;

    /**
     * A value from {@link ClientStatus}.
     */
    @Column(name = "status_enum", nullable = false)
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_status", nullable = true)
    private CodeValue subStatus;
    
    @Column(name = "activation_date", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date activationDate;

    @Column(name = "office_joining_date", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date officeJoiningDate;

    @Column(name = "firstname", length = 50)
    private String firstname;

    @Column(name = "middlename", length = 50)
    private String middlename;

    @Column(name = "lastname", length = 50)
    private String lastname;

    @Column(name = "fullname", length = 100)
    private String fullname;

    @Column(name = "dependents", length = 11, nullable = true)
    private Long dependents;

    @Column(name = "display_name", length = 100, nullable = false)
    private String displayName;

    @Column(name = "mobile_no", length = 50, nullable = false, unique = true)
    private String mobileNo;

    @Column(name = "address_line_1", length = 500, nullable = true)
    private String addressLine1;

    @Column(name = "address_line_2", length = 500, nullable = true)
    private String addressLine2;

    @Column(name = "town", length = 50, nullable = true)
    private String town;

    @Column(name = "city", length = 50, nullable = true)
    private String city;

    @Column(name = "state", length = 50, nullable = true)
    private String state;

    @Column(name = "zip", length = 20, nullable = true)
    private String zip;

    @Column(name = "country", length = 50, nullable = true)
    private String country;
    
    @Column(name = "residence_no", length = 20, nullable = true)
    private String residenceNo;

    @Column(name = "email", length = 100, nullable = true)
    private String email;    

    @Column(name = "state_of_origin", length = 50, nullable = true)
    private String stateOfOrigin;    

    @Column(name = "lga_of_origin", length = 50, nullable = true)
    private String lgaOfOrigin;    

    @Column(name = "longitude", length = 20)
    private String longitude;

    @Column(name = "latitude", length = 20)
    private String latitude;

    @Column(name = "next_of_kin_firstname", length = 50)
    private String kinfirstname;

    @Column(name = "next_of_kin_lastname", length = 50)
    private String kinlastname;
    
    @Column(name = "next_of_kin_address_line_1", length = 500, nullable = true)
    private String kinaddressLine1;

    @Column(name = "next_of_kin_address_line_2", length = 500, nullable = true)
    private String kinaddressLine2;

    @Column(name = "next_of_kin_town", length = 50, nullable = true)
    private String kintown;

    @Column(name = "next_of_kin_city", length = 50, nullable = true)
    private String kincity;

    @Column(name = "next_of_kin_state", length = 50, nullable = true)
    private String kinstate;

    @Column(name = "next_of_kin_zip", length = 20, nullable = true)
    private String kinzip;

    @Column(name = "next_of_kin_country", length = 50, nullable = true)
    private String kincountry;
    
    @Column(name = "next_of_kin_residence_no", length = 20, nullable = true)
    private String kinresidenceNo;

    @Column(name = "next_of_kin_email", length = 100, nullable = true)
    private String kinemail;    

    @Column(name = "next_of_kin_longitude", length = 20)
    private String kinlongitude;

    @Column(name = "next_of_kin_latitude", length = 20)
    private String kinlatitude;

    @Column(name = "external_id", length = 100, nullable = true, unique = true)
    private String externalId;

    @Column(name = "date_of_birth", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gender_cv_id", nullable = true)
    private CodeValue gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marital_cv_id", nullable = true)
    private CodeValue marital;
    
    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    @JoinTable(name = "m_group_client", joinColumns = @JoinColumn(name = "client_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<Group> groups;

    @Transient
    private boolean accountNumberRequiresAutoGeneration = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "closure_reason_cv_id", nullable = true)
    private CodeValue closureReason;

    @Column(name = "closedon_date", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date closureDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reject_reason_cv_id", nullable = true)
    private CodeValue rejectionReason;

    @Column(name = "rejectedon_date", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date rejectionDate;

    @ManyToOne(optional = true)
    @JoinColumn(name = "rejectedon_userid", nullable = true)
    private AppUser rejectedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "withdraw_reason_cv_id", nullable = true)
    private CodeValue withdrawalReason;

    @Column(name = "withdrawn_on_date", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date withdrawalDate;

    @ManyToOne(optional = true)
    @JoinColumn(name = "withdraw_on_userid", nullable = true)
    private AppUser withdrawnBy;

    @Column(name = "reactivated_on_date", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date reactivateDate;

    @ManyToOne(optional = true)
    @JoinColumn(name = "reactivated_on_userid", nullable = true)
    private AppUser reactivatedBy;

    @ManyToOne(optional = true)
    @JoinColumn(name = "closedon_userid", nullable = true)
    private AppUser closedBy;

    @Column(name = "submittedon_date", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date submittedOnDate;

    @ManyToOne(optional = true)
    @JoinColumn(name = "submittedon_userid", nullable = true)
    private AppUser submittedBy;

    @Column(name = "updated_on", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date updatedOnDate;

    @ManyToOne(optional = true)
    @JoinColumn(name = "updated_by", nullable = true)
    private AppUser updatedBy;

    @ManyToOne(optional = true)
    @JoinColumn(name = "activatedon_userid", nullable = true)
    private AppUser activatedBy;

    @ManyToOne
    @JoinColumn(name = "default_savings_product", nullable = true)
    private SavingsProduct savingsProduct;

    @ManyToOne
    @JoinColumn(name = "default_savings_account", nullable = true)
    private SavingsAccount savingsAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_type_cv_id", nullable = true)
    private CodeValue clientType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_classification_cv_id", nullable = true)
    private CodeValue clientClassification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "next_of_kin_relationship_cv_id", nullable = true)
    private CodeValue kinRelationship;

    public static Client createNew(final AppUser currentUser, final Office clientOffice, final Group clientParentGroup, final Staff staff,
            final SavingsProduct savingsProduct, final CodeValue gender, final CodeValue marital, final CodeValue clientType, final CodeValue clientClassification,
            final CodeValue kinRelationship, final JsonCommand command) {

        final String accountNo = command.stringValueOfParameterNamed(ClientApiConstants.accountNoParamName);
        final String externalId = command.stringValueOfParameterNamed(ClientApiConstants.externalIdParamName);
        final String mobileNo = command.stringValueOfParameterNamed(ClientApiConstants.mobileNoParamName);

        final String firstname = command.stringValueOfParameterNamed(ClientApiConstants.firstnameParamName);
        final String middlename = command.stringValueOfParameterNamed(ClientApiConstants.middlenameParamName);
        final String lastname = command.stringValueOfParameterNamed(ClientApiConstants.lastnameParamName);
        final String fullname = command.stringValueOfParameterNamed(ClientApiConstants.fullnameParamName);
        final Long dependents = command.longValueOfParameterNamed(ClientApiConstants.dependentsParamName);

        final String addressLine1 = command.stringValueOfParameterNamed(ClientApiConstants.addressLine1ParamName);
        final String addressLine2 = command.stringValueOfParameterNamed(ClientApiConstants.addressLine2ParamName);
        final String town = command.stringValueOfParameterNamed(ClientApiConstants.townParamName);
        final String city = command.stringValueOfParameterNamed(ClientApiConstants.cityParamName);
        final String state = command.stringValueOfParameterNamed(ClientApiConstants.stateParamName);
        final String zip = command.stringValueOfParameterNamed(ClientApiConstants.zipParamName);
        final String country = command.stringValueOfParameterNamed(ClientApiConstants.countryParamName);
        final String residenceNo = command.stringValueOfParameterNamed(ClientApiConstants.residenceNoParamName);
        final String email = command.stringValueOfParameterNamed(ClientApiConstants.emailParamName);
        final String stateOfOrigin = command.stringValueOfParameterNamed(ClientApiConstants.stateOfOriginParamName);
        final String lgaOfOrigin = command.stringValueOfParameterNamed(ClientApiConstants.lgaOfOriginParamName);
        final String latitude = command.stringValueOfParameterNamed(ClientApiConstants.latitudeParamName);
        final String longitude = command.stringValueOfParameterNamed(ClientApiConstants.longitudeParamName);
        
        final String kinfirstname = command.stringValueOfParameterNamed(ClientApiConstants.kinFirstnameParamName);
        final String kinlastname = command.stringValueOfParameterNamed(ClientApiConstants.kinLastnameParamName);
        final String kinaddressLine1 = command.stringValueOfParameterNamed(ClientApiConstants.kinAddressLine1ParamName);
        final String kinaddressLine2 = command.stringValueOfParameterNamed(ClientApiConstants.kinAddressLine2ParamName);
        final String kintown = command.stringValueOfParameterNamed(ClientApiConstants.kinTownParamName);
        final String kincity = command.stringValueOfParameterNamed(ClientApiConstants.kinCityParamName);
        final String kinstate = command.stringValueOfParameterNamed(ClientApiConstants.kinStateParamName);
        final String kinzip = command.stringValueOfParameterNamed(ClientApiConstants.kinZipParamName);
        final String kincountry = command.stringValueOfParameterNamed(ClientApiConstants.kinCountryParamName);
        final String kinresidenceNo = command.stringValueOfParameterNamed(ClientApiConstants.kinResidenceNoParamName);
        final String kinemail = command.stringValueOfParameterNamed(ClientApiConstants.kinEmailParamName);
        final String kinlatitude = command.stringValueOfParameterNamed(ClientApiConstants.kinLatitudeParamName);
        final String kinlongitude = command.stringValueOfParameterNamed(ClientApiConstants.kinLongitudeParamName);
        
        final LocalDate dataOfBirth = command.localDateValueOfParameterNamed(ClientApiConstants.dateOfBirthParamName);

        ClientStatus status = ClientStatus.PENDING;
        boolean active = false;
        if (command.hasParameter("active")) {
            active = command.booleanPrimitiveValueOfParameterNamed(ClientApiConstants.activeParamName);
        }

        LocalDate activationDate = null;
        LocalDate officeJoiningDate = null;
        if (active) {
            status = ClientStatus.ACTIVE;
            activationDate = command.localDateValueOfParameterNamed(ClientApiConstants.activationDateParamName);
            officeJoiningDate = activationDate;
        }

        LocalDate submittedOnDate = new LocalDate();
        if (active && submittedOnDate.isAfter(activationDate)) {
            submittedOnDate = activationDate;
        }
        if (command.hasParameter(ClientApiConstants.submittedOnDateParamName)) {
            submittedOnDate = command.localDateValueOfParameterNamed(ClientApiConstants.submittedOnDateParamName);
        }
        final SavingsAccount account = null;
        return new Client(currentUser, status, clientOffice, clientParentGroup, accountNo, firstname, middlename, lastname, fullname, dependents,
                activationDate, officeJoiningDate, externalId, mobileNo, addressLine1, addressLine2, town, city, state, zip, country, latitude, longitude, 
                residenceNo, email, stateOfOrigin, lgaOfOrigin, kinfirstname, kinlastname, kinaddressLine1, kinaddressLine2, kintown, kincity, kinstate, kinzip, 
                kincountry, kinresidenceNo, kinemail, kinlatitude, kinlongitude, staff, submittedOnDate, savingsProduct, account, dataOfBirth, 
                gender, marital, clientType, clientClassification, kinRelationship);
    }

    protected Client() {
        //
    }

    private Client(final AppUser currentUser, final ClientStatus status, final Office office, final Group clientParentGroup,
            final String accountNo, final String firstname, final String middlename, final String lastname, final String fullname, final Long dependents,
            final LocalDate activationDate, final LocalDate officeJoiningDate, final String externalId, final String mobileNo, final String addressLine1, 
            final String addressLine2, final String town, final String city, final String state, final String zip, final String country, 
            final String residenceNo, final String email, final String stateOfOrigin, final String lgaOfOrigin, final String latitude, 
            final String longitude, final String kinfirstname, final String kinlastname, final String kinaddressLine1, final String kinaddressLine2, 
            final String kintown, final String kincity, final String kinstate, final String kinzip, final String kincountry, final String kinresidenceNo, 
            final String kinemail, final String kinlatitude, final String kinlongitude, final Staff staff, 
            final LocalDate submittedOnDate, final SavingsProduct savingsProduct, final SavingsAccount savingsAccount, final LocalDate dateOfBirth, 
            final CodeValue gender, final CodeValue marital, final CodeValue clientType, final CodeValue clientClassification, final CodeValue kinRelationship) {

        if (StringUtils.isBlank(accountNo)) {
            this.accountNumber = new RandomPasswordGenerator(19).generate();
            this.accountNumberRequiresAutoGeneration = true;
        } else {
            this.accountNumber = accountNo;
        }

        this.submittedOnDate = submittedOnDate.toDate();
        this.submittedBy = currentUser;

        this.status = status.getValue();
        this.office = office;
        if (StringUtils.isNotBlank(externalId)) {
            this.externalId = externalId.trim();
        } else {
            this.externalId = null;
        }

        if (StringUtils.isNotBlank(mobileNo)) {
            this.mobileNo = mobileNo.trim();
        } else {
            this.mobileNo = null;
        }

        if (activationDate != null) {
            this.activationDate = activationDate.toDateTimeAtStartOfDay().toDate();
            this.activatedBy = currentUser;
        }
        if (officeJoiningDate != null) {
            this.officeJoiningDate = officeJoiningDate.toDateTimeAtStartOfDay().toDate();
        }
        if (StringUtils.isNotBlank(firstname)) {
            this.firstname = firstname.trim();
        } else {
            this.firstname = null;
        }

        if (StringUtils.isNotBlank(middlename)) {
            this.middlename = middlename.trim();
        } else {
            this.middlename = null;
        }

        if (StringUtils.isNotBlank(lastname)) {
            this.lastname = lastname.trim();
        } else {
            this.lastname = null;
        }

        if (StringUtils.isNotBlank(fullname)) {
            this.fullname = fullname.trim();
        } else {
            this.fullname = null;
        }

        if (StringUtils.isNotBlank(addressLine1)) {
            this.addressLine1 = addressLine1.trim();
        } else {
            this.addressLine1 = null;
        }

        if (StringUtils.isNotBlank(addressLine2)) {
            this.addressLine2 = addressLine2.trim();
        } else {
            this.addressLine2 = null;
        }

        if (StringUtils.isNotBlank(town)) {
            this.town = town.trim();
        } else {
            this.town = null;
        }

        if (StringUtils.isNotBlank(city)) {
            this.city = city.trim();
        } else {
            this.city = null;
        }

        if (StringUtils.isNotBlank(state)) {
            this.state = state.trim();
        } else {
            this.state = null;
        }

        if (StringUtils.isNotBlank(zip)) {
            this.zip = zip.trim();
        } else {
            this.zip = null;
        }

        if (StringUtils.isNotBlank(country)) {
            this.country = country.trim();
        } else {
            this.country = null;
        }
        
        if (StringUtils.isNotBlank(residenceNo)) {
            this.residenceNo = residenceNo.trim();
        } else {
            this.residenceNo = null;
        }

        if (StringUtils.isNotBlank(email)) {
            this.email = email.trim();
        } else {
            this.email = null;
        }

        if (StringUtils.isNotBlank(stateOfOrigin)) {
            this.stateOfOrigin = stateOfOrigin.trim();
        } else {
            this.stateOfOrigin = null;
        }

        if (StringUtils.isNotBlank(lgaOfOrigin)) {
            this.lgaOfOrigin = lgaOfOrigin.trim();
        } else {
            this.lgaOfOrigin = null;
        }

        if (StringUtils.isNotBlank(latitude)) {
            this.latitude = latitude.trim();
        } else {
            this.latitude = null;
        }

        if (StringUtils.isNotBlank(longitude)) {
            this.longitude = longitude.trim();
        } else {
            this.longitude = null;
        }

        if (StringUtils.isNotBlank(kinfirstname)) {
            this.kinfirstname = kinfirstname.trim();
        } else {
            this.kinfirstname = null;
        }

        if (StringUtils.isNotBlank(kinlastname)) {
            this.kinlastname = kinlastname.trim();
        } else {
            this.kinlastname = null;
        }

        if (StringUtils.isNotBlank(kinaddressLine1)) {
            this.kinaddressLine1 = kinaddressLine1.trim();
        } else {
            this.kinaddressLine1 = null;
        }

        if (StringUtils.isNotBlank(kinaddressLine2)) {
            this.kinaddressLine2 = kinaddressLine2.trim();
        } else {
            this.kinaddressLine2 = null;
        }

        if (StringUtils.isNotBlank(kintown)) {
            this.kintown = kintown.trim();
        } else {
            this.kintown = null;
        }

        if (StringUtils.isNotBlank(kincity)) {
            this.kincity = kincity.trim();
        } else {
            this.kincity = null;
        }

        if (StringUtils.isNotBlank(kinstate)) {
            this.kinstate = kinstate.trim();
        } else {
            this.kinstate = null;
        }

        if (StringUtils.isNotBlank(kinzip)) {
            this.kinzip = kinzip.trim();
        } else {
            this.kinzip = null;
        }

        if (StringUtils.isNotBlank(kincountry)) {
            this.kincountry = kincountry.trim();
        } else {
            this.kincountry = null;
        }
        
        if (StringUtils.isNotBlank(kinresidenceNo)) {
            this.kinresidenceNo = kinresidenceNo.trim();
        } else {
            this.kinresidenceNo = null;
        }

        if (StringUtils.isNotBlank(kinemail)) {
            this.kinemail = kinemail.trim();
        } else {
            this.kinemail = null;
        }

        if (StringUtils.isNotBlank(kinlatitude)) {
            this.kinlatitude = kinlatitude.trim();
        } else {
            this.kinlatitude = null;
        }

        if (StringUtils.isNotBlank(kinlongitude)) {
            this.kinlongitude = kinlongitude.trim();
        } else {
            this.kinlongitude = null;
        }

        if (clientParentGroup != null) {
            this.groups = new HashSet<>();
            this.groups.add(clientParentGroup);
        }

        this.staff = staff;
        this.savingsProduct = savingsProduct;
        this.savingsAccount = savingsAccount;

        if (gender != null) {
            this.gender = gender;
        }

        if (marital != null) {
            this.marital = marital;
        }

        this.dependents = dependents;

        if (dateOfBirth != null) {
            this.dateOfBirth = dateOfBirth.toDateTimeAtStartOfDay().toDate();
        }
        this.clientType = clientType;
        this.clientClassification = clientClassification;
        this.kinRelationship = kinRelationship;

        deriveDisplayName();
        validate();
    }

    private void validate() {
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        validateNameParts(dataValidationErrors);
        validateActivationDate(dataValidationErrors);

        if (!dataValidationErrors.isEmpty()) { throw new PlatformApiDataValidationException(dataValidationErrors); }

    }

    public boolean isAccountNumberRequiresAutoGeneration() {
        return this.accountNumberRequiresAutoGeneration;
    }

    public void setAccountNumberRequiresAutoGeneration(final boolean accountNumberRequiresAutoGeneration) {
        this.accountNumberRequiresAutoGeneration = accountNumberRequiresAutoGeneration;
    }

    public boolean identifiedBy(final String identifier) {
        return identifier.equalsIgnoreCase(this.externalId);
    }

    public boolean identifiedBy(final Long clientId) {
        return getId().equals(clientId);
    }

    public void updateAccountNo(final String accountIdentifier) {
        this.accountNumber = accountIdentifier;
        this.accountNumberRequiresAutoGeneration = false;
    }

    public void activate(final AppUser currentUser, final DateTimeFormatter formatter, final LocalDate activationLocalDate) {

        if (isActive()) {
            final String defaultUserMessage = "Cannot activate client. Client is already active.";
            final ApiParameterError error = ApiParameterError.parameterError("error.msg.clients.already.active", defaultUserMessage,
                    ClientApiConstants.activationDateParamName, activationLocalDate.toString(formatter));

            final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
            dataValidationErrors.add(error);

            throw new PlatformApiDataValidationException(dataValidationErrors);
        }

        this.activationDate = activationLocalDate.toDate();
        this.activatedBy = currentUser;
        this.officeJoiningDate = this.activationDate;
        this.status = ClientStatus.ACTIVE.getValue();

        validate();
    }

    public boolean isNotActive() {
        return !isActive();
    }

    public boolean isActive() {
        return ClientStatus.fromInt(this.status).isActive();
    }

    public boolean isClosed() {
        return ClientStatus.fromInt(this.status).isClosed();
    }

    public boolean isTransferInProgress() {
        return ClientStatus.fromInt(this.status).isTransferInProgress();
    }

    public boolean isTransferOnHold() {
        return ClientStatus.fromInt(this.status).isTransferOnHold();
    }

    public boolean isTransferInProgressOrOnHold() {
        return isTransferInProgress() || isTransferOnHold();
    }

    public boolean isNotPending() {
        return !isPending();
    }

    public boolean isPending() {
        return ClientStatus.fromInt(this.status).isPending();
    }

    private boolean isDateInTheFuture(final LocalDate localDate) {
        return localDate.isAfter(DateUtils.getLocalDateOfTenant());
    }

    public Map<String, Object> update(final JsonCommand command) {

        final Map<String, Object> actualChanges = new LinkedHashMap<>(9);

        if (command.isChangeInIntegerParameterNamed(ClientApiConstants.statusParamName, this.status)) {
            final Integer newValue = command.integerValueOfParameterNamed(ClientApiConstants.statusParamName);
            actualChanges.put(ClientApiConstants.statusParamName, ClientEnumerations.status(newValue));
            this.status = ClientStatus.fromInt(newValue).getValue();
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.accountNoParamName, this.accountNumber)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.accountNoParamName);
            actualChanges.put(ClientApiConstants.accountNoParamName, newValue);
            this.accountNumber = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.externalIdParamName, this.externalId)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.externalIdParamName);
            actualChanges.put(ClientApiConstants.externalIdParamName, newValue);
            this.externalId = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.mobileNoParamName, this.mobileNo)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.mobileNoParamName);
            actualChanges.put(ClientApiConstants.mobileNoParamName, newValue);
            this.mobileNo = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.firstnameParamName, this.firstname)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.firstnameParamName);
            actualChanges.put(ClientApiConstants.firstnameParamName, newValue);
            this.firstname = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.middlenameParamName, this.middlename)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.middlenameParamName);
            actualChanges.put(ClientApiConstants.middlenameParamName, newValue);
            this.middlename = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.lastnameParamName, this.lastname)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.lastnameParamName);
            actualChanges.put(ClientApiConstants.lastnameParamName, newValue);
            this.lastname = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.fullnameParamName, this.fullname)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.fullnameParamName);
            actualChanges.put(ClientApiConstants.fullnameParamName, newValue);
            this.fullname = newValue;
        }

        if (command.isChangeInLongParameterNamed(ClientApiConstants.dependentsParamName, this.dependents)) {
            final Long newValue = command.longValueOfParameterNamed(ClientApiConstants.dependentsParamName);
            actualChanges.put(ClientApiConstants.dependentsParamName, newValue);
            this.dependents = newValue;
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.addressLine1ParamName, this.addressLine1)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.addressLine1ParamName);
            actualChanges.put(ClientApiConstants.addressLine1ParamName, newValue);
            this.addressLine1 = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.addressLine2ParamName, this.addressLine2)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.addressLine2ParamName);
            actualChanges.put(ClientApiConstants.addressLine2ParamName, newValue);
            this.addressLine2 = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.townParamName, this.town)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.townParamName);
            actualChanges.put(ClientApiConstants.townParamName, newValue);
            this.town = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.cityParamName, this.city)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.cityParamName);
            actualChanges.put(ClientApiConstants.cityParamName, newValue);
            this.city = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.stateParamName, this.state)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.stateParamName);
            actualChanges.put(ClientApiConstants.stateParamName, newValue);
            this.state = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.zipParamName, this.zip)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.zipParamName);
            actualChanges.put(ClientApiConstants.zipParamName, newValue);
            this.zip = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.countryParamName, this.country)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.countryParamName);
            actualChanges.put(ClientApiConstants.countryParamName, newValue);
            this.country = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.residenceNoParamName, this.residenceNo)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.residenceNoParamName);
            actualChanges.put(ClientApiConstants.residenceNoParamName, newValue);
            this.residenceNo = StringUtils.defaultIfEmpty(newValue, null);
        }
        
        if (command.isChangeInStringParameterNamed(ClientApiConstants.emailParamName, this.email)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.emailParamName);
            actualChanges.put(ClientApiConstants.emailParamName, newValue);
            this.email = StringUtils.defaultIfEmpty(newValue, null);
        }
        
        if (command.isChangeInStringParameterNamed(ClientApiConstants.stateOfOriginParamName, this.stateOfOrigin)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.stateOfOriginParamName);
            actualChanges.put(ClientApiConstants.stateOfOriginParamName, newValue);
            this.stateOfOrigin = StringUtils.defaultIfEmpty(newValue, null);
        }
        
        if (command.isChangeInStringParameterNamed(ClientApiConstants.lgaOfOriginParamName, this.lgaOfOrigin)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.lgaOfOriginParamName);
            actualChanges.put(ClientApiConstants.lgaOfOriginParamName, newValue);
            this.lgaOfOrigin = StringUtils.defaultIfEmpty(newValue, null);
        }
        
        if (command.isChangeInStringParameterNamed(ClientApiConstants.latitudeParamName, this.latitude)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.latitudeParamName);
            actualChanges.put(ClientApiConstants.latitudeParamName, newValue);
            this.latitude = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.longitudeParamName, this.longitude)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.longitudeParamName);
            actualChanges.put(ClientApiConstants.longitudeParamName, newValue);
            this.longitude = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.kinFirstnameParamName, this.kinfirstname)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.kinFirstnameParamName);
            actualChanges.put(ClientApiConstants.kinFirstnameParamName, newValue);
            this.kinfirstname = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.kinLastnameParamName, this.kinlastname)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.kinLastnameParamName);
            actualChanges.put(ClientApiConstants.kinLastnameParamName, newValue);
            this.kinlastname = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.kinAddressLine1ParamName, this.kinaddressLine1)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.kinAddressLine1ParamName);
            actualChanges.put(ClientApiConstants.kinAddressLine1ParamName, newValue);
            this.kinaddressLine1 = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.kinAddressLine2ParamName, this.kinaddressLine2)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.kinAddressLine2ParamName);
            actualChanges.put(ClientApiConstants.kinAddressLine2ParamName, newValue);
            this.kinaddressLine2 = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.kinTownParamName, this.kintown)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.kinTownParamName);
            actualChanges.put(ClientApiConstants.kinTownParamName, newValue);
            this.kintown = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.kinCityParamName, this.kincity)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.kinCityParamName);
            actualChanges.put(ClientApiConstants.kinCityParamName, newValue);
            this.kincity = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.kinStateParamName, this.kinstate)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.kinStateParamName);
            actualChanges.put(ClientApiConstants.kinStateParamName, newValue);
            this.kinstate = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.kinZipParamName, this.kinzip)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.kinZipParamName);
            actualChanges.put(ClientApiConstants.kinZipParamName, newValue);
            this.kinzip = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.kinCountryParamName, this.kincountry)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.kinCountryParamName);
            actualChanges.put(ClientApiConstants.kinCountryParamName, newValue);
            this.kincountry = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.kinResidenceNoParamName, this.kinresidenceNo)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.kinResidenceNoParamName);
            actualChanges.put(ClientApiConstants.kinResidenceNoParamName, newValue);
            this.kinresidenceNo = StringUtils.defaultIfEmpty(newValue, null);
        }
        
        if (command.isChangeInStringParameterNamed(ClientApiConstants.kinEmailParamName, this.kinemail)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.kinEmailParamName);
            actualChanges.put(ClientApiConstants.kinEmailParamName, newValue);
            this.kinemail = StringUtils.defaultIfEmpty(newValue, null);
        }
        
        if (command.isChangeInStringParameterNamed(ClientApiConstants.kinLatitudeParamName, this.kinlatitude)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.kinLatitudeParamName);
            actualChanges.put(ClientApiConstants.kinLatitudeParamName, newValue);
            this.kinlatitude = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.kinLongitudeParamName, this.kinlongitude)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.kinLongitudeParamName);
            actualChanges.put(ClientApiConstants.kinLongitudeParamName, newValue);
            this.kinlongitude = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInLongParameterNamed(ClientApiConstants.staffIdParamName, staffId())) {
            final Long newValue = command.longValueOfParameterNamed(ClientApiConstants.staffIdParamName);
            actualChanges.put(ClientApiConstants.staffIdParamName, newValue);
        }

        if (command.isChangeInLongParameterNamed(ClientApiConstants.genderIdParamName, genderId())) {
            final Long newValue = command.longValueOfParameterNamed(ClientApiConstants.genderIdParamName);
            actualChanges.put(ClientApiConstants.genderIdParamName, newValue);
        }

        if (command.isChangeInLongParameterNamed(ClientApiConstants.savingsProductIdParamName, savingsProductId())) {
            final Long newValue = command.longValueOfParameterNamed(ClientApiConstants.savingsProductIdParamName);
            actualChanges.put(ClientApiConstants.savingsProductIdParamName, newValue);
        }

        if (command.isChangeInLongParameterNamed(ClientApiConstants.maritalIdParamName, maritalId())) {
            final Long newValue = command.longValueOfParameterNamed(ClientApiConstants.maritalIdParamName);
            actualChanges.put(ClientApiConstants.maritalIdParamName, newValue);
        }

        if (command.isChangeInLongParameterNamed(ClientApiConstants.clientTypeIdParamName, clientTypeId())) {
            final Long newValue = command.longValueOfParameterNamed(ClientApiConstants.clientTypeIdParamName);
            actualChanges.put(ClientApiConstants.clientTypeIdParamName, newValue);
        }

        if (command.isChangeInLongParameterNamed(ClientApiConstants.clientClassificationIdParamName, clientClassificationId())) {
            final Long newValue = command.longValueOfParameterNamed(ClientApiConstants.clientClassificationIdParamName);
            actualChanges.put(ClientApiConstants.clientClassificationIdParamName, newValue);
        }

        if (command.isChangeInLongParameterNamed(ClientApiConstants.kinRelationshipIdParamName, kinRelationshipId())) {
            final Long newValue = command.longValueOfParameterNamed(ClientApiConstants.kinRelationshipIdParamName);
            actualChanges.put(ClientApiConstants.kinRelationshipIdParamName, newValue);
        }

        final String dateFormatAsInput = command.dateFormat();
        final String localeAsInput = command.locale();

        if (command.isChangeInLocalDateParameterNamed(ClientApiConstants.activationDateParamName, getActivationLocalDate())) {
            final String valueAsInput = command.stringValueOfParameterNamed(ClientApiConstants.activationDateParamName);
            actualChanges.put(ClientApiConstants.activationDateParamName, valueAsInput);
            actualChanges.put(ClientApiConstants.dateFormatParamName, dateFormatAsInput);
            actualChanges.put(ClientApiConstants.localeParamName, localeAsInput);

            final LocalDate newValue = command.localDateValueOfParameterNamed(ClientApiConstants.activationDateParamName);
            this.activationDate = newValue.toDate();
            this.officeJoiningDate = this.activationDate;
        }

        if (command.isChangeInLocalDateParameterNamed(ClientApiConstants.dateOfBirthParamName, dateOfBirthLocalDate())) {
            final String valueAsInput = command.stringValueOfParameterNamed(ClientApiConstants.dateOfBirthParamName);
            actualChanges.put(ClientApiConstants.dateOfBirthParamName, valueAsInput);
            actualChanges.put(ClientApiConstants.dateFormatParamName, dateFormatAsInput);
            actualChanges.put(ClientApiConstants.localeParamName, localeAsInput);

            final LocalDate newValue = command.localDateValueOfParameterNamed(ClientApiConstants.dateOfBirthParamName);
            this.dateOfBirth = newValue.toDate();
        }

        if (command.isChangeInLocalDateParameterNamed(ClientApiConstants.submittedOnDateParamName, getSubmittedOnDate())) {
            final String valueAsInput = command.stringValueOfParameterNamed(ClientApiConstants.submittedOnDateParamName);
            actualChanges.put(ClientApiConstants.submittedOnDateParamName, valueAsInput);
            actualChanges.put(ClientApiConstants.dateFormatParamName, dateFormatAsInput);
            actualChanges.put(ClientApiConstants.localeParamName, localeAsInput);

            final LocalDate newValue = command.localDateValueOfParameterNamed(ClientApiConstants.submittedOnDateParamName);
            this.submittedOnDate = newValue.toDate();
        }

        validate();

        deriveDisplayName();

        return actualChanges;
    }

    private void validateNameParts(final List<ApiParameterError> dataValidationErrors) {
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("client");

        if (StringUtils.isNotBlank(this.fullname)) {

            baseDataValidator.reset().parameter(ClientApiConstants.firstnameParamName).value(this.firstname)
                    .mustBeBlankWhenParameterProvided(ClientApiConstants.fullnameParamName, this.fullname);

            baseDataValidator.reset().parameter(ClientApiConstants.middlenameParamName).value(this.middlename)
                    .mustBeBlankWhenParameterProvided(ClientApiConstants.fullnameParamName, this.fullname);

            baseDataValidator.reset().parameter(ClientApiConstants.lastnameParamName).value(this.lastname)
                    .mustBeBlankWhenParameterProvided(ClientApiConstants.fullnameParamName, this.fullname);
        } else {

            baseDataValidator.reset().parameter(ClientApiConstants.firstnameParamName).value(this.firstname).notBlank()
                    .notExceedingLengthOf(50);
            baseDataValidator.reset().parameter(ClientApiConstants.middlenameParamName).value(this.middlename).ignoreIfNull()
                    .notExceedingLengthOf(50);
            baseDataValidator.reset().parameter(ClientApiConstants.lastnameParamName).value(this.lastname).notBlank()
                    .notExceedingLengthOf(50);
        }
    }

    private void validateActivationDate(final List<ApiParameterError> dataValidationErrors) {

        if (getSubmittedOnDate() != null && isDateInTheFuture(getSubmittedOnDate())) {

            final String defaultUserMessage = "submitted date cannot be in the future.";
            final ApiParameterError error = ApiParameterError.parameterError("error.msg.clients.submittedOnDate.in.the.future",
                    defaultUserMessage, ClientApiConstants.submittedOnDateParamName, this.submittedOnDate);

            dataValidationErrors.add(error);
        }

        if (getActivationLocalDate() != null && getSubmittedOnDate() != null && getSubmittedOnDate().isAfter(getActivationLocalDate())) {

            final String defaultUserMessage = "submitted date cannot be after the activation date";
            final ApiParameterError error = ApiParameterError.parameterError("error.msg.clients.submittedOnDate.after.activation.date",
                    defaultUserMessage, ClientApiConstants.submittedOnDateParamName, this.submittedOnDate);

            dataValidationErrors.add(error);
        }

        if (getActivationLocalDate() != null && isDateInTheFuture(getActivationLocalDate())) {

            final String defaultUserMessage = "Activation date cannot be in the future.";
            final ApiParameterError error = ApiParameterError.parameterError("error.msg.clients.activationDate.in.the.future",
                    defaultUserMessage, ClientApiConstants.activationDateParamName, getActivationLocalDate());

            dataValidationErrors.add(error);
        }

        if (getActivationLocalDate() != null) {
            if (this.office.isOpeningDateAfter(getActivationLocalDate())) {
                final String defaultUserMessage = "Client activation date cannot be a date before the office opening date.";
                final ApiParameterError error = ApiParameterError.parameterError(
                        "error.msg.clients.activationDate.cannot.be.before.office.activation.date", defaultUserMessage,
                        ClientApiConstants.activationDateParamName, getActivationLocalDate());
                dataValidationErrors.add(error);
            }
        }
    }

    private void deriveDisplayName() {

        StringBuilder nameBuilder = new StringBuilder();
        if (StringUtils.isNotBlank(this.firstname)) {
            nameBuilder.append(this.firstname).append(' ');
        }

        if (StringUtils.isNotBlank(this.middlename)) {
            nameBuilder.append(this.middlename).append(' ');
        }

        if (StringUtils.isNotBlank(this.lastname)) {
            nameBuilder.append(this.lastname);
        }

        if (StringUtils.isNotBlank(this.fullname)) {
            nameBuilder = new StringBuilder(this.fullname);
        }

        this.displayName = nameBuilder.toString();
    }

    public LocalDate getSubmittedOnDate() {
        return (LocalDate) ObjectUtils.defaultIfNull(new LocalDate(this.submittedOnDate), null);
    }

    public LocalDate getActivationLocalDate() {
        LocalDate activationLocalDate = null;
        if (this.activationDate != null) {
            activationLocalDate = LocalDate.fromDateFields(this.activationDate);
        }
        return activationLocalDate;
    }

    public LocalDate getOfficeJoiningLocalDate() {
        LocalDate officeJoiningLocalDate = null;
        if (this.officeJoiningDate != null) {
            officeJoiningLocalDate = LocalDate.fromDateFields(this.officeJoiningDate);
        }
        return officeJoiningLocalDate;
    }

    public boolean isOfficeIdentifiedBy(final Long officeId) {
        return this.office.identifiedBy(officeId);
    }

    public Long officeId() {
        return this.office.getId();
    }

    public void setImage(final Image image) {
        this.image = image;
    }

    public Image getImage() {
        return this.image;
    }

    public String mobileNo() {
        return this.mobileNo;
    }

    public void setMobileNo(final String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    public Office getOffice() {
        return this.office;
    }

    public Office getTransferToOffice() {
        return this.transferToOffice;
    }

    public void updateOffice(final Office office) {
        this.office = office;
    }

    public void updateTransferToOffice(final Office office) {
        this.transferToOffice = office;
    }

    public void updateOfficeJoiningDate(final Date date) {
        this.officeJoiningDate = date;
    }

    private Long staffId() {
        Long staffId = null;
        if (this.staff != null) {
            staffId = this.staff.getId();
        }
        return staffId;
    }

    public void updateStaff(final Staff staff) {
        this.staff = staff;
    }

    public Staff getStaff() {
        return this.staff;
    }

    public void unassignStaff() {
        this.staff = null;
    }

    public void assignStaff(final Staff staff) {
        this.staff = staff;
    }

    public Set<Group> getGroups() {
        return this.groups;
    }

    public void close(final AppUser currentUser, final CodeValue closureReason, final Date closureDate) {
        this.closureReason = closureReason;
        this.closureDate = closureDate;
        this.closedBy = currentUser;
        this.status = ClientStatus.CLOSED.getValue();
    }

    public Integer getStatus() {
        return this.status;
    }
    
    public CodeValue subStatus() {
        return this.subStatus;
    }
    
    public Long subStatusId() {
        Long subStatusId = null;
        if (this.subStatus != null) {
            subStatusId = this.subStatus.getId();
        }
        return subStatusId;
    }

    public void setStatus(final Integer status) {
        this.status = status;
    }

    public boolean isActivatedAfter(final LocalDate submittedOn) {
        return getActivationLocalDate().isAfter(submittedOn);
    }

    public boolean isChildOfGroup(final Long groupId) {
        if (groupId != null && this.groups != null && !this.groups.isEmpty()) {
            for (final Group group : this.groups) {
                if (group.getId().equals(groupId)) { return true; }
            }
        }
        return false;
    }

    private Long savingsProductId() {
        Long savingsProductId = null;
        if (this.savingsProduct != null) {
            savingsProductId = this.savingsProduct.getId();
        }
        return savingsProductId;
    }

    public SavingsProduct SavingsProduct() {
        return this.savingsProduct;
    }

    public void updateSavingsProduct(SavingsProduct savingsProduct) {
        this.savingsProduct = savingsProduct;
    }

    public AppUser activatedBy() {
        return this.activatedBy;
    }

    public SavingsAccount savingsAccount() {
        return this.savingsAccount;
    }

    public void updateSavingsAccount(SavingsAccount savingsAccount) {
        this.savingsAccount = savingsAccount;
    }

    public Long genderId() {
        Long genderId = null;
        if (this.gender != null) {
            genderId = this.gender.getId();
        }
        return genderId;
    }

    public Long maritalId() {
        Long maritalId = null;
        if (this.marital != null) {
            maritalId = this.marital.getId();
        }
        return maritalId;
    }

    public Long clientTypeId() {
        Long clientTypeId = null;
        if (this.clientType != null) {
            clientTypeId = this.clientType.getId();
        }
        return clientTypeId;
    }

    public Long clientClassificationId() {
        Long clientClassificationId = null;
        if (this.clientClassification != null) {
            clientClassificationId = this.clientClassification.getId();
        }
        return clientClassificationId;
    }

    public Long kinRelationshipId() {
        Long kinRelationshipId = null;
        if (this.kinRelationship != null) {
            kinRelationshipId = this.kinRelationship.getId();
        }
        return kinRelationshipId;
    }

    public LocalDate getClosureDate() {
        return (LocalDate) ObjectUtils.defaultIfNull(new LocalDate(this.closureDate), null);
    }

    public CodeValue gender() {
        return this.gender;
    }

    public CodeValue marital() { return  this.marital; }

    public CodeValue clientType() {
        return this.clientType;
    }

    public void updateClientType(CodeValue clientType) {
        this.clientType = clientType;
    }

    public CodeValue clientClassification() {
        return this.clientClassification;
    }

    public void updateClientClassification(CodeValue clientClassification) {
        this.clientClassification = clientClassification;
    }

    public CodeValue kinRelationship() {
        return this.kinRelationship;
    }
    
    public void updateKinRelationship(CodeValue kinRelationship) {
        this.kinRelationship = kinRelationship;
    }

    public void updateGender(CodeValue gender) {
        this.gender = gender;
    }

    public void updateMarital(CodeValue marital) {
        this.marital = marital;
    }

    public Date dateOfBirth() {
        return this.dateOfBirth;
    }

    public LocalDate dateOfBirthLocalDate() {
        LocalDate dateOfBirth = null;
        if (this.dateOfBirth != null) {
            dateOfBirth = LocalDate.fromDateFields(this.dateOfBirth);
        }
        return dateOfBirth;
    }

    public void reject(AppUser currentUser, CodeValue rejectionReason, Date rejectionDate) {
        this.rejectionReason = rejectionReason;
        this.rejectionDate = rejectionDate;
        this.rejectedBy = currentUser;
        this.updatedBy = currentUser;
        this.updatedOnDate = rejectionDate;
        this.status = ClientStatus.REJECTED.getValue();

    }

    public void withdraw(AppUser currentUser, CodeValue withdrawalReason, Date withdrawalDate) {
        this.withdrawalReason = withdrawalReason;
        this.withdrawalDate = withdrawalDate;
        this.withdrawnBy = currentUser;
        this.updatedBy = currentUser;
        this.updatedOnDate = withdrawalDate;
        this.status = ClientStatus.WITHDRAWN.getValue();

    }

    public void reActivate(AppUser currentUser, Date reactivateDate) {
        this.closureDate = null;
        this.reactivateDate = reactivateDate;
        this.reactivatedBy = currentUser;
        this.updatedBy = currentUser;
        this.updatedOnDate = reactivateDate;
        this.status = ClientStatus.PENDING.getValue();

    }
}