package com.rdrcelic.mtls.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${client_trust}")
    private String client_trust;
    @Value("${client}")
    private String client_keystore;
    @Value("${client_trust_pass}")
    private String client_trust_pass;
    @Value("${client_pass}")
    private String client_pass;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().anonymous();
    }

    @PostConstruct
    public void initSsl(){
        System.setProperty("javax.net.ssl.keyStore", Thread.currentThread().getContextClassLoader().getResource("client-keystore.jks").getPath());
        System.setProperty("javax.net.ssl.keyStorePassword", "changeit");
        System.setProperty("javax.net.ssl.trustStore", Thread.currentThread().getContextClassLoader().getResource("client-truststore.jks").getPath());
        System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
//        try {
//            System.setProperty("javax.net.ssl.keyStore", stringFromBase64(client_keystore));
//            System.setProperty("javax.net.ssl.keyStorePassword", client_pass);
//            System.setProperty("javax.net.ssl.trustStore", stringFromBase64(client_trust));
//            System.setProperty("javax.net.ssl.trustStorePassword", client_pass);
//        } catch (IOException ex) {
//
//        }


		/*
		javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
			(hostname,sslSession) -> {
				if (hostname.equals("localhost")) {
					return true;
				}
				return false;
			});*/
    }

    private String stringFromBase64(String base64Str) throws IOException {
        byte[] content = Base64.getDecoder().decode(base64Str);
        String value = new String(content);
        return value;
    }
}
