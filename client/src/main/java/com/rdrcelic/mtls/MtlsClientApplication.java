package com.rdrcelic.mtls;

import com.example.mutualssl.keystore.LoadKeyStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MtlsClientApplication {

	static {
		new LoadKeyStore().LoadKeyStore();
	}

	public static void main(String[] args) {
		SpringApplication.run(MtlsClientApplication.class, args);
	}
}
