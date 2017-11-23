package com.rdrcelic.mtls.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

    @PostConstruct
    public void initSsl(){
        System.setProperty("javax.net.ssl.keyStore", tmpKeystoreFileFromBase64("client-keystore", client_keystore));
        System.setProperty("javax.net.ssl.keyStorePassword", client_pass);
        System.setProperty("javax.net.ssl.trustStore", tmpKeystoreFileFromBase64("client-truststore", client_trust));
        System.setProperty("javax.net.ssl.trustStorePassword", client_pass);
    }

    /**
     * Decode base64 value from Vault and write it to tmp keystore file
     * @param fileName
     * @param base64Str
     * @return
     */
    private String tmpKeystoreFileFromBase64(String fileName, String base64Str) {
        byte[] content = Base64.getDecoder().decode(base64Str);
        String tmpFilePath = fileName;
        try {
            File file = File.createTempFile(fileName, ".jks");
            file.deleteOnExit();
            OutputStream ostream = new FileOutputStream(file);
            ostream.write(content);
            tmpFilePath = file.getAbsolutePath();
        } catch (IOException ex) {
            System.err.println("Error creating tmp keystore file " + fileName);
        }

        return tmpFilePath;
    }
}
