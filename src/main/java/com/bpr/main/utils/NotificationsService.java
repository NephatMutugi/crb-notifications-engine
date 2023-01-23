package com.bpr.main.utils;

/**
 * @ Author NMuchiri
 **/

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class NotificationsService {
    public static void main(String[] args) {
        String host = "your_host";
        int port = 22;
        String user = "your_username";
        String password = "your_password";
        String sourceFilePath = "/home/ken20549/T24UPLOADS/CRB_TEST";
        String crbServerUrl = "http://172.17.152.67:9545/duv2/data/rw/update/consumercredit";

        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;

            BufferedReader br = new BufferedReader(new InputStreamReader(sftpChannel.get(sourceFilePath)));
            List<String> requests = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                requests.add(line);
            }
            br.close();

            int requestsSize = requests.size();
            int batchSize = 10;
            int start = 0;
            int end = 0;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            RestTemplate restTemplate = new RestTemplate();
            List<Map<String, Object>> responses = new ArrayList<>();
            while (start < requestsSize) {
                end = Math.min(start + batchSize, requestsSize);
                List<String> batch = requests.subList(start, end);
                List<HttpEntity<String>> entities = new ArrayList<>();
                for (String request : batch) {
                    entities.add(new HttpEntity<>(request, headers));
                }
                List<ResponseEntity<String>> batchResponses = Collections.singletonList(restTemplate.postForEntity(crbServerUrl, entities, String.class));
                for (ResponseEntity<String> response : batchResponses) {
                    Map<String, Object> responseMap = new HashMap<>();
                    responseMap.put("RESPONSE MESSAGE", response.getBody());
                    responseMap.put("RESPONSE_CODE", response.getStatusCode().value());
                    responses.add(responseMap);
                }
                start = end;
            }
            sftpChannel.exit();
            session.disconnect();
            System.out.println("File sent successfully!");
            System.out.println("Responses: " + responses);
        } catch (Exception e) {
            System.out.println("File sending failed: " + e.getMessage());
        }

    }
}
