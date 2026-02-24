package dev.bedesi.sms.chatmanagementsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@SpringBootApplication
public class ChatManagementSystemApplication {

	private static final Logger logger = LoggerFactory.getLogger(ChatManagementSystemApplication.class);
	public static void main(String[] args) {

		// Get system properties
		Properties systemProperties = System.getProperties();

		// Print all system properties
		logger.info("All system properties:");
		for (String key : systemProperties.stringPropertyNames()) {
			String value = systemProperties.getProperty(key);
			logger.info(key + " = " + value);
		}
		SpringApplication.run(ChatManagementSystemApplication.class, args);
	}
}
