package com.rdrcelic.mtls.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MtlsServerApplication {

	// want to see SSL handshake logs
	static {
		System.setProperty("javax.net.debug", "ssl");
	}

	public static void main(String[] args) {
		SpringApplication.run(MtlsServerApplication.class, args);
	}
}
