package com.example.Assignment1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableScheduling
@SpringBootApplication
public class Assignment1Application {

	public static void main(String[] args) {
		SpringApplication.run(Assignment1Application.class, args);
		  System.out.println("------------------------------------");
        System.out.println("🚀  ENGINE IS LIVE!");
        System.out.println("------------------------------------");
	}

}
