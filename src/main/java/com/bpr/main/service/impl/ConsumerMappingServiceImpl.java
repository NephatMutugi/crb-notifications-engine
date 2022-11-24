package com.bpr.main.service.impl;

import com.bpr.main.model.ConsumerCreditInformationRecord;
import com.bpr.main.model.ConsumerRequestPayload;
import com.bpr.main.model.CrbAAConsumer;
import com.bpr.main.service.ConsumerMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @ Author NMuchiri
 **/
@Service
@Slf4j
public class ConsumerMappingServiceImpl implements ConsumerMappingService {

    @Override
    public ConsumerRequestPayload mapT24PayloadToCrb(CrbAAConsumer crbAAConsumer, String recordType) {
        ConsumerRequestPayload consumerRequestPayload = new ConsumerRequestPayload();
        ConsumerCreditInformationRecord consumerCreditInformationRecord = new ConsumerCreditInformationRecord();

        consumerCreditInformationRecord.setAccountNumber(crbAAConsumer.getAccountNumber());
        consumerCreditInformationRecord.setAccountOwner(crbAAConsumer.getAccountOwner());
        consumerCreditInformationRecord.setAccountRepaymentTerm(crbAAConsumer.getAccountRepaymentTerm());
        consumerCreditInformationRecord.setAccountStatus(crbAAConsumer.getAccountStatus());
        consumerCreditInformationRecord.setAccountType(crbAAConsumer.getAccountType());
        consumerCreditInformationRecord.setActualPaymentAmount(crbAAConsumer.getActualPaymentAmount());
        consumerCreditInformationRecord.setAmountPastDue(crbAAConsumer.getAmountPastDue());
        consumerCreditInformationRecord.setApprovalDate(crbAAConsumer.getApprovalDate());
        consumerCreditInformationRecord.setAvailableCredit(crbAAConsumer.getAvailableCredit());
        consumerCreditInformationRecord.setCategory(crbAAConsumer.getCategory());
        consumerCreditInformationRecord.setClassification(crbAAConsumer.getClassification());
        consumerCreditInformationRecord.setCountry(crbAAConsumer.getCountry());
        consumerCreditInformationRecord.setCurrencyType(crbAAConsumer.getCurrencyType());
        consumerCreditInformationRecord.setCurrentBalance(crbAAConsumer.getCurrentBalance());
        consumerCreditInformationRecord.setCurrentBalanceIndicator(crbAAConsumer.getCurrentBalanceIndicator());
        consumerCreditInformationRecord.setDateAccountOpened(crbAAConsumer.getDateAccountOpened());
        consumerCreditInformationRecord.setDateAccountUpdated(crbAAConsumer.getDateAccountOpened());
        consumerCreditInformationRecord.setDateClosed(crbAAConsumer.getDateClosed());
        consumerCreditInformationRecord.setDateOfBirth(crbAAConsumer.getDateofBirth());
        consumerCreditInformationRecord.setDaysInArrears(crbAAConsumer.getDaysinArrears());
        consumerCreditInformationRecord.setEmailAddress(crbAAConsumer.getEmailAddress());
        consumerCreditInformationRecord.setEmployerAddressLine1(crbAAConsumer.getEmployerAddressLine1());
        consumerCreditInformationRecord.setEmployerAddressLine2(crbAAConsumer.getEmployerAddressLine2());
        consumerCreditInformationRecord.setEmployerCountry(crbAAConsumer.getEmployerCountry());
        consumerCreditInformationRecord.setEmployerName(crbAAConsumer.getEmployerName());
        consumerCreditInformationRecord.setEmployerTown(crbAAConsumer.getEmployerTown());
        consumerCreditInformationRecord.setFacsimile(crbAAConsumer.getFacsimile());
        consumerCreditInformationRecord.setFinalPaymentDate(crbAAConsumer.getFinalPaymentDate());
        consumerCreditInformationRecord.setFirstPaymentDate(crbAAConsumer.getFirstPaymentDate());
        consumerCreditInformationRecord.setForeName1(crbAAConsumer.getForeName1());
        consumerCreditInformationRecord.setForeName2(crbAAConsumer.getForeName2());
        consumerCreditInformationRecord.setGender(crbAAConsumer.getGender());
        consumerCreditInformationRecord.setGroupName(crbAAConsumer.getGroupName());
        consumerCreditInformationRecord.setGroupNumber(crbAAConsumer.getGroupNumber());
        consumerCreditInformationRecord.setHealthInsuranceNumber(crbAAConsumer.getHealthInsuranceNumber());
        consumerCreditInformationRecord.setHomeTelephone(crbAAConsumer.getHomeTelephone());
        consumerCreditInformationRecord.setIncome(crbAAConsumer.getIncome());
        consumerCreditInformationRecord.setIncomeFrequency(crbAAConsumer.getIncomeFrequency());
        consumerCreditInformationRecord.setInstallmentsInArrears(crbAAConsumer.getInstallmentsinArrears());
        consumerCreditInformationRecord.setInterestRateAtDisbursement(crbAAConsumer.getInterestRateatDisbursement());
        consumerCreditInformationRecord.setLastPaymentDate(crbAAConsumer.getLastPaymentDate());
        consumerCreditInformationRecord.setMaritalStatus(crbAAConsumer.getMaritalStatus());
        consumerCreditInformationRecord.setMobileTelephone(crbAAConsumer.getMobileTelephone());
        consumerCreditInformationRecord.setNationalId(crbAAConsumer.getNationalId());
        consumerCreditInformationRecord.setNationality(crbAAConsumer.getNationality());
        consumerCreditInformationRecord.setNature(crbAAConsumer.getNature());
        consumerCreditInformationRecord.setNoOfDependants(crbAAConsumer.getNumberofDependants());
        consumerCreditInformationRecord.setNumberOfJointLoanParticipants(crbAAConsumer.getNumberofLoanJoinParticipants());
        consumerCreditInformationRecord.setOccupation(crbAAConsumer.getOccupation());
        consumerCreditInformationRecord.setOldAccountNumber(crbAAConsumer.getOldAccountNumber());
        consumerCreditInformationRecord.setOpeningBalance(crbAAConsumer.getOpeningBalance());
        consumerCreditInformationRecord.setPassportNumber(crbAAConsumer.getPassportNumber());
        consumerCreditInformationRecord.setPhysicalAddressCell(crbAAConsumer.getPhysicalAddressCell());
        consumerCreditInformationRecord.setPhysicalAddressDistrict(crbAAConsumer.getPhysicalAddressProvince());
        consumerCreditInformationRecord.setPhysicalAddressLine1(crbAAConsumer.getPhysicalAddressLine1());
        consumerCreditInformationRecord.setPhysicalAddressLine2(crbAAConsumer.getPhysicalAddressLine2());
        consumerCreditInformationRecord.setPhysicalAddressPlotNumber(crbAAConsumer.getPhysicalAddressPlotNumber());
        consumerCreditInformationRecord.setPhysicalAddressPostalCode(crbAAConsumer.getPhysicalAddressPostalCode());
        consumerCreditInformationRecord.setPhysicalAddressProvince(crbAAConsumer.getPhysicalAddressProvince());
        consumerCreditInformationRecord.setPhysicalAddressSector(crbAAConsumer.getPhysicalAddressSector());
        consumerCreditInformationRecord.setPlaceOfBirth(crbAAConsumer.getPlaceofBirth());
        consumerCreditInformationRecord.setPostalAddressNumber(crbAAConsumer.getPostalAddressNumber());
        consumerCreditInformationRecord.setPostalCode(crbAAConsumer.getPostalCode());
        consumerCreditInformationRecord.setResidenceType(crbAAConsumer.getResidenceType());
        consumerCreditInformationRecord.setSalutation(crbAAConsumer.getSalutation());
        consumerCreditInformationRecord.setScheduledPaymentAmount(crbAAConsumer.getScheduledPaymentAmount());
        consumerCreditInformationRecord.setSectorOfActivity(crbAAConsumer.getSectorOfActivity());
        consumerCreditInformationRecord.setSocialSecurityNumber(crbAAConsumer.getSocialSecurityNumber());
        consumerCreditInformationRecord.setSurName(crbAAConsumer.getSurName());
        consumerCreditInformationRecord.setTaxNumber(crbAAConsumer.getTaxNumber());
        consumerCreditInformationRecord.setTermsDuration(crbAAConsumer.getTermsDuration());
        consumerCreditInformationRecord.setWorkTelephone(crbAAConsumer.getWorkTelephone());

        consumerRequestPayload.setConsumerCreditInformationRecord(consumerCreditInformationRecord);
        consumerRequestPayload.setRecordType(recordType);

        return consumerRequestPayload;
    }
}
