package org.objectworld.shopping.order.service;

import java.util.List;
import java.util.stream.Collectors;

import org.objectworld.shopping.common.dto.CartDto;
import org.objectworld.shopping.common.dto.OrderDto;
import org.objectworld.shopping.common.util.ObjectMapper;
import org.objectworld.shopping.order.domain.Order;
import org.objectworld.shopping.order.repository.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    public OrderService(OrderRepository orderRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }

    public static OrderDto mapToDto(Order order) {
    	return ObjectMapper.map(order, OrderDto.class);
    }

    public static Order createFromDto(OrderDto orderDto) {
		return ObjectMapper.map(orderDto, Order.class);
	}
	
	private CartDto getCartDtoByCustomerId(Long customerId) {
		ResponseEntity<CartDto> productResponseEntity = this.restTemplate.getForEntity(
        		"http://customer-service/api/carts/customer/{id}",
        		CartDto.class,
                customerId);
		CartDto cartDto = productResponseEntity.getBody();
		return cartDto;
	}
	
    @Transactional(readOnly = true)
    public List<OrderDto> findAll() {
        log.debug("Request to get all Orders");
        return this.orderRepository.findAll()
                .stream()
                .map(OrderService::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderDto findById(Long id) {
        log.debug("Request to get Order : {}", id);
        return this.orderRepository.findById(id)
        		.map(OrderService::mapToDto).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<OrderDto> findAllByCustomerId(Long customerId) {
        log.debug("Request to get Order with Customer : {}", customerId);
        
        CartDto cartDto = getCartDtoByCustomerId(customerId);
        return this.orderRepository.findByCartId(cartDto.getId())
                .stream()
                .map(OrderService::mapToDto)
                .collect(Collectors.toList());
    }

    public OrderDto create(OrderDto orderDto) {
        log.debug("Request to create Order : {}", orderDto);
        return mapToDto(this.orderRepository.save(createFromDto(orderDto)));
    }

    public void delete(Long id) {
        log.debug("Request to delete Order : {}", id);
        this.orderRepository.deleteById(id);
    }
}
