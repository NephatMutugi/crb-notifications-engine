package com.bpr.main.service.impl;

import com.bpr.main.model.CorporateCreditInformationRecord;
import com.bpr.main.model.CorporateRequestPayload;
import com.bpr.main.model.CrbAACorporate;
import com.bpr.main.service.CorporateMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @ Author NMuchiri
 **/
@Service
@Slf4j
public class CorporateMappingServiceImpl implements CorporateMappingService {

    @Override
    public CorporateRequestPayload mapT24PayloadToCrb(CrbAACorporate crbAACorporate, String recordType) {
        CorporateRequestPayload corporateRequestPayload = new CorporateRequestPayload();
        CorporateCreditInformationRecord corporateCreditInformationRecord = new CorporateCreditInformationRecord();

        corporateCreditInformationRecord.setAccountNumber(crbAACorporate.getAccountNumber());
        corporateCreditInformationRecord.setAccountOwner(crbAACorporate.getAccountOwner());
        corporateCreditInformationRecord.setAccountRepaymentTerm(crbAACorporate.getAccountRepaymentTerm());
        corporateCreditInformationRecord.setAccountStatus(crbAACorporate.getAccountStatus());
        corporateCreditInformationRecord.setAccountType(crbAACorporate.getAccountType());
        corporateCreditInformationRecord.setActualPaymentAmount(crbAACorporate.getActualPaymentAmount());
        corporateCreditInformationRecord.setAmountPastDue(crbAACorporate.getAmountPastDue());
        corporateCreditInformationRecord.setApprovalDate(crbAACorporate.getApprovalDate());
        corporateCreditInformationRecord.setAvailableCredit(crbAACorporate.getAvailableCredit());
        corporateCreditInformationRecord.setCategory(crbAACorporate.getCategory());
        corporateCreditInformationRecord.setClassification(crbAACorporate.getClassification());
        corporateCreditInformationRecord.setCompanyCeaseDate(crbAACorporate.getCompanyCeaseDate());
        corporateCreditInformationRecord.setCompanyRegistrationDate(crbAACorporate.getCompanyRegistrationDate());
        corporateCreditInformationRecord.setCountry(crbAACorporate.getCountry());
        corporateCreditInformationRecord.setCurrencyType(crbAACorporate.getCurrencyType());
        corporateCreditInformationRecord.setCurrentBalance(crbAACorporate.getCurrentBalance());
        corporateCreditInformationRecord.setCurrentBalanceIndicator(crbAACorporate.getCurrentBalanceIndicator());
        corporateCreditInformationRecord.setDateAccountOpened(crbAACorporate.getDateAccountOpened());
        corporateCreditInformationRecord.setDateClosed(crbAACorporate.getDateClosed());
        corporateCreditInformationRecord.setDaysInArrears(crbAACorporate.getDaysinArrears());
        corporateCreditInformationRecord.setEmailAddress(crbAACorporate.getEmailAddress());
        corporateCreditInformationRecord.setFacsimile1(crbAACorporate.getFacsimile1());
        corporateCreditInformationRecord.setFacsimile2(crbAACorporate.getFacsimile2());
        corporateCreditInformationRecord.setFinalPaymentDate(crbAACorporate.getFinalPaymentDate());
        corporateCreditInformationRecord.setFirstPaymentDate(crbAACorporate.getFirstPaymentDate());
        corporateCreditInformationRecord.setIndustry(crbAACorporate.getIndustry());
        corporateCreditInformationRecord.setInstallmentsInArrears(crbAACorporate.getInstallmentsinArrears());
        corporateCreditInformationRecord.setInstitution(crbAACorporate.getInstitution());
        corporateCreditInformationRecord.setInterestRateAtDisbursement(crbAACorporate.getInterestRateatDisbursement());
        corporateCreditInformationRecord.setLastPaymentDate(crbAACorporate.getLastPaymentDate());
        corporateCreditInformationRecord.setNature(crbAACorporate.getNature());
        corporateCreditInformationRecord.setNumberOfJointLoanParticipants(crbAACorporate.getNumberofLoanJoinParticipants());
        corporateCreditInformationRecord.setOpeningBalance(crbAACorporate.getOpeningBalance());
        corporateCreditInformationRecord.setPhysicalAddressCell(crbAACorporate.getPhysicalAddressCell());
        corporateCreditInformationRecord.setPhysicalAddressLine1(crbAACorporate.getPhysicalAddressLine1());
        corporateCreditInformationRecord.setPhysicalAddressLine2(crbAACorporate.getPhysicalAddressLine2());
        corporateCreditInformationRecord.setPhysicalAddressPlotNumber(crbAACorporate.getPhysicalAddressPlotNumber());
        corporateCreditInformationRecord.setPhysicalAddressPostalCode(crbAACorporate.getPhysicalAddressPostalCode());
        corporateCreditInformationRecord.setPhysicalAddressProvince(crbAACorporate.getPhysicalAddressProvince());
        corporateCreditInformationRecord.setPhysicalAddressSector(crbAACorporate.getPhysicalAddressSector());
        corporateCreditInformationRecord.setPostalAddressNumber(crbAACorporate.getPostalAddressNumber());
        corporateCreditInformationRecord.setPostalCode(crbAACorporate.getPostalCode());
        corporateCreditInformationRecord.setScheduledPaymentAmount(crbAACorporate.getScheduledPaymentAmount());
        corporateCreditInformationRecord.setSectorOfActivity(crbAACorporate.getSectorOfActivity());
        corporateCreditInformationRecord.setTaxNo(crbAACorporate.getTaxNumber());
        corporateCreditInformationRecord.setTelephone1(crbAACorporate.getTelephone1());
        corporateCreditInformationRecord.setTelephone2(crbAACorporate.getTelephone2());
        corporateCreditInformationRecord.setTelephone3(crbAACorporate.getTelephone3());
        corporateCreditInformationRecord.setTelephone4(crbAACorporate.getTelephone4());
        corporateCreditInformationRecord.setTelephone5(crbAACorporate.getTelephone5());
        corporateCreditInformationRecord.setTelephone6(crbAACorporate.getTelephone6());
        corporateCreditInformationRecord.setTermsDuration(crbAACorporate.getTermsDuration());
        corporateCreditInformationRecord.setTradingName(crbAACorporate.getTradingName());
        corporateCreditInformationRecord.setVatNo(crbAACorporate.getVATNumber());

        corporateRequestPayload.setCorporateCreditInformationRecord(corporateCreditInformationRecord);
        corporateRequestPayload.setRecordType(recordType);
        return corporateRequestPayload;
    }
}
