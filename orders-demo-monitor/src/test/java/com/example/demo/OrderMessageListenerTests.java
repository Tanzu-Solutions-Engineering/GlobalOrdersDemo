package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.charset.StandardCharsets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMessageListenerTests {
	
	private OrderMessageListener listener;
	private OrderWindow window = new OrderWindow();
	
	public OrderMessageListenerTests() {
		listener = new OrderMessageListener(window);
	}

	@Test
	public void registersStringifiedOrder() {
		
		// Given an order
		Order order = new Order("x", 5);
		
		// When it is received
		listener.receiveMessage(order);
		
		// Then it will have been reconstructed in the listener's window
		int i = window.getOrderSum("x");
		assertEquals(5, i);
	}

}
