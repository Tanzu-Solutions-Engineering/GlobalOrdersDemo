package com.example.demo;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@Controller
@CrossOrigin
public class OrderGeneratorController {
	
	static Logger logger = LoggerFactory.getLogger(OrderGeneratorController.class);

	@Autowired
	private OrderGenerator generator;
	Thread threadSender;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@PostConstruct
    public void startGenerator() {
		threadSender = new Thread(generator);
		threadSender.start();
    }
	
    @PreDestroy
    public void shutdownThread(){
    	
    	generator.shutdown();
    }
    
	@RequestMapping(value = "/status")
	public @ResponseBody OrderGeneratorInfo status() throws Exception{
		
		OrderGeneratorInfo info = new OrderGeneratorInfo();
		info.rabbitHost = rabbitTemplate.getConnectionFactory().getHost();
		info.setStopped(generator.isStopped());
		info.setStreaming(generator.isGenerating());
		info.setMessagesSent(generator.getMessagesSent());
		
		ObjectMapper mapper = new ObjectMapper();
		
		//add details about VCAP APPLICATION
		//if(System.getenv("VCAP_APPLICATION") != null){
		//	Map vcapMap = mapper.readValue(System.getenv("VCAP_APPLICATION"), Map.class);
			
		//}
		
        return info;
    }  	
    
    @RequestMapping(value="/startStream")
    public @ResponseBody String startStream() {
    	
    	String rabbitHost = rabbitTemplate.getConnectionFactory().getHost();
    	
		if (rabbitHost == null) {
			return "Please bind a RabbitMQ service";
		}
		
		logger.warn("Rabbit Host "+ rabbitHost);
    	
    	if (generator.isGenerating()) {
    		return "Data already being generated";
    	}
 
    	generator.startGen();
    	return "Started";

    }    	

    @RequestMapping(value="/stopStream")
    public @ResponseBody String stopStream(){

    	String rabbitHost = rabbitTemplate.getConnectionFactory().getHost();
    	
		if (rabbitHost == null) {
			return "Please bind a RabbitMQ service";
		}
		
		logger.warn("Rabbit Host "+ rabbitHost);
    	
    	if (!generator.isGenerating()) {
    		return "Data not being generated";
    	}
 
    	generator.stopGen();

    	return "Stopped";
    } 
    
    
    @RequestMapping(value="/killApp")
    public @ResponseBody String kill(){
		logger.warn("Killing application instance");
		System.exit(-1);    	
    	return "Killed";

    }       
}
