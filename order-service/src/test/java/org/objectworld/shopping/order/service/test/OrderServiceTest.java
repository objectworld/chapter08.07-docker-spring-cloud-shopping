package org.objectworld.shopping.order.service.test;
import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.objectworld.shopping.common.dto.AddressDto;
import org.objectworld.shopping.common.dto.OrderDto;
import org.objectworld.shopping.order.domain.enumeration.OrderStatus;
import org.objectworld.shopping.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@ComponentScan("org.objectworld")
@Rollback(false)
@Slf4j
@DisplayName("OrderService test case")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderServiceTest {
	private static int beforeCount;
	private static Long newId;

	@Autowired
	private OrderService service;

	@Test
	@Order(1)
	@DisplayName("Select All Before Insert Test")
//	@Disabled("Temporary Disable")
	public void init() {
		List<OrderDto> list = service.findAll();
		log.info("list.size={}", list.size());
		log.info("list={}", list);
		beforeCount = list.size();
	}
	
	@Test
	@Order(2)
	@DisplayName("Insert Test")
//	@Disabled("Temporary Disable")
	public void create() {
		AddressDto addressDto = AddressDto.builder()
				.country("KR")
				.city("서울")
				.address1("종로구 청와대로 1번지")
				.address2(null)
				.postcode("03048")
				.build();
		// given
		OrderDto orderDto = OrderDto.builder()
				.cartId(1L)
				.shipmentAddress(addressDto)
				.totalPrice(BigDecimal.ZERO)
				.status(OrderStatus.NEW.toString())
				.build();
		orderDto = service.create(orderDto);
		newId = orderDto.getId();
		log.info("created id = {}", newId);
		log.info("order={}", orderDto);
	}
	
	@Test
	@Order(3) 
	@DisplayName("Select All After Insert Test")
//	@Disabled("Temporary Disable") 
	public void findById() { 
		OrderDto dto = service.findById(newId);
		
		Assertions.assertNotNull(dto);
		Assertions.assertEquals(dto.getOrderItems().size(), 0);
	}

	@Test
	@Order(4) 
	@DisplayName("Select All After Insert Test")
//	@Disabled("Temporary Disable")
	public void findAll() {
		List<OrderDto> list = service.findAll();
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
		List<OrderDto> list = service.findAll();
		log.info("list.size={}", list.size());
		log.info("list={}", list);
		Assertions.assertEquals(list.size(), beforeCount);
	}
	
	@Test
	@Order(7)
	@DisplayName("Select Enabled Test")
//	@Disabled("Temporary Disable")	
	public void findAllByCustomerId() {
		List<OrderDto> list = service.findAllByCustomerId(1L);
		log.info("list.size={}", list.size());
		log.info("list={}", list);
		Assertions.assertEquals(list.size(), 1);
	}	
}
