package com.academy.training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class TrainingApplication{

	protected static final Logger logger = LoggerFactory
			.getLogger(TrainingApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(TrainingApplication.class, args);
	}

}
