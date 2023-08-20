package org.objectworld.shopping.customer.service.test;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.objectworld.shopping.common.dto.CustomerDto;
import org.objectworld.shopping.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@ComponentScan("org.objectworld")
@Rollback(false)
@Slf4j
@DisplayName("CustomerService test case")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerServiceTest {
	private static int beforeCount;
	private static Long newId;

	@Autowired
	private CustomerService service;

	@Test
	@Order(1)
	@DisplayName("Select All Before Insert Test")
//	@Disabled("Temporary Disable")
	public void init() {
		List<CustomerDto> list = service.findAll();
		log.info("list.size={}", list.size());
		log.info("list={}", list);
		beforeCount = list.size();
	}
	
	@Test
	@Order(2)
	@DisplayName("Insert Test")
//	@Disabled("Temporary Disable")
	public void create() {		
		// given
		CustomerDto dto = CustomerDto.builder()
				.firstName("철수")
				.lastName("배")
				.enabled(true) 
				.build();
		dto = service.save(dto);
		newId = dto.getId();
		log.info("created id = {}", newId);
		log.info("dto={}", dto);
	}
	
	@Test
	@Order(3) 
	@DisplayName("Select All After Insert Test")
//	@Disabled("Temporary Disable") 
	public void findById() { 
		CustomerDto dto = service.findById(newId);
		
		Assertions.assertNotNull(dto);
		Assertions.assertEquals(dto.getFirstName(), "철수");
	}

	@Test
	@Order(4) 
	@DisplayName("Select All After Insert Test")
//	@Disabled("Temporary Disable")
	public void findAll() {
		List<CustomerDto> list = service.findAll();
		log.info("list.size={}", list.size());
		log.info("list={}", list);
		Assertions.assertEquals(list.size(), beforeCount + 1);
	}
	
	@Test
	@Order(5)
	@DisplayName("Delete Test")
//	@Disabled("Temporary Disable")	
	public void delete() {
		log.info("delete id = {}", newId);
		service.delete(newId);
	}
	
	@Test
	@Order(6)
	@DisplayName("Select Enabled Test")
//	@Disabled("Temporary Disable")	
	public void findAllActive() {
		List<CustomerDto> list = service.findAllActive();
		log.info("list.size={}", list.size());
		log.info("list={}", list);
		Assertions.assertEquals(list.size(), beforeCount);
	}	
}
