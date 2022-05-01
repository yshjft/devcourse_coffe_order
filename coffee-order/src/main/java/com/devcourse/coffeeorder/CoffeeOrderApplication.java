package com.devcourse.coffeeorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CoffeeOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoffeeOrderApplication.class, args);
	}

}
