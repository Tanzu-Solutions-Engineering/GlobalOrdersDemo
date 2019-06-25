package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderWindowTests {
	
	private OrderWindow window;
	
	public OrderWindowTests() {
		window = new OrderWindow();
	}

	@Test
	public void registersOrder() {
		
		// Given a new order
		Order order = new Order("x", 1);
		
		// When it is registered
		window.registerOrder(order);
		
		// Then it doesn't get lost in another dimension
		int sum = window.getOrderSum("x");
		assertEquals(1, sum);
	}

	@Test
	public void registersOrdersForDifferentStates() {
		
		// Given the map
		
		// When orders are registered for different states
		window.registerOrder(new Order("x", 7));
		window.registerOrder(new Order("y", 11));
		
		// Then the sums are still correct
		int sum = window.getOrderSum("x");
		assertEquals(7, sum);
		sum = window.getOrderSum("y");
		assertEquals(11, sum);
	}

	@Test
	public void registersManyOrders() {
		
		// Given the map
		
		// When orders are registered
		window.registerOrder(new Order("x", 1));
		window.registerOrder(new Order("x", 2));
		window.registerOrder(new Order("x", 3));
		window.registerOrder(new Order("x", 4));
		window.registerOrder(new Order("x", 5));
		window.registerOrder(new Order("x", 6));
		window.registerOrder(new Order("x", 7));
		window.registerOrder(new Order("x", 8));
		window.registerOrder(new Order("x", 9));
		window.registerOrder(new Order("x", 10));
		
		// Then the sum is right
		int sum = window.getOrderSum("x");
		assertEquals(55, sum);
	}

	@Test
	public void windowsOrders() {
		
		// Given the map
		
		// When orders are registered
		window.registerOrder(new Order("x", 1));
		window.registerOrder(new Order("x", 2));
		window.registerOrder(new Order("x", 3));
		window.registerOrder(new Order("x", 4));
		window.registerOrder(new Order("x", 5));
		window.registerOrder(new Order("x", 6));
		window.registerOrder(new Order("x", 7));
		window.registerOrder(new Order("x", 8));
		window.registerOrder(new Order("x", 9));
		window.registerOrder(new Order("x", 10));
		window.registerOrder(new Order("x", 20));
		
		// Then the sum is right
		int sum = window.getOrderSum("x");
		assertEquals(74, sum);
	}
	
	@Test public void returnsZeroForNonExistentState() {
		int i = window.getOrderSum("argleblargle");
		assertEquals(0, i);
	}

	@Test public void returnsValueOfLastOrder() {
		// Given the map
		
		// When orders are registered
		window.registerOrder(new Order("x", 1));
		window.registerOrder(new Order("x", 2));
		window.registerOrder(new Order("x", 3));
		// Then the latest order value is right
		int sum = window.getLastOrderAmount("x");
		assertEquals(3, sum);

	}

}
