package com.vekrest.vekgateway.vekgateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VekgatewayApplication implements CommandLineRunner {
	private static final Logger LOG = LoggerFactory.getLogger(VekgatewayApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(VekgatewayApplication.class, args);
	}

	@Override
	public void run(String... args) {
		LOG.info("VEKREST -> VEKGATEWAY - INICIALIZADO COM SUCESSO!");
	}
}
