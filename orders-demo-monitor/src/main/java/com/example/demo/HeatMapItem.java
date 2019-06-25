package com.example.demo;

import java.io.Serializable;

public class HeatMapItem implements Serializable, Comparable{

	private String state;
	private int value;
	private String heatMapColor;
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getHeatMapColor() {
		return heatMapColor;
	}
	
	public void setHeatMapColor(String heatMapColor) {
		this.heatMapColor = heatMapColor;
	}
	
	/*
	 * HeatMapItems are sorted by their state
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object o) {
		if (!(o instanceof HeatMapItem)) {
			throw new RuntimeException("Trying to compare HeatMapIteam and another obj");
		}
		
		int compare = String.valueOf(getState()).compareTo(((HeatMapItem)o).getState());
		return compare;
	}

}
