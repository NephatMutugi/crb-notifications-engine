package com.bpr.main.service.impl;

import com.bpr.main.client.HttpCrbClient;
import com.bpr.main.model.CrbNotification;
import com.bpr.main.model.CrbNotificationResponse;
import com.bpr.main.repository.CrbNotificationsRepository;
import com.bpr.main.service.CrbNotificationsService;
import com.bpr.main.utils.Utils;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialBlob;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
    private static final String BOARDER_LINE = "===========================================================================================";
    private static final String RESPONSE_CODE = "RESPONSE_CODE";
    private static final String RESPONSE_BODY = "RESPONSE_BODY";
    private static final String REQUEST_BODY = "REQUEST_BODY";

    private final Utils utils;
    private final HttpCrbClient httpClient;
    private final CrbNotificationsRepository crbNotificationsRepository;

    public CrbNotificationsServiceImpl(Utils utils, HttpCrbClient httpClient, CrbNotificationsRepository crbNotificationsRepository) {
        this.utils = utils;
        this.httpClient = httpClient;
        this.crbNotificationsRepository = crbNotificationsRepository;
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
        List<CrbNotification> crbNotificationList = new ArrayList<>();
        long start = System.currentTimeMillis();

        if (!requests.isEmpty()){
            // We have some requests
            List<HashMap<String, String>> responseMapList = httpClient.multiThreadedClient(requests, "CI");

            if (responseMapList != null){
                log.info("NUMBER OF RESPONSES -------------------------{}", responseMapList.size());
                for (HashMap<String , String> responseMap : responseMapList){
                    String responseCode = responseMap.get(RESPONSE_CODE);
                    String responseBody = responseMap.get(RESPONSE_BODY);
                    String requestBody = responseMap.get(REQUEST_BODY);

                    if (responseCode.equals("000")){
                        // REQUEST WAS SUCCESSFULLY RECEIVED BY CRB AND GOT A RESPONSE BACK
                        Gson gson = new Gson();
                        CrbNotificationResponse crbNotificationResponse = gson.fromJson(responseBody, CrbNotificationResponse.class);
                        String notificationsResponseCode = crbNotificationResponse.getResponseCode();

                        if (notificationsResponseCode.equals("200")){
                            log.info(BOARDER_LINE);
                            log.info("-------------------SUCCESS NOTIFICATIONS RESPONSE----------------------");
                            crbNotificationList.add(saveProcessedRequest(responseCode,
                                    "SUCCESS",
                                    requestBody,
                                    responseBody
                            ));


                        } else {
                            log.info(BOARDER_LINE);
                            log.info("-------------------FAILURE NOTIFICATIONS RESPONSE----------------------");
                            crbNotificationList.add(saveProcessedRequest(responseCode,
                                    "FAILED",
                                    requestBody,
                                    responseBody
                            ));

                        }
                    } else {
                        log.error(BOARDER_LINE);
                        log.error("-------------------FAILED IN SENDING REQUEST----------------------");
                        log.error("CODE: {} : ", responseCode);
                        log.error(BOARDER_LINE);
                        log.error("REQUEST SAVED TO FAILURE");
                    }
                }
            } else {
                log.error("---- ERROR WHILE GETTING RESPONSE FROM COMPLETABLE FUTURE -----");
            }

            if (!crbNotificationList.isEmpty()){
                crbNotificationsRepository.saveAll(crbNotificationList);
                log.info("-------------------- SAVED :: {} REQUESTS TO DB", crbNotificationList.size());
            }

        } else {
            log.info("!!! NO REQUESTS FOUND !!!");
        }


        long stop = System.currentTimeMillis();

        log.info(BOARDER_LINE);
        log.info("----------- TIME TAKEN :{} SECONDS", (stop-start)/1000);
    }


    private List<String> readRequestsFromFile(){
        List<String> requestList = new ArrayList<>();

        String filePath = "D:\\KCB\\bpr\\CrbNotificationsReactive\\src\\main\\resources\\files\\CRB.AA.DATA.EXTRACT.CORPORATE.json";
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


    private CrbNotification saveProcessedRequest(String responseCode, String statusMessage, String requestBody, String responseBody){

        log.info(BOARDER_LINE);
        log.info("-------------------SUCCESS NOTIFICATIONS RESPONSE----------------------");
        String request = (Utils.objectToJson(requestBody)).replace("\\","");
        String response = (Utils.objectToJson(responseBody)).replace("\\","");
        byte[] byteDataRequest = request.getBytes();
        byte[] byteDataResponse = response.getBytes();
        Blob requestBlob;
        Blob responseBlob;

        requestBlob = null;
        responseBlob = null;
        try {
            requestBlob = new SerialBlob(byteDataRequest);
            responseBlob = new SerialBlob(byteDataResponse);
        } catch (SQLException e) {
            log.error("SQLException :: {}", e.getMessage());
        }
        return new CrbNotification(requestBlob,
                responseBlob,
                new Date(),
                "CI",
                responseCode,
                statusMessage,
                "accountNumber",
                "accountName",
                0);
    }

}
