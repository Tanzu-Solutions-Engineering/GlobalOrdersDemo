package com.example.demo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ISOCountriesTest {
	
	@Autowired
	private ISOCountries countries;
	
	@Test
	public void shouldBeOK() {
		assertEquals("AF", countries.getCountries()[0].Code);
	}

}
