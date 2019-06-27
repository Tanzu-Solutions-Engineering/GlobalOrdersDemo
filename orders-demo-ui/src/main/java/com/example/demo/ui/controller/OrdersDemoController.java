package com.example.demo.ui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Controller
public class OrdersDemoController {
	
	@Value( "${monitorHost}" )
	private String monitorHost;
	
	@Value( "${generatorHost}" )
	private String generatorHost;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static Logger logger = LoggerFactory.getLogger(OrdersDemoController.class);

	@RequestMapping(value="/")
	public String getStatus(Model model) {
		
		model.addAttribute("monitorHost", monitorHost);
		model.addAttribute("generatorHost", generatorHost);
		
		return "index.html";
	}
	
	@RequestMapping( value="/getHeatMap", produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody String getHeatMap() {
		ResponseEntity<String> response = request(monitorHost, "/getHeatMap");
		return response.getBody();
	}
	
    private ResponseEntity<String> request(String host, String path) {
        ResponseEntity<String> response;

        final String uri = "http://" + host + path;
        logger.info("sending request to " + uri);
        try {
            response = this.restTemplate.getForEntity(uri, String.class);
        } catch(RestClientException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        logger.info("finished request to " + uri);
        return response;
    }

}
