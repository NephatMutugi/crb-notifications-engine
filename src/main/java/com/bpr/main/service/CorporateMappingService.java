package com.bpr.main.service;

import com.bpr.main.model.CorporateRequestPayload;
import com.bpr.main.model.CrbAACorporate;

/**
 * @ Author NMuchiri
 **/
public interface CorporateMappingService {

    CorporateRequestPayload mapT24PayloadToCrb(CrbAACorporate crbAACorporate, String recordType);
}
