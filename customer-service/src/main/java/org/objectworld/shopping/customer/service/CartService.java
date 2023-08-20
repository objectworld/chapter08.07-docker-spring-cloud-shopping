package org.objectworld.shopping.customer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.objectworld.shopping.common.dto.CartDto;
import org.objectworld.shopping.common.util.ObjectMapper;
import org.objectworld.shopping.customer.domain.Cart;
import org.objectworld.shopping.customer.domain.Customer;
import org.objectworld.shopping.customer.domain.enumeration.CartStatus;
import org.objectworld.shopping.customer.repository.CartRepository;
import org.objectworld.shopping.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class CartService {
    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    
    public CartService(CartRepository cartRepository, CustomerRepository customerRepository) {
        this.cartRepository = cartRepository;
        this.customerRepository = customerRepository;
    }

    public static CartDto mapToDto(Cart cart) {
    	return ObjectMapper.map(cart, CartDto.class);
    }

    @Transactional(readOnly = true)
    public List<CartDto> findAll() {
        log.debug("Request to get all Carts");
        return this.cartRepository.findAll()
            .stream()
            .map(CartService::mapToDto)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CartDto> findAllActiveCarts() {
        return this.cartRepository.findByStatus(CartStatus.NEW)
            .stream()
            .map(CartService::mapToDto)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CartDto findById(Long id) {
        log.debug("Request to get Cart : {}", id);
        return this.cartRepository.findById(id)
        	.map(CartService::mapToDto).orElse(null);
    }

    @Transactional(readOnly = true)
    public CartDto findByCustomerId(Long customerId) {
        log.debug("Request to get Active Cart with Customer : {}", customerId);
        List<Cart> carts = this.cartRepository
            .findByStatusAndCustomerId(CartStatus.NEW, customerId);
        if (carts != null) {

            if (carts.size() == 1) {
                return mapToDto(carts.get(0));
            }
            if (carts.size() > 1) {
                throw new IllegalStateException("Many active carts detected !!!");
            }
        }
        return null;
    }

    public CartDto create(Long customerId) {
        log.debug("Request to create Cart : {}", customerId);
        if (this.findByCustomerId(customerId) == null) {
            Customer customer = this.customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalStateException("The Customer does not exist!"));

            Cart cart = Cart.builder()
            	.customer(customer)
            	.status(CartStatus.NEW)
            	.build();

            return mapToDto(this.cartRepository.save(cart));
        } else {
            throw new IllegalStateException("There is already an active cart");
        }
    }

    public void delete(Long id) {
        log.debug("Request to delete Cart : {}", id);
        Cart cart = this.cartRepository.findById(id)
        	.orElseThrow(() -> new IllegalStateException("Cannot find cart id : " + id));

        cart.setStatus(CartStatus.CANCELED);

        this.cartRepository.save(cart);
    }
}
