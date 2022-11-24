package com.bpr.main.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @ Author NMuchiri
 **/
@Service
@Slf4j
public class Utils {
    /*--------------------- VALIDATE IF JSON IS VALID -------------------------*/
    public Boolean validateJson(String request) {
        try {
            JsonParser.parseString(request);
        } catch (JsonSyntaxException e) {
            log.error("------------ INVALID JSON ------------- {}", e.getMessage());
            return false;
        }
        return true;
    }

    /*--------------------- Convert object to json -------------------------*/
    public static String objectToJson(Object entity) {
        try {
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(entity);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            return ex.getMessage();
        }
    }

    /*--------------------- Generate current time stamp -------------------------*/
    public String generateTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+2"));
        String dateString = dateFormat.format(new Date());
        dateString = dateString.replaceAll("[^a-zA-Z0-9]", "");
        return dateString;
    }

    /*--------------------- Map JSON String to Object -------------------------*/
    public Object jsonStringToPojo(String jsonString, Object inputClass) {
        Object object = new Object();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            objectMapper.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
            object = objectMapper.readValue(jsonString, inputClass.getClass());

        } catch (IOException e) {
            log.error("-----------!!! ERROR IN MAPPING JSON TO CLASS !!!---------------- {}", e.getMessage());

        }
        return object;
    }

}
