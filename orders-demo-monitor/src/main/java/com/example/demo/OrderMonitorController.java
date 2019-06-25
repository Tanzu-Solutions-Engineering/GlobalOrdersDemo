package com.example.demo;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
public class OrderMonitorController {
	
	@Autowired
	private OrderWindow window;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	private HeatMapColorer heatMapColourer = new HeatMapColorer();
	private static Logger logger = LoggerFactory.getLogger(OrderMonitorController.class);
	
    @RequestMapping(value="/getLastOrderAmount")
    public @ResponseBody int getLastOrderAmount(@RequestParam("state") String state) {
    	
    	return window.getLastOrderAmount(state);
    } 
    
    @RequestMapping(value="/getHeatMap")
    public @ResponseBody HeatMap getHeatMap() {
    	HeatMap map = new HeatMap();
    	Set<String> states = window.getStates();
    	for(String state : states) {
    		map.addOrderSum(state, window.getOrderSum(state));
    	}
    	
    	heatMapColourer.assignColors(map);
    	
    	return map;
    }
    
    @RequestMapping(value="/getStateOrderSum")
    public @ResponseBody int getStateOrderSum(@RequestParam("state") String state) {
    	
    	return window.getOrderSum(state);

    } 

    @RequestMapping(value="/killApp")
    public @ResponseBody String kill(){
		logger.warn("Killing application instance");
		System.exit(-1);    	
    	return "Killed";
    }   
    
	@RequestMapping(value = "/status")
	public @ResponseBody OrderMonitorInfo status() throws Exception{
		
		OrderMonitorInfo info = new OrderMonitorInfo();
		info.rabbitHost = rabbitTemplate.getConnectionFactory().getHost();
		info.setMessagesProcessed(window.getTotalSeen());

		
		//add details about VCAP APPLICATION
		//if(System.getenv("VCAP_APPLICATION") != null){
		//	Map vcapMap = mapper.readValue(System.getenv("VCAP_APPLICATION"), Map.class);
			
		//}
		
        return info;
    }  	
}
