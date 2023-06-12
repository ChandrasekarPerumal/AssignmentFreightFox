package com.example.rest.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.example.rest.config.AppConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonService {

	public Map<String, Object> getService(HttpGet request) throws Exception {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		try (CloseableHttpClient client = HttpClients.createDefault();
				CloseableHttpResponse response = client.execute(request)) {

			HttpEntity entity = response.getEntity();
			String output_generated = "";
			if (entity != null)
				output_generated = EntityUtils.toString(entity); // return it as a String

			// check API status
			if (response.getStatusLine().getStatusCode() != 200) {

				responseMap.put("response", null);
				responseMap.put("execute", false);
				return responseMap;
			} else { // response success block

				responseMap.put("response", output_generated.toString());
				responseMap.put("execute", true);
				return responseMap;
			}

		} catch (HttpHostConnectException e) {
			e.printStackTrace();
//			logger.error(e.getMessage());
			throw new Exception(AppConstants.SERVER_CONNECTION_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
//			logger.error(e.getMessage());
			responseMap.put("execute", false);
			if (Common.isJSON(e.getMessage())) {
				ObjectMapper mapper = new ObjectMapper();
				ErrorResponse errorResponse = mapper.readValue(e.getMessage(), ErrorResponse.class);
				if (errorResponse.getResponse() != null)
					throw new Exception(errorResponse.getResponse());
				else
					throw new Exception(errorResponse.getMessage());
			} else
				throw new Exception(e.getMessage());
		}
	}

	public Map<String, Object> postService(HttpPost request) throws Exception {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			response = client.execute(request);
			String output_generated = "";
			HttpEntity entity = response.getEntity();

			if (entity != null)
				output_generated = EntityUtils.toString(entity);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new Exception(output_generated);
			} else {
				responseMap.put("response", output_generated.toString());
				responseMap.put("execute", true);
				return responseMap;
			}

		} catch (HttpHostConnectException e) {

			e.printStackTrace();
			throw new Exception(AppConstants.SERVER_CONNECTION_ERROR);
		} catch (Exception e) {

			e.printStackTrace();
			responseMap.put("execute", false);
			if (Common.isJSON(e.getMessage())) {
				ObjectMapper mapper = new ObjectMapper();
				ErrorResponse errorResponse = mapper.readValue(e.getMessage(), ErrorResponse.class);
				if (errorResponse.getResponse() != null)
					throw new Exception(errorResponse.getResponse());
				else
					throw new Exception(errorResponse.getMessage());
			} else {
				throw new Exception(e.getMessage());
			}
		} finally {
			if (response != null)
				response.close();
			if (client != null)
				client.close();
		}
	}

}
