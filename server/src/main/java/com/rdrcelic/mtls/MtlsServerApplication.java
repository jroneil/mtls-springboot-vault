package com.rdrcelic.mtls;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MtlsServerApplication {

	static {
		System.setProperty("javax.net.debug", "ssl");
	}

	public static void main(String[] args) {
		SpringApplication.run(MtlsServerApplication.class, args);
	}
}
