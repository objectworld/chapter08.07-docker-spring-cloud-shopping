package org.objectworld.shopping.customer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class CustomerServiceApplicationTests {
	@Value("${spring.application.name}")
	String applicationName;
	
	@Test
	public void getName() {
		log.info("applicationName={}", applicationName);
		Assertions.assertEquals("config-server", applicationName);		
	}
}
