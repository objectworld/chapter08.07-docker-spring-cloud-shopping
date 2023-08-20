package org.objectworld.shopping.order.service.test;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.objectworld.shopping.common.dto.PaymentDto;
import org.objectworld.shopping.order.domain.enumeration.PaymentStatus;
import org.objectworld.shopping.order.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@ComponentScan("org.objectworld")
@Rollback(false)
@Slf4j
@DisplayName("PaymentService test case")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PaymentServiceTest {
	private static int beforeCount;
	private static Long newId;

	@Autowired
	private PaymentService service;

	@Test
	@Order(1)
	@DisplayName("Select All Before Insert Test")
//	@Disabled("Temporary Disable")
	public void init() {
		List<PaymentDto> list = service.findAll();
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
		PaymentDto dto = PaymentDto.builder()
				.orderId(1L)
				.status(PaymentStatus.PENDING.toString())
				.build();
		dto = service.create(dto);
		newId = dto.getId();
		log.info("created id = {}", newId);
		log.info("dto={}", dto);
	}
	
	@Test
	@Order(3) 
	@DisplayName("Select All After Insert Test")
//	@Disabled("Temporary Disable") 
	public void findById() { 
		PaymentDto dto = service.findById(newId);
		
		Assertions.assertNotNull(dto);
		Assertions.assertEquals(dto.getId(), newId);
	}

	@Test
	@Order(4) 
	@DisplayName("Select All After Insert Test")
//	@Disabled("Temporary Disable")
	public void findAll() {
		List<PaymentDto> list = service.findAll();
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
	@DisplayName("Select All After Delete Test")
//	@Disabled("Temporary Disable")
	public void findAllAfterDelete() {
		List<PaymentDto> list = service.findAll();
		log.info("list.size={}", list.size());
		log.info("list={}", list);
		Assertions.assertEquals(list.size(), beforeCount);
	}
}
