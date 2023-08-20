package org.objectworld.shopping.product.service.test;
import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.objectworld.shopping.common.dto.ProductDto;
import org.objectworld.shopping.product.domain.enumeration.ProductStatus;
import org.objectworld.shopping.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@ComponentScan("org.objectworld")
@Rollback(false)
@Slf4j
@DisplayName("ProductService test case")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceTest {
	private static int beforeCount;
	private static Long newId;

	@Autowired
	private ProductService service;

	@Test
	@Order(1)
	@DisplayName("Select All Before Insert Test")
//	@Disabled("Temporary Disable")
	public void init() {
		List<ProductDto> list = service.findAll();
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
		ProductDto dto = ProductDto.builder()
				.categoryId(1L)
				.name("Test Product")
				.description("This is test product")
				.price(BigDecimal.TEN)
				.status(ProductStatus.DISCONTINUED.toString())
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
		ProductDto dto = service.findById(newId);
		
		Assertions.assertNotNull(dto);
		Assertions.assertEquals(dto.getCategoryId(), 1L);
	}

	@Test
	@Order(4) 
	@DisplayName("Select All After Insert Test")
//	@Disabled("Temporary Disable")
	public void findAll() {
		List<ProductDto> list = service.findAll();
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
		List<ProductDto> list = service.findAll();
		log.info("list.size={}", list.size());
		log.info("list={}", list);
		Assertions.assertEquals(list.size(), beforeCount);
	}
}
