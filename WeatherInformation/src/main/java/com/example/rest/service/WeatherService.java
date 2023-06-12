package com.example.rest.service;

import java.util.Map;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.rest.common.CommonService;
import com.example.rest.dao.PincodeWeatherRepository;
import com.example.rest.entity.Weather;

@Service
public class WeatherService {

	@Value("${weather.api.url}")
	String apiUrl;

	@Value("${api.key}")
	String apiKey;

	@Autowired
	private PincodeWeatherRepository pincodeWeatherRepository;

	public ResponseEntity<Weather> savePincodeWeatherInfo(String requestInput) {

		Weather weather = new Weather();

		try {

			JSONObject jsonObject = new JSONObject(requestInput);

			if (!jsonObject.getString("pincode").isEmpty() && !jsonObject.getString("date").isEmpty()) {

				URIBuilder uriBuilder = new URIBuilder(apiUrl);
				uriBuilder.setParameter("q", jsonObject.getString("pincode"))
						.setParameter("date", jsonObject.getString("date")).setParameter("appid", apiKey);

				HttpGet request = new HttpGet(uriBuilder.build());
				CommonService commonService = new CommonService();
				Map<String, Object> responseMap = commonService.getService(request);

				JSONParser parser = new JSONParser();
				Object obj = parser.parse(String.valueOf(responseMap.get("response")));
				org.json.simple.JSONObject jsonObject2 = (org.json.simple.JSONObject) obj;

				if (responseMap.get("execute").equals(true)) {
					Object coord = (Object) jsonObject2.get("coord");
					JSONObject coords = new JSONObject(coord.toString());
					Object jsonMainObject = (Object) jsonObject2.get("main");
					JSONObject jsonTemp = new JSONObject(jsonMainObject.toString());

					weather.setPincode(Integer.parseInt(jsonObject.getString("pincode")));
					weather.setLat(coords.getDouble("lat"));
					weather.setLon(coords.getDouble("lon"));
					weather.setTemperature(jsonTemp.getDouble("temp"));

					pincodeWeatherRepository.save(weather);
					return new ResponseEntity<>(weather, HttpStatus.OK);
				}

			} else {

				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}

		} catch (Exception e) {
			e.getMessage();
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
