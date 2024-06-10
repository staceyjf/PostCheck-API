package com.auspost.postcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpringBootApplication
@PropertySource("file:${user.dir}/.env")
public class PostcodeApplication {

	private static final Logger logger = LogManager.getLogger(PostcodeApplication.class);

	public static void main(String[] args) {
		System.setProperty("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager");
		SpringApplication.run(PostcodeApplication.class, args);
		logger.info("PostCheck API has successfully loaded");
	}

}
