package com.bpr.main.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ Author NMuchiri
 **/
@Data
public class CrbAAConsumer implements Serializable {
    @JsonProperty("GroupName")
    private String groupName;
    @JsonProperty("Income")
    private String income;
    @JsonProperty("CurrentBalance")
    private String currentBalance;
    @JsonProperty("PhysicalAddressCell")
    private String physicalAddressCell;
    @JsonProperty("Gender")
    private String gender;
    @JsonProperty("AccountStatus")
    private String accountStatus;
    @JsonProperty("AvailableCredit")
    private String availableCredit;
    @JsonProperty("DateClosed")
    private String dateClosed;
    @JsonProperty("NumberofDependants")
    private String numberofDependants;
    @JsonProperty("TermsDuration")
    private String termsDuration;
    @JsonProperty("Classification")
    private String classification;
    @JsonProperty("PhysicalAddressPostalCode")
    private String physicalAddressPostalCode;
    @JsonProperty("Facsimile")
    private String facsimile;
    @JsonProperty("HomeTelephone")
    private String homeTelephone;
    @JsonProperty("OldAccountNumber")
    private String oldAccountNumber;
    @JsonProperty("DrivingLicenseNumber")
    private String drivingLicenseNumber;
    @JsonProperty("MobileTelephone")
    private String mobileTelephone;
    @JsonProperty("ActualPaymentAmount")
    private String actualPaymentAmount;
    @JsonProperty("GroupNumber")
    private String groupNumber;
    @JsonProperty("LastPaymentDate")
    private String lastPaymentDate;
    @JsonProperty("InterestRateatDisbursement")
    private String interestRateatDisbursement;
    @JsonProperty("DateAccountClosed")
    private String dateAccountClosed;
    @JsonProperty("PostalAddressNumber")
    private String postalAddressNumber;
    @JsonProperty("EmailAddress")
    private String emailAddress;
    @JsonProperty("ApprovalDate")
    private String approvalDate;
    @JsonProperty("MaritalStatus")
    private String maritalStatus;
    @JsonProperty("FirstPaymentDate")
    private String firstPaymentDate;
    @JsonProperty("PlaceofBirth")
    private String placeofBirth;
    @JsonProperty("Country")
    private String country;
    @JsonProperty("PassportNumber")
    private String passportNumber;
    @JsonProperty("ForeName2")
    private String foreName2;
    @JsonProperty("DateofBirth")
    private String dateofBirth;
    @JsonProperty("DaysinArrears")
    private String daysinArrears;
    @JsonProperty("ForeName1")
    private String foreName1;
    @JsonProperty("IncomeFrequency")
    private String incomeFrequency;
    @JsonProperty("CurrencyType")
    private String currencyType;
    @JsonProperty("ForeName3")
    private String foreName3;
    @JsonProperty("InstallmentsinArrears")
    private String installmentsinArrears;
    @JsonProperty("Category")
    private String category;
    @JsonProperty("OpeningBalance")
    private String openingBalance;
    @JsonProperty("StudentNumber")
    private String studentNumber;
    @JsonProperty("PhysicalAddressLine1")
    private String physicalAddressLine1;
    @JsonProperty("PhysicalAddressLine2")
    private String physicalAddressLine2;
    @JsonProperty("FinalPaymentDate")
    private String finalPaymentDate;
    @JsonProperty("PostalCode")
    private String postalCode;
    @JsonProperty("Salutation")
    private String salutation;
    @JsonProperty("AccountRepaymentTerm")
    private String accountRepaymentTerm;
    @JsonProperty("PhysicalAddressSector")
    private String physicalAddressSector;
    @JsonProperty("PhysicalAddressProvince")
    private String physicalAddressProvince;
    @JsonProperty("ResidenceType")
    private String residenceType;
    @JsonProperty("CurrentBalanceIndicator")
    private String currentBalanceIndicator;
    @JsonProperty("EmployerAddressLine1")
    private String employerAddressLine1;
    @JsonProperty("RecordType")
    private String recordType;
    @JsonProperty("EmployerAddressLine2")
    private String employerAddressLine2;
    @JsonProperty("ScheduledPaymentAmount")
    private String scheduledPaymentAmount;
    @JsonProperty("TaxNumber")
    private String taxNumber;
    @JsonProperty("DateAccountOpened")
    private String dateAccountOpened;
    @JsonProperty("SectorOfActivity")
    private String sectorOfActivity;
    @JsonProperty("AccountOwner")
    private String accountOwner;
    @JsonProperty("Nature")
    private String nature;
    @JsonProperty("SocialSecurityNumber")
    private String socialSecurityNumber;
    @JsonProperty("PhysicalAddressPlotNumber")
    private String physicalAddressPlotNumber;
    @JsonProperty("NationalId")
    private String nationalId;
    @JsonProperty("NumberofLoanJoinParticipants")
    private String numberofLoanJoinParticipants;
    @JsonProperty("WorkTelephone")
    private String workTelephone;
    @JsonProperty("SurName")
    private String surName;
    @JsonProperty("AccountType")
    private String accountType;
    @JsonProperty("Nationality")
    private String nationality;
    @JsonProperty("AccountNumber")
    private String accountNumber;
    @JsonProperty("Occupation")
    private String occupation;
    @JsonProperty("EmployerTown")
    private String employerTown;
    @JsonProperty("EmployerCountry")
    private String employerCountry;
    @JsonProperty("EmployerName")
    private String employerName;
    @JsonProperty("AmountPastDue")
    private String amountPastDue;
    @JsonProperty("HealthInsuranceNumber")
    private String healthInsuranceNumber;
}
