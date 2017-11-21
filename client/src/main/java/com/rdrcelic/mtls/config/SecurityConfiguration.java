package com.rdrcelic.mtls.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.annotation.PostConstruct;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
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
		/*
		javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
			(hostname,sslSession) -> {
				if (hostname.equals("localhost")) {
					return true;
				}
				return false;
			});*/
    }
}
