package com.example.demo;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderMessageListener {
	
	@Autowired
	private OrderWindow window;
	
	public OrderMessageListener() {
		
	}
	
	public OrderMessageListener(OrderWindow window) {
		this.window = window;
	}
	
	@RabbitListener(queues = AMQPConfiguration.QUEUE_NAME)
	public void receiveMessage(final Order order) {
		
		window.registerOrder(order);
	
	}

}
