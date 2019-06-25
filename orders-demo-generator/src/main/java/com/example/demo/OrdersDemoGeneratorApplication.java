package com.example.demo;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;

@SpringBootApplication
@ComponentScan(basePackageClasses = { ISOCountries.class })
public class OrdersDemoGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdersDemoGeneratorApplication.class, args);
	}
	
	
	@Bean
	public ISOCountries countries() throws JsonParseException, JsonMappingException, IOException {
		
			ClassPathResource resource = new ClassPathResource("iso-2-countries.json");
		
		    ISOCountry[] countries = new ObjectMapper()
		      .readValue(resource.getInputStream(), ISOCountry[].class);
		    
		    ISOCountries c = new ISOCountries();
		    c.setCountries(countries);
		   
		    
		    return c;
	}


}
