package com.bpr.main.model;

import lombok.Data;

/**
 * @ Author NMuchiri
 **/
@Data
public class ConsumerRequestPayload {
    private ConsumerCreditInformationRecord consumerCreditInformationRecord;
    private String recordType;
}
