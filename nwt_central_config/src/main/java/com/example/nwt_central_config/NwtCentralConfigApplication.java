package com.example.nwt_central_config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class NwtCentralConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(NwtCentralConfigApplication.class, args);
	}

}