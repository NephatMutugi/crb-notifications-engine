package com.bpr.main.model;

import lombok.Data;

/**
 * @ Author NMuchiri
 **/
@Data
public class CorporateRequestPayload {
    private CorporateCreditInformationRecord corporateCreditInformationRecord;
    private String recordType;
}
