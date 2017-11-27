package com.hungerbash.restaurants;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RestaurantsBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantsBeApplication.class, args);
	}
}
