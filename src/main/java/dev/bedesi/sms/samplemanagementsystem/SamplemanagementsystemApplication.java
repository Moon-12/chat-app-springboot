package dev.bedesi.sms.samplemanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@SpringBootApplication
public class SamplemanagementsystemApplication {

	public static void main(String[] args) {

		// Get system properties
		Properties systemProperties = System.getProperties();

		// Print all system properties
		System.out.println("All system properties:");
		for (String key : systemProperties.stringPropertyNames()) {
			String value = systemProperties.getProperty(key);
			System.out.println(key + " = " + value);
		}
		SpringApplication.run(SamplemanagementsystemApplication.class, args);
	}
}
