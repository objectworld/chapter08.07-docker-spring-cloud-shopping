package org.objectworld.shopping.customer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.objectworld.shopping.common.dto.CustomerDto;
import org.objectworld.shopping.common.util.ObjectMapper;
import org.objectworld.shopping.customer.domain.Customer;
import org.objectworld.shopping.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static CustomerDto mapToDto(Customer customer) {
//    	if(customer != null) {
//	    	return CustomerDto.builder()
//	    			.id(customer.getId())
//	    			.firstName(customer.getFirstName())
//	    			.lastName(customer.getLastName())
//	                .email(customer.getEmail())
//	                .telephone(customer.getTelephone())
//	                .homeAddress(AddressService.mapToDto(customer.getHomeAddress()))
//	                .officeAddress(AddressService.mapToDto(customer.getOfficeAddress()))
//	                .enabled(customer.getEnabled())
//	                .build();
//    	} else {
//    		return null;
//    	}
    	return ObjectMapper.map(customer, CustomerDto.class);
    }

    public static Customer createFromDto(CustomerDto customerDto) {
//        return Customer.builder()
//            	.firstName(customerDto.getFirstName())
//            	.lastName(customerDto.getLastName())
//                .email(customerDto.getEmail())
//                .telephone(customerDto.getTelephone())
//                .homeAddress(AddressService.createFromDto(customerDto.getHomeAddress()))
//                .officeAddress(AddressService.createFromDto(customerDto.getOfficeAddress()))
//                .carts(Collections.emptySet())
//                .enabled(Boolean.TRUE)
//                .build();
        return ObjectMapper.map(customerDto, Customer.class);
    }
    
    @Transactional(readOnly = true)
    public List<CustomerDto> findAll() {
        log.debug("Request to get all Customers");
        return this.customerRepository.findAll()
                .stream()
                .map(CustomerService::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CustomerDto findById(Long id) {
        log.debug("Request to get Customer : {}", id);
        return this.customerRepository.findById(id)
        		.map(CustomerService::mapToDto).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<CustomerDto> findAllActive() {
        log.debug("Request to get all active Customers");
        return this.customerRepository.findAllByEnabled(true)
                .stream()
                .map(CustomerService::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CustomerDto> findAllInactive() {
        log.debug("Request to get all inactive Customers");
        return this.customerRepository.findAllByEnabled(false)
                .stream()
                .map(CustomerService::mapToDto)
                .collect(Collectors.toList());
    }

    public CustomerDto save(CustomerDto customerDto) {
        log.debug("Request to save Customer : {}", customerDto);
        return mapToDto(this.customerRepository.save(createFromDto(customerDto)));
    }

    public void delete(Long id) {
        log.debug("Request to delete Customer : {}", id);

        Customer customer = this.customerRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Cannot find Customer id : " + id));

        customer.setEnabled(false);
        this.customerRepository.save(customer);
    }
}
