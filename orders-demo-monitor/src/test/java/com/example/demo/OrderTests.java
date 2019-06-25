package com.example.demo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OrderTests {
	
	@Test
	public void shouldSetProperties() {
		
		Order given = new Order("x", 5);
		
		assertEquals("x", given.getState());
		assertEquals(5, given.getAmount());
	}

}
