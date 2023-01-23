package com.bpr.main.client;

import com.bpr.main.configs.CustomProperties;
import com.bpr.main.configs.RestTemplateConfig;
import com.bpr.main.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @ Author NMuchiri
 **/
@Service
@Slf4j
public class HttpCrbClient {
    /*------------------------------- GLOBAL VARIABLES ------------------------------*/
    private static final String RESPONSE_CODE = "RESPONSE_CODE";
    private static final String RESPONSE_BODY = "RESPONSE_BODY";
    private static final String REQUEST_BODY = "REQUEST_BODY";
    private static final String BOARDER = "=======================================================================================";
    @Value("${crb.consumer-record-type}")
    private String consumerRecordType;
    @Value("${crb.corporate-record-type}")
    private String corporateRecordType;

    /*----------------------------- AUTOWIRED DEPENDENCIES --------------------------*/
    private final RestTemplateConfig restTemplateConfig;
    private final CustomProperties customProperties;
    private final TokenGenClient tokenGenClient;
    private final Utils utils;

    public HttpCrbClient(RestTemplateConfig restTemplateConfig, CustomProperties customProperties, TokenGenClient tokenGenClient, Utils utils) {
        this.restTemplateConfig = restTemplateConfig;
        this.customProperties = customProperties;
        this.tokenGenClient = tokenGenClient;
        this.utils = utils;
    }

    public List<HashMap<String, String>> dumpRequestsToCrb(List<String> requestList, String recordType) {
        long startTime = System.currentTimeMillis();
        log.info(BOARDER);
        log.info("START SENDING REQUESTS TO CRB :: 50 THREADS");
        log.info("START TIME :: {}", startTime);
        log.info(BOARDER);

        List<HashMap<String, String>> mapList = new ArrayList<>();
        HashMap<String, String> responseMap = new HashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(100);

        Map<String, String> tokenMap = tokenGenClient.token();

        log.info("TOKEN: {}", tokenMap.get(RESPONSE_BODY));
        String tokenResponseCode = tokenMap.get(RESPONSE_CODE);

        log.info("TOKEN RESPONSE **** :\n{}", tokenResponseCode);
        if (tokenResponseCode.equals("000")){
            log.info("--------Token Stored Successfully----------: {}", tokenResponseCode);

            String httpMessage;
            // Set appropriate url depending on the record type
            String crbUrl = null;

            if (recordType.equals(corporateRecordType)) {
                crbUrl = customProperties.getBaseUrl() + customProperties.getNotificationsBaseResourceCorporate();
            } else if (recordType.equals(consumerRecordType)) {
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

            if (crbUrl != null) {
                if (restTemplate != null) {
                    for (String request : requestList) {
                        String finalCrbUrl = crbUrl;
                        RestTemplate finalRestTemplate = restTemplate;
                        executor.submit(() -> {
                            try {

                                log.info(BOARDER);
                                log.info("============================= REQUEST NUMBER :: {} =========================", requestList.indexOf(request));
                                HttpEntity<String> requestEntity = new HttpEntity<>(request, httpHeaders);
                                ResponseEntity<String> responseEntity = finalRestTemplate.exchange(
                                        finalCrbUrl,
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
                                responseMap.put(REQUEST_BODY, request);
                                mapList.add(responseMap);
                            } catch (Exception e) {
                                responseMap.put(RESPONSE_CODE, "500");
                                responseMap.put(RESPONSE_BODY, e.getMessage());
                                responseMap.put(REQUEST_BODY, request);
                                mapList.add(responseMap);
                                log.error("EXCEPTION WHILE SENDING REQUESTS TO CRB :: {}", e.getMessage());
                            }
                        });
                    }


                } else {
                    log.error("Error while creating rest template instance");
                    httpMessage = "Error while creating rest template instance";

                    responseMap.put(RESPONSE_CODE, "500");
                    responseMap.put(RESPONSE_BODY, httpMessage);
                    mapList.add(responseMap);
                }
            } else {
                log.error("RECORD TYPE :: {} is invalid", recordType);
                httpMessage = "Invalid record type provided.";

                responseMap.put(RESPONSE_CODE, "500");
                responseMap.put(RESPONSE_BODY, httpMessage);
                mapList.add(responseMap);

            }
        } else {
            log.error("ERROR WHEN GENERATING TOKEN");
            log.error(tokenMap.get(RESPONSE_BODY));
        }

//        executor.shutdown();
        log.info("SHUT DOWN THREADS");
        long endTime = System.currentTimeMillis();
        log.info(BOARDER);
        log.info("FINISH SENDING REQUESTS");
        log.info("END TIME :: {}", endTime);
        log.info("TIME TAKEN TO SEND {} REQUESTS : {}", requestList.size(), (endTime-startTime)/1000);
        log.info(BOARDER);
        executor.shutdown();

        return mapList;

    }

    public List<HashMap<String, String>> multiThreadedClient(List<String > requestList, String recordType){

        Map<String, String> tokenMap = tokenGenClient.token();
        String tokenResponseCode = tokenMap.get(RESPONSE_CODE);

        log.info("TOKEN RESPONSE **** :\n{}", tokenResponseCode);

        // Set appropriate CRB URL based on record type.
        String crbUrl = customProperties.getBaseUrl() + customProperties.getNotificationsBaseResource();
        if (recordType.equals(corporateRecordType)){
            crbUrl = customProperties.getBaseUrl() + customProperties.getNotificationsBaseResourceCorporate();
        }
        log.info("SENDING REQUEST TO URL --------------------------------- : {}", crbUrl);

        String httpMessage;

        // Prepare HTTP Headers to send with the requests
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(tokenMap.get(RESPONSE_BODY));

        // Set up Executor to send multithreaded requests
        ExecutorService executor = Executors.newFixedThreadPool(50);
        HashMap<String, String> responseMap = new HashMap<>();
        List<HashMap<String , String>> responseMapList = new ArrayList<>();
        Map<Integer, Future<ResponseEntity<String>>> futureMap = new HashMap<>();

        if (tokenResponseCode.equals("000")){
            try {
                RestTemplate restTemplate = restTemplateConfig.restTemplateByPassSSL();

                for (String request: requestList){
                    String finalCrbUrl = crbUrl;
                    HttpEntity<Object> requestEntity = new HttpEntity<>(request, httpHeaders);
                    Future<ResponseEntity<String >> futureResponse =
                            executor.submit(() -> restTemplate.exchange(
                                    finalCrbUrl, HttpMethod.POST, requestEntity, String.class
                            ));
                    futureMap.put(requestList.indexOf(request), futureResponse);

                    // Add request to map for logging
                    responseMap.put(REQUEST_BODY, request);

                }
                while (futureMap.size() > 0){
                    Map<Integer, Future<ResponseEntity<String>>> completedMap = new HashMap<>();
//                    for (int i : futureMap.keySet()){
                    for (Map.Entry<Integer, Future<ResponseEntity<String>>> entry: futureMap.entrySet()){
                        int key = entry.getKey();
                        if (futureMap.get(key).isDone()){
                            completedMap.put(key, futureMap.get(key));
                        }
                    }
//int i : completedMap.keySet()
                    for (Map.Entry<Integer, Future<ResponseEntity<String>>> entry : completedMap.entrySet()){

                        int key = entry.getKey();
                        ResponseEntity<String> responseEntity = completedMap.get(key).get();
                        HttpStatus statusCode = responseEntity.getStatusCode();
                        if (statusCode == HttpStatus.OK){
                            responseMap.put(RESPONSE_CODE, "000");
                        } else {
                            responseMap.put(RESPONSE_CODE, "500");
                        }
                        responseMap.put(RESPONSE_BODY, responseEntity.getBody());
                        responseMapList.add(responseMap);

                        futureMap.remove(key);
                    }
                }

            } catch (KeyStoreException | NoSuchAlgorithmException | KeyManagementException e) {
                httpMessage = e.getMessage();
                responseMap.put(RESPONSE_CODE, "500");
                responseMap.put(RESPONSE_BODY, httpMessage);
                responseMapList.add(responseMap);

                log.error("Error while sending request");
                log.error(httpMessage);
            } catch (ExecutionException | InterruptedException e) {
                log.error("THREAD INTERRUPTION : {}", e.getMessage());
                httpMessage = e.getMessage();
                responseMap.put(RESPONSE_CODE, "500");
                responseMap.put(RESPONSE_BODY, httpMessage);
                responseMapList.add(responseMap);

                Thread.currentThread().interrupt();
            }
        } else {
            log.error("ERROR WHEN GENERATING TOKEN");
            log.error(tokenMap.get(RESPONSE_BODY));
        }



        return responseMapList;
    }

}
