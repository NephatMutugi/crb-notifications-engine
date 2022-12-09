package com.bpr.main.model;

import lombok.Data;

/**
 * @ Author NMuchiri
 **/
@Data
public class RecordError {
    private String accountNumber;
    private String errorMessage;
    private String fieldName;
    private String fieldValue;
    private String errorCode;
}
