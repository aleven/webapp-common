package it.attocchi.rest;

import com.google.gson.Gson;

public abstract class GsonObject implements JsonObject {
	
	@Override
	public String toJson() {

		return new Gson().toJson(this);
	}
}
