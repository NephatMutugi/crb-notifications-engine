package com.bpr.main.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ Author NMuchiri
 **/

@Getter
@Setter
@ToString
public class CrbNotificationResponse {
    private String message;
    private String responseCode;
    private String callbackId;
    private RecordError[] recordErrors;
}