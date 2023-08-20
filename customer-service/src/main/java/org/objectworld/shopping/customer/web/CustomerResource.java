package org.objectworld.shopping.customer.web;

import org.objectworld.shopping.common.dto.CustomerDto;
import org.objectworld.shopping.customer.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import static org.objectworld.shopping.common.util.RestBase.BASE_URL;

import java.util.List;

@RestController
@RequestMapping(BASE_URL + "/customers")
public class CustomerResource {

    private final CustomerService customerService;

    public CustomerResource(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerDto> findAll() {
        return this.customerService.findAll();
    }

    @GetMapping("/{id}")
    public CustomerDto findById(@PathVariable Long id) {
        return this.customerService.findById(id);
    }

    @GetMapping("/active")
    public List<CustomerDto> findAllActive() {
        return this.customerService.findAllActive();
    }

    @GetMapping("/inactive")
    public List<CustomerDto> findAllInactive() {
        return this.customerService.findAllInactive();
    }

    @PostMapping
    public CustomerDto create(CustomerDto customerDto) {
        return this.customerService.save(customerDto);
    }

    @PutMapping("/{id}")
    public CustomerDto save(CustomerDto customerDto) {
        return this.customerService.save(customerDto);
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.customerService.delete(id);
    }
}