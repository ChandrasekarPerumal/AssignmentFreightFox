package com.example.rest.common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Common {

	public static Boolean isJSON(String jsonString) {
		try {
			new JSONObject(jsonString);
		} catch (JSONException e) {
			e.printStackTrace();
			try {
				new JSONArray(jsonString);
			} catch (JSONException exception) {
				exception.printStackTrace();
				return false;
			}
		}
		return true;
	}

}
