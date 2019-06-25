package com.example.demo;

import java.awt.Color;

public class HeatMapColorer {
	
    private final Color WHITE = new Color(255,255,255);
    private final Color PIVOTAL = new Color(7,63,7);
	
	public void assignColors(HeatMap map) {
		
		HeatMapItem[] items = map.getItemsArray();
		
		if(items.length > 0) {
			
			int min = items[0].getValue();
			int max = items[items.length-1].getValue();
			
			for (int i=0; i<items.length; i++) {
				
				HeatMapItem item = items[i];
				
				items[i].setHeatMapColor(colorGradient(max, min, item.getValue()));

			}
		}
	}
	
	/*
	 * Returns a HEX color string shaded between white and Pivotal green
	 * 
	 * |min ............. max|
	 *          v
	 *   .    .
	 * 0123456789
	 *   012345
	 */
	public String colorGradient(int max, int min, int value) {
		
		int maxIndex = max - min;
		int valueIndex = value - min;
		float ratio = 0.0f;
		
		if(maxIndex > 0) {
			ratio = (float) valueIndex / (float) maxIndex;
		}
		
        int red = (int) (PIVOTAL.getRed() * ratio + WHITE.getRed() * (1 - ratio));
        int green = (int) (PIVOTAL.getGreen() * ratio + WHITE.getGreen() * (1 - ratio));
        int blue = (int) (PIVOTAL.getBlue() * ratio + WHITE.getBlue() * (1 - ratio));		
		
        return String.format("#%1$02X%2$02X%3$02X", red, green, blue);
	}
}
