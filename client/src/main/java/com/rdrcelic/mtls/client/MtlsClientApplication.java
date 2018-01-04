package com.rdrcelic.mtls.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class MtlsClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(MtlsClientApplication.class, args);
	}
}
