package org.objectworld.shopping.order.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.objectworld.shopping.common.dto.OrderItemDto;
import org.objectworld.shopping.common.dto.ProductDto;
import org.objectworld.shopping.common.util.ObjectMapper;
import org.objectworld.shopping.order.domain.Order;
import org.objectworld.shopping.order.domain.OrderItem;
import org.objectworld.shopping.order.repository.OrderItemRepository;
import org.objectworld.shopping.order.repository.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    public OrderItemService(OrderItemRepository orderItemRepository,
                            OrderRepository orderRepository,
                            RestTemplate restTemplate) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }

    public static OrderItemDto mapToDto(OrderItem orderItem) {
    	return ObjectMapper.map(orderItem, OrderItemDto.class);
    }
    
    public static OrderItem createFromDto(OrderItemDto orderItemDto) {
        return ObjectMapper.map(orderItemDto, OrderItem.class);
    }

	private ProductDto getProductDto(Long productId) {
		ResponseEntity<ProductDto> productResponseEntity = this.restTemplate.getForEntity(
        		"http://localhost:8080/api/products/{id}",
                ProductDto.class,
                productId);
        ProductDto productDto = productResponseEntity.getBody();
		return productDto;
	}
	
    @Transactional(readOnly = true)
    public List<OrderItemDto> findAll() {
        log.debug("Request to get all OrderItems");
        return this.orderItemRepository.findAll()
            .stream()
            .map(OrderItemService::mapToDto)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderItemDto findById(Long id) {
        log.debug("Request to get OrderItem : {}", id);
        return this.orderItemRepository.findById(id)
    		.map(OrderItemService::mapToDto).orElse(null);
    }

    public OrderItemDto create(OrderItemDto orderItemDto) {
        log.debug("Request to create OrderItem : {}", orderItemDto);
        
        Order order = this.orderRepository.findById(orderItemDto.getOrderId())
        	.orElseThrow(() -> new IllegalStateException("The Order does not exist!"));

        ProductDto productDto = getProductDto(orderItemDto.getProductId());

        OrderItem orderItem = createFromDto(orderItemDto);
        orderItem.setOrder(order);
        orderItem = this.orderItemRepository.save(orderItem);

        order.setTotalPrice(order.getTotalPrice().add(
        		productDto.getPrice().multiply(
        		BigDecimal.valueOf(orderItem.getQuantity()))));
        this.orderRepository.save(order);

        return mapToDto(orderItem);
    }

    public void delete(Long id) {
        log.debug("Request to delete OrderItem : {}", id);
        
        OrderItem orderItem = this.orderItemRepository.findById(id)
            .orElseThrow(() -> new IllegalStateException("The OrderItem does not exist!"));
        
        Order order = orderItem.getOrder();
        ProductDto productDto = getProductDto(orderItem.getProductId());
        
        order.setTotalPrice(order.getTotalPrice().subtract(
        		productDto.getPrice().multiply(
        		BigDecimal.valueOf(orderItem.getQuantity()))));
        this.orderRepository.save(order);
        
        this.orderItemRepository.deleteById(id);
    }
}
