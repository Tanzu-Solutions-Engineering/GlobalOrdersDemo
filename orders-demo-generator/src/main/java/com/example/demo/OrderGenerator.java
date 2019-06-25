package com.example.demo;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderGenerator implements Runnable {
	
	@Autowired
	private ISOCountries countries;
	
	static Logger logger = LoggerFactory.getLogger(OrderGenerator.class);

	@Autowired
	private RabbitTemplate rabbitTemplate;

	private boolean generating = false;
	private boolean stopped = false;
	
	private int messagesSent = 0;
	
	public void startGen(){
		this.generating = true;
	}

	public void stopGen(){
		this.generating = false;
	}
	
	public boolean isGenerating() {
		return generating;
	}
	
	public boolean isStopped() {
		return stopped;
	}
	
	public int getMessagesSent() {
		return messagesSent;
	}
	
	@Override
	public void run() {
		
		logger.info("Run generator");
		
		ISOCountry[] c = countries.getCountries();

		while (!stopped){
			if (generating){
				
				logger.info("Generating an order");
				
				Random random = new Random();
				String state = c[random.nextInt(c.length)].Code;
				int value = 1+random.nextInt(100);
				Order order = new Order(state, value);
				

				try {
					rabbitTemplate.convertAndSend(AMQPConfiguration.EXCHANGE_NAME, "", order);
					messagesSent++;
				} catch (AmqpException e1) {
					
					logger.info("Error during send: " + e1.getMessage());
					throw new RuntimeException(e1);
				}
			}
			else{
				try{
					Thread.sleep(500);
				}catch(Exception e){ return; }
			}
		try{
			   Thread.sleep(100);
		   }
		   catch(Exception e){ return; }
		}
		
	}
	
	public void shutdown(){
		stopped = true;
		
	}

}
