package com.bpr.main.configs;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * @ Author NMuchiri
 **/
@Component
public class RestTemplateConfig {
    /*----------------------------------GLOBAL VARIABLES--------------------------*/
    @Value("${sftp.server.http-connection-request-timeout}")
    private int connectionRequestTimeout;

    @Value("${sftp.server.http-connection-timeout}")
    private int connectionTimeout;

    @Value("${sftp.server.http-connection-read-timeout}")
    private int connectionReadTimeout;

    // Bypass ssl for HTTPS

    @Bean(name = "RestTemplateByPassSSL")
    public RestTemplate restTemplateByPassSSL() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
        HostnameVerifier hostnameVerifier = (s, sslSession) -> true;
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectionRequestTimeout(connectionRequestTimeout);
        requestFactory.setConnectTimeout(connectionTimeout);
        requestFactory.setReadTimeout(connectionReadTimeout);
        requestFactory.setHttpClient(httpClient);
        return new RestTemplate(requestFactory);
    }

    // configure a custom rest template

    @Bean(name="RestTemplateClient")
    public RestTemplate customRestTemplate() {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(connectionRequestTimeout);
        httpRequestFactory.setConnectTimeout(connectionTimeout);
        httpRequestFactory.setReadTimeout(connectionReadTimeout);
        return new RestTemplate(httpRequestFactory);
    }
}
