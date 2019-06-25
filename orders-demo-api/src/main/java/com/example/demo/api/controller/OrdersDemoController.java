package com.example.demo.api.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrdersDemoController {
	
	@Value( "${monitorHost}" )
	private String monitorHost;
	
	@Value( "${generatorHost}" )
	private String generatorHost;
	
	
	@RequestMapping(value="/")
	public String getStatus(Model model) {
		
		model.addAttribute("monitorHost", monitorHost);
		model.addAttribute("generatorHost", generatorHost);
		
		return "index.html";
	}

}
