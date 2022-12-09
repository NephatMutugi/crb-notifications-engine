package com.bpr.main.service.impl;

import com.bpr.main.client.HttpCrbClient;
import com.bpr.main.service.CrbNotificationsService;
import com.bpr.main.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @ Author NMuchiri
 **/
@Service
@Slf4j
public class CrbNotificationsServiceImpl implements CrbNotificationsService {

    /*---------------------- GLOBAL VARIABLES ----------------------*/
    private static final String CRB_LOG_LINE = "===========================================================================================";
    private static final String RESPONSE_CODE = "RESPONSE_CODE";
    private static final String RESPONSE_BODY = "RESPONSE_BODY";
    private static final String REQUEST_BODY = "REQUEST_BODY";
    private String filePath = "D:\\KCB\\bpr\\CrbNotificationsReactive\\src\\main\\resources\\files\\CRB.AA.DATA.EXTRACT.CORPORATE.json";

    private final Utils utils;
    private final HttpCrbClient httpClient;

    public CrbNotificationsServiceImpl(Utils utils, HttpCrbClient httpClient) {
        this.utils = utils;
        this.httpClient = httpClient;
    }

    @Scheduled(fixedRateString = "${scheduler.frequency}", initialDelay = 1000)
    @Override
    public void sendNotificationsJob() {
        log.info("\n\n");
        log.info(CRB_LOG_LINE);
        log.info(CRB_LOG_LINE);
        log.info("================================ CRB NOTIFICATIONS ENGINE =================================");
        log.info(CRB_LOG_LINE);
        log.info("{}\n\n",CRB_LOG_LINE);

        List<String > requests = readRequestsFromFile();


        if (!requests.isEmpty()){
            // We have some requests
            List<HashMap<String, String>> responseMapList =httpClient.dumpRequestsToCrb(requests, "CI", "");

            for (HashMap<String , String> responseMap : responseMapList){
                String responseCode = responseMap.get(RESPONSE_CODE);
                String responseBody = responseMap.get(RESPONSE_BODY);
                String requestBody = responseMap.get(REQUEST_BODY);




            }

        } else {
            log.info("!!! NO REQUESTS FOUND !!!");
        }


    }


    private List<String> readRequestsFromFile(){
        List<String> requestList = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String requestLine;
            while ((requestLine = reader.readLine()) != null){
                if (Boolean.TRUE.equals(utils.validateJson(requestLine))){
                    requestList.add(requestLine);
                    log.info("-----------ADDING REQUESTS: {}-------------", requestList.size());
                } else {
                    log.info("-------------- INVALID JSON -----------------");
                }
            }

        } catch (IOException e) {
            log.error("IO Exception : {}", e.getMessage());
        }


        return requestList;
    }


}
