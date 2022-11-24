package com.bpr.main.client;

import com.bpr.main.configs.CustomProperties;
import com.bpr.main.configs.RestTemplateConfig;
import com.bpr.main.model.CrbTokenRequest;
import com.bpr.main.model.CrbTokenResponse;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @ Author NMuchiri
 **/
@Service
@Slf4j
public class TokenGenClient {
    /*------------------------------- GLOBAL VARIABLES ------------------------------*/
    private static final String RESPONSE_CODE = "RESPONSE_CODE";
    private static final String RESPONSE_BODY = "RESPONSE_BODY";
    private static final String TOKEN_LOG = "-------------------- TOKEN GENERATOR : ";

    /*----------------------------- AUTOWIRED DEPENDENCIES --------------------------*/
    private final CustomProperties customProperties;
    private final RestTemplateConfig restTemplateConfig;

    public TokenGenClient(CustomProperties customProperties, RestTemplateConfig restTemplateConfig) {
        this.customProperties = customProperties;
        this.restTemplateConfig = restTemplateConfig;
    }

    private Map<String , String > tokenGenerator(){
        CrbTokenRequest tokenRequest = new CrbTokenRequest();
        tokenRequest.setUsername(customProperties.getUsername());
        tokenRequest.setPassword(customProperties.getPassword());
        tokenRequest.setInfinityCode(customProperties.getInfinityCode());

        String httpMessage;
        String httpMessageCode = "500";
        HashMap<String, String > responsePayload = new HashMap<>();
        String tokenUrl = customProperties.getBaseUrl() + customProperties.getTokenBaseResource();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CrbTokenRequest> requestEntity = new HttpEntity<>(tokenRequest, httpHeaders);
        try {
            log.info("------------------------ START TOKEN REQUEST ------------------");
            log.info("------------- TOKEN URL :: {}", tokenUrl);
            RestTemplate restTemplate = restTemplateConfig.restTemplateByPassSSL();
            ResponseEntity<String > responseEntity = restTemplate.postForEntity(
                    tokenUrl,
                    requestEntity,
                    String.class
            );

            HttpStatus statusCode = responseEntity.getStatusCode();
            httpMessage = responseEntity.getBody();
            if (statusCode == HttpStatus.OK){
                log.info("{}SUCCESS", TOKEN_LOG);
                httpMessageCode = "000";
            } else {
                log.info("{}FAILED", TOKEN_LOG);
            }
            responsePayload.put(RESPONSE_CODE, httpMessageCode);
            responsePayload.put(RESPONSE_BODY, httpMessage);

        } catch (KeyStoreException | NoSuchAlgorithmException | KeyManagementException e) {
            log.error("------------ AN ERROR OCCURRED WHILE TRYING TO GENERATE TOKEN ------------");
            log.error(e.getMessage());
            httpMessage = e.getMessage();
            responsePayload.put(RESPONSE_CODE, httpMessageCode);
            responsePayload.put(RESPONSE_BODY, httpMessage);
        }
        log.info("---------------TOKEN GENERATION RESPONSE ------------------");
        log.info(responsePayload.get(RESPONSE_BODY));

        return responsePayload;
    }

    /*------------------------------ GET CRB TOKEN FROM TOKEN RESPONSE ------------------------*/
    public Map<String , String > token(){
        Map<String , String> bearerTokenGen = tokenGenerator();
        Map<String, String> tokenMap = new HashMap<>();
        if (bearerTokenGen.get(RESPONSE_CODE).equals("000")){
            Gson gson = new Gson();
            log.info("TOKEN RESPONSE :: {}", bearerTokenGen.get(RESPONSE_BODY));
            CrbTokenResponse crbTokenResponse = gson.fromJson(bearerTokenGen.get(RESPONSE_BODY), CrbTokenResponse.class);
            log.info("--------Token GENERATED----------: {}", crbTokenResponse.getStatus());
            tokenMap.put(RESPONSE_CODE, "000");
            tokenMap.put(RESPONSE_BODY, crbTokenResponse.getToken());
        } else {
            tokenMap.put(RESPONSE_CODE, "500");
            tokenMap.put(RESPONSE_BODY, bearerTokenGen.get(RESPONSE_BODY));
        }
        return tokenMap;
    }
}
