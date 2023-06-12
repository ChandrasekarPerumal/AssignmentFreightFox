package com.example.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pincode-api")
public class PincodeDistanceController {

	
	
	@GetMapping("/distance/{fromPincode}/{toPincode}")
	public void findDistance(@PathVariable int fromPincode, @PathVariable int toPincode) {
		
		if(true) {
			
		}
		
		
		
	}
	
	
}
