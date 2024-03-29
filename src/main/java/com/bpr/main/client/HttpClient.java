package com.bpr.main.client;

import com.bpr.main.configs.CustomProperties;
import com.bpr.main.configs.RestTemplateConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ Author NMuchiri
 **/
@Service
@Slf4j
public class HttpClient {

    /*------------------------------- GLOBAL VARIABLES ------------------------------*/
    private static final String RESPONSE_CODE = "RESPONSE_CODE";
    private static final String RESPONSE_BODY = "RESPONSE_BODY";
    private static final String REQUEST_BODY = "REQUEST_BODY";
    private static final int NUM_THREADS = 50;
    private static final String BOARDER = "=======================================================================================";
    @Value("${crb.consumer-record-type}")
    private String consumerRecordType;
    @Value("${crb.corporate-record-type}")
    private String corporateRecordType;

    /*----------------------------- AUTOWIRED DEPENDENCIES --------------------------*/
    private final RestTemplateConfig restTemplateConfig;
    private final CustomProperties customProperties;
    private final TokenGenClient tokenGenClient;

    public HttpClient(RestTemplateConfig restTemplateConfig,
                      CustomProperties customProperties,
                      TokenGenClient tokenGenClient) {
        this.restTemplateConfig = restTemplateConfig;
        this.customProperties = customProperties;
        this.tokenGenClient = tokenGenClient;
    }

//    @Async("myExecutor")
    public List<HashMap<String, String>> dumpRequestsToCrb(List<String> requestList, String recordType) {
        long startTime = System.currentTimeMillis();
        log.info(BOARDER);
        log.info("START SENDING REQUESTS TO CRB :: 50 THREADS");
        log.info("START TIME :: {}", startTime);
        log.info(BOARDER);

        List<HashMap<String, String>> mapList = new ArrayList<>();
        HashMap<String, String> responseMap = new HashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        Map<String, String> tokenMap = tokenGenClient.token();
        String tokenResponseCode = tokenMap.get(RESPONSE_CODE);

        log.info("TOKEN RESPONSE **** :\n{}", tokenResponseCode);
        if (tokenResponseCode.equals("000")) {
            log.info("Token Generated Successfully");

            String httpMessage;
            String crbUrl;

            if (recordType.equals(corporateRecordType)) {
                crbUrl = customProperties.getBaseUrl() + customProperties.getNotificationsBaseResourceCorporate();
            } else {
                crbUrl = customProperties.getBaseUrl() + customProperties.getNotificationsBaseResource();
            }

            // SET HTTP HEADERS
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON));
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setBearerAuth(tokenMap.get(RESPONSE_BODY));

            // Initialize RestTemplate instance to send the requests
            RestTemplate restTemplate = null;
            try {
                restTemplate = restTemplateConfig.restTemplateByPassSSL();
            } catch (KeyStoreException | NoSuchAlgorithmException | KeyManagementException e) {
                log.error("REST TEMPLATE ERROR : {}", e.getMessage());
            }

            if (restTemplate != null) {
                    RestTemplate finalRestTemplate = restTemplate;
                    executor.execute(() ->{

                        for (int i=0; i<NUM_THREADS; i++ ){
                            log.info("THREAD: {}", Thread.currentThread().getName());

                            try {
                                log.info(BOARDER);
                                log.info("REQUEST NUMBER :: {} ", i+1);
                                HttpEntity<String> requestEntity = new HttpEntity<>(requestList.get(i), httpHeaders);
                                ResponseEntity<String> responseEntity = finalRestTemplate.exchange(
                                        crbUrl,
                                        HttpMethod.POST,
                                        requestEntity,
                                        String.class
                                );

                                HttpStatus statusCode = responseEntity.getStatusCode();
                                if (statusCode == HttpStatus.OK) {
                                    responseMap.put(RESPONSE_CODE, "000");
                                } else {
                                    responseMap.put(RESPONSE_CODE, "500");
                                }
                                responseMap.put(RESPONSE_BODY, responseEntity.getBody());
                                responseMap.put(REQUEST_BODY, requestList.get(i));
                                mapList.add(responseMap);
                            } catch (Exception e) {
                                responseMap.put(RESPONSE_CODE, "500");
                                responseMap.put(RESPONSE_BODY, e.getMessage());
                                responseMap.put(REQUEST_BODY, requestList.get(i));
                                mapList.add(responseMap);
                                log.error("EXCEPTION WHILE SENDING REQUEST TO CRB :: {}", e.getMessage());
                            }
                        }


                    });


            } else {
                log.error("Error while creating rest template instance");
                httpMessage = "Error while creating rest template instance";
                responseMap.put(RESPONSE_CODE, "500");
                responseMap.put(RESPONSE_BODY, httpMessage);
                mapList.add(responseMap);
            }
        } else {
            log.error("ERROR WHEN GENERATING TOKEN");
            log.error(tokenMap.get(RESPONSE_BODY));
        }

        executor.shutdownNow();

        log.info("SHUT DOWN THREADS");
        long endTime = System.currentTimeMillis();
        log.info(BOARDER);
        log.info("FINISH SENDING REQUESTS");
        log.info("END TIME :: {}", endTime);
        log.info("TIME TAKEN TO SEND {} REQUESTS : {}", requestList.size(), (endTime - startTime) / 1000);
        log.info(BOARDER);

        return mapList;
    }
}
