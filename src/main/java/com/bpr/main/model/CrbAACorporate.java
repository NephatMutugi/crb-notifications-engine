package com.bpr.main.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ Author NMuchiri
 **/
@Data
public class CrbAACorporate implements Serializable {
    @JsonProperty("CompanyCeaseDate")
    private String companyCeaseDate;
    @JsonProperty("CurrentBalance")
    private String currentBalance;
    @JsonProperty("PhysicalAddressCell")
    private String physicalAddressCell;
    @JsonProperty("Institution")
    private String institution;
    @JsonProperty("Industry")
    private String industry;
    @JsonProperty("AccountStatus")
    private String accountStatus;
    @JsonProperty("AvailableCredit")
    private String availableCredit;
    @JsonProperty("DateClosed")
    private String dateClosed;
    @JsonProperty("TermsDuration")
    private String termsDuration;
    @JsonProperty("Classification")
    private String classification;
    @JsonProperty("PhysicalAddressPostalCode")
    private String physicalAddressPostalCode;
    @JsonProperty("ActualPaymentAmount")
    private String actualPaymentAmount;
    @JsonProperty("LastPaymentDate")
    private String lastPaymentDate;
    @JsonProperty("Telephone5")
    private String telephone5;
    @JsonProperty("Telephone4")
    private String telephone4;
    @JsonProperty("InterestRateatDisbursement")
    private String interestRateatDisbursement;
    @JsonProperty("Telephone6")
    private String telephone6;
    @JsonProperty("DateAccountClosed")
    private String dateAccountClosed;
    @JsonProperty("PostalAddressNumber")
    private String postalAddressNumber;
    @JsonProperty("EmailAddress")
    private String emailAddress;
    @JsonProperty("ApprovalDate")
    private String approvalDate;
    @JsonProperty("Telephone1")
    private String telephone1;
    @JsonProperty("FirstPaymentDate")
    private String firstPaymentDate;
    @JsonProperty("Telephone3")
    private String telephone3;
    @JsonProperty("Telephone2")
    private String telephone2;
    @JsonProperty("Country")
    private String country;
    @JsonProperty("TradingName")
    private String tradingName;
    @JsonProperty("DaysinArrears")
    private String daysinArrears;
    @JsonProperty("CurrencyType")
    private String currencyType;
    @JsonProperty("VATNumber")
    private String vATNumber;
    @JsonProperty("InstallmentsinArrears")
    private String installmentsinArrears;
    @JsonProperty("Category")
    private String category;
    @JsonProperty("Record Type")
    private String recordType;
    @JsonProperty("OpeningBalance")
    private String openingBalance;
    @JsonProperty("PhysicalAddressLine1")
    private String physicalAddressLine1;
    @JsonProperty("PhysicalAddressLine2")
    private String physicalAddressLine2;
    @JsonProperty("FinalPaymentDate")
    private String finalPaymentDate;
    @JsonProperty("PostalCode")
    private String postalCode;
    @JsonProperty("AccountRepaymentTerm")
    private String accountRepaymentTerm;
    @JsonProperty("PhysicalAddressSector")
    private String physicalAddressSector;
    @JsonProperty("CompanyRegistrationNumber")
    private String companyRegistrationNumber;
    @JsonProperty("PhysicalAddressProvince")
    private String physicalAddressProvince;
    @JsonProperty("CurrentBalanceIndicator")
    private String currentBalanceIndicator;
    @JsonProperty("CompanyRegistrationDate")
    private String companyRegistrationDate;
    @JsonProperty("ScheduledPaymentAmount")
    private String scheduledPaymentAmount;
    @JsonProperty("TaxNumber")
    private String taxNumber;
    @JsonProperty("Facsimile1")
    private String facsimile1;
    @JsonProperty("DateAccountOpened")
    private String dateAccountOpened;
    @JsonProperty("Facsimile2")
    private String facsimile2;
    @JsonProperty("SectorOfActivity")
    private String sectorOfActivity;
    @JsonProperty("AccountOwner")
    private String accountOwner;
    @JsonProperty("Nature")
    private String nature;
    @JsonProperty("PhysicalAddressPlotNumber")
    private String physicalAddressPlotNumber;
    @JsonProperty("NumberofLoanJoinParticipants")
    private String numberofLoanJoinParticipants;
    @JsonProperty("AccountType")
    private String accountType;
    @JsonProperty("AccountNumber")
    private String accountNumber;
    @JsonProperty("AmountPastDue")
    private String amountPastDue;
}
