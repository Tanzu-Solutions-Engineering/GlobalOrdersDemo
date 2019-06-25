package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HeatMapColorerTests {
	
	@Test
	public void shouldHandleAnEmptyMap() {
		
		// Given a map and a colorer
		HeatMapColorer c = new HeatMapColorer();
		HeatMap map = new HeatMap();
		
		// when coloring an empty map
		c.assignColors(map);
		
		// Then there should have been no exception
		assertTrue(true);
	}
	
	@Test
	public void shouldColourASingleItem() {
		// Given a map and a colorer
		HeatMapColorer c = new HeatMapColorer();
		HeatMap map = new HeatMap();
		
		// When a single item is added to the map
		map.addOrderSum("x", 13);
		// And the map is coloured
		c.assignColors(map);
		
		// Then there shoud be a single item
		HeatMapItem[] items = map.getItemsArray();
		assertEquals(1, items.length);
		
		// and the item should be coloured white
		HeatMapItem i = items[0];
		assertEquals("#FFFFFF", i.getHeatMapColor());
	}

	@Test
	public void shouldColourTwoItems() {
		// Given a map and a colorer
		HeatMapColorer c = new HeatMapColorer();
		HeatMap map = new HeatMap();
		
		// When two items are added to the map
		map.addOrderSum("x", 13);
		map.addOrderSum("y", 17);
		// And the map is coloured
		c.assignColors(map);
		
		// Then there should be two items
		HeatMapItem[] items = map.getItemsArray();
		assertEquals(2, items.length);
		
		// and the first item should be coloured white
		HeatMapItem i = items[0];
		assertEquals("#FFFFFF", i.getHeatMapColor());
		
		// and the second item should be colored Pivotal green
		// Color(7,63,7) = #073f07
		i = items[1];
		assertEquals("#073F07", i.getHeatMapColor());
	}

}
