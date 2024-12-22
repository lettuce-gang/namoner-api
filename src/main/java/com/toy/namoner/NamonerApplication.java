package com.toy.namoner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class NamonerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NamonerApplication.class, args);
	}

}
