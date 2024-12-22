package com.toy.namoneo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class NamoneoApplication {

	public static void main(String[] args) {
		SpringApplication.run(NamoneoApplication.class, args);
	}

}
