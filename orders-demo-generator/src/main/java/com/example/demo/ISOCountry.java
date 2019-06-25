package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ISOCountry {
	
	@JsonProperty(value="Code")
	public String Code;
	
	@JsonProperty(value="Name")
	public String Name;
	
	public ISOCountry() {
		
	}
}
