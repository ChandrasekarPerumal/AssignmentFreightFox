package com.example.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rest.entity.Weather;
import com.example.rest.service.WeatherService;

@RestController
@RequestMapping("/weather")
public class WeatherController {
	
	
	@Autowired
	private WeatherService weatherService;

	@PostMapping("/pincode")
	public ResponseEntity<Weather> updateSaveWeather(@RequestBody String requestInput) {
			return weatherService.savePincodeWeatherInfo(requestInput);
	}

}
