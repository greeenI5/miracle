package com.green.miracle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class MiracleApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiracleApplication.class, args);
	}

}
