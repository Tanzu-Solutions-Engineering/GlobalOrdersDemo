package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Order {

	public String state;
	public int amount;
	
	public Order(
			@JsonProperty("state") String state, 
			@JsonProperty("amount") int amount) {
		this.state = state;
		this.amount = amount;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
}
