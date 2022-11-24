package com.bpr.main.service;

import com.bpr.main.model.ConsumerRequestPayload;
import com.bpr.main.model.CrbAAConsumer;

/**
 * @ Author NMuchiri
 **/
public interface ConsumerMappingService {
    ConsumerRequestPayload mapT24PayloadToCrb(CrbAAConsumer crbAAConsumer, String recordType);

}
