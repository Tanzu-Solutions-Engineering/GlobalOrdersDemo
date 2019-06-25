package com.example.demo;

import java.util.Set;
import java.util.TreeSet;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A HeatMap holds a set of HeatMapItem
 * 
 * @author dmcintyre
 *
 */
public class HeatMap {
	
	private Set<HeatMapItem> items;
	
	public HeatMap(){
		items = new TreeSet<HeatMapItem>();	
	}
	
	public void addOrderSum(String state, int amount){
		HeatMapItem item = new HeatMapItem();
		item.setState(state);
		item.setValue(amount);
		items.add(item);
	}
	
	public Set<HeatMapItem> getItems() {
		return items;
	}
	
	@JsonIgnore
	public HeatMapItem[] getItemsArray() {
		return (HeatMapItem[])items.toArray(new HeatMapItem[]{});
	}
}
