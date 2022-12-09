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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    @Value("${crb.consumer-record-type}")
    private String consumerRecordType;
    @Value("${crb.corporate-record-type}")
    private String corporateRecordType;

    /*----------------------------- AUTOWIRED DEPENDENCIES --------------------------*/
    private final RestTemplateConfig restTemplateConfig;
    private final CustomProperties customProperties;

    public HttpCrbClient(RestTemplateConfig restTemplateConfig, CustomProperties customProperties) {
        this.restTemplateConfig = restTemplateConfig;
        this.customProperties = customProperties;
    }

    public List<HashMap<String, String>> dumpRequestsToCrb(List<String> requestList, String recordType, String bearerToken) {

        String httpMessage;

        // Set appropriate url depending on the record type
        String crbUrl = null;

        if (recordType.equals(corporateRecordType)) {
            crbUrl = customProperties.getBaseUrl() + customProperties.getNotificationsBaseResourceCorporate();
        } else if (recordType.equals(consumerRecordType)) {
            crbUrl = customProperties.getBaseUrl() + customProperties.getNotificationsBaseResource();
        }

        List<HashMap<String, String>> mapList = new ArrayList<>();
        HashMap<String, String> responseMap = new HashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(10);

        // SET HTTP HEADERS
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(bearerToken);

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

                executor.shutdown();


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

        return mapList;
    }
}
