package com.example.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WeatherInformation {

	/*
	 * Completed 2 Assignments
	 * 1. Weather information for Specified Pincode
	 * 2. Download and Upload files in S3. 
	 * 
	 * */
	
	public static void main(String[] args) {
		try {
			SpringApplication.run(WeatherInformation.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
