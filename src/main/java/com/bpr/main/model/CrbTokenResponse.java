package com.bpr.main.model;

import lombok.Data;

/**
 * @ Author NMuchiri
 **/
@Data
public class CrbTokenResponse {
    private String token;
    private String expiresAfter;
    private String status;
}
