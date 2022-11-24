package com.bpr.main.service.impl;

import com.bpr.main.service.CrbNotificationsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @ Author NMuchiri
 **/
@Service
@Slf4j
public class CrbNotificationsServiceImpl implements CrbNotificationsService {


    @Scheduled(fixedRateString = "${scheduler.frequency}", initialDelay = 1000)
    @Override
    public void sendNotificationsJob() {
        log.info("===========================================================================================");
        log.info("================================ CRB NOTIFICATIONS ENGINE =================================");
        log.info("===========================================================================================");
    }
}
