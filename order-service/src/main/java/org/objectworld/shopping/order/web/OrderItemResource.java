package org.objectworld.shopping.order.web;

import org.objectworld.shopping.common.dto.OrderItemDto;
import org.objectworld.shopping.order.service.OrderItemService;
import org.springframework.web.bind.annotation.*;

import static org.objectworld.shopping.common.util.RestBase.BASE_URL;

import java.util.List;

@RestController
@RequestMapping(BASE_URL + "/order-items")
public class OrderItemResource {

    private final OrderItemService itemService;

    public OrderItemResource(OrderItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<OrderItemDto> findAll() {
        return this.itemService.findAll();
    }

    @GetMapping("/{id}")
    public OrderItemDto findById(@PathVariable Long id) {
        return this.itemService.findById(id);
    }

    @PostMapping
    public OrderItemDto create(@RequestBody OrderItemDto orderItemDto) {
        return this.itemService.create(orderItemDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.itemService.delete(id);
    }
}
