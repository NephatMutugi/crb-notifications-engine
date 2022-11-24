package com.bpr.main.model;

import lombok.Data;

/**
 * @ Author NMuchiri
 **/
@Data
public class CrbTokenRequest {
    private String username;
    private String password;
    private String infinityCode;
}
