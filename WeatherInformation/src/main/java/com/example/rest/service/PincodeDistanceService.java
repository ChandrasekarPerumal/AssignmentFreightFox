package com.example.rest.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



@Service
public class PincodeDistanceService {

	@Value("${google.maps.api.key}")
	private String googleApiKey; 
	
	
	public void calculateDistanceDuration() {
		
	}
	
}
