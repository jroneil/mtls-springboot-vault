package com.rdrcelic.mtls.client.config;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.util.StringUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Base64;

/**
 * Configures client security and SSL environment
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    // base64 encoded client truststore from vault
    @Value("${client_trust}")
    private String client_trust;
    // base64 encoded client keystore from vault
    @Value("${client}")
    private String client_keystore;
    @Value("${client_trust_pass}")
    private String client_trust_pass;
    @Value("${client_pass}")
    private String client_pass;

    /**
     * The goal is to have client to demonstrate mTLS between applications, for this purpose no need to protect this client
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().anonymous();
    }

    @Bean
    public RestTemplate restTemplate() throws Exception {
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
        return restTemplate;
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() throws Exception {
        return new HttpComponentsClientHttpRequestFactory(customSslHttpClient());
    }

    private HttpClient customSslHttpClient() {

        HttpClient httpClient = HttpClients.custom().build();
        try {
            // get keyStore
            final KeyStore keyStore = KeyStore.getInstance("JKS");
            byte[] keyStoreContent = Base64.getDecoder().decode(client_keystore);
            ByteArrayInputStream keyStoreInputStream = new ByteArrayInputStream(keyStoreContent);
            keyStore.load(keyStoreInputStream, client_pass.toCharArray());
            final KeyManagerFactory keyStoreManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory
                    .getDefaultAlgorithm());
            keyStoreManagerFactory.init(keyStore, client_pass.toCharArray());

            // get trustStore
            final KeyStore trustStore = KeyStore.getInstance("JKS");
            byte[] trustStoreContent = Base64.getDecoder().decode(client_trust);
            ByteArrayInputStream trustStoreInputStream = new ByteArrayInputStream(trustStoreContent);
            trustStore.load(trustStoreInputStream, client_pass.toCharArray());
            final KeyManagerFactory trustStoreManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory
                    .getDefaultAlgorithm());
            trustStoreManagerFactory.init(trustStore, client_pass.toCharArray());
            final TrustManagerFactory trustStoreTrustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory
                    .getDefaultAlgorithm());
            trustStoreTrustManagerFactory.init(trustStore);

            /*
             * Creates a socket factory for HttpsURLConnection using JKS
             * contents
             */
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyStoreManagerFactory.getKeyManagers(), trustStoreTrustManagerFactory.getTrustManagers(), new java.security.SecureRandom());

            final SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext);
            httpClient = HttpClients.custom()
                    .setSSLSocketFactory(sslSocketFactory).build();

        } catch (final GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }

        return httpClient;
    }
}
