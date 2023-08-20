package org.objectworld.shopping.customer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.objectworld.shopping.common.dto.CartDto;
import org.objectworld.shopping.common.dto.OrderDto;

/**
 *
 * @author n.lamouchi
 */
@FeignClient(name = "order-service")
public interface OrderServiceClient {

    @RequestMapping(value = "/api/orders", method = POST)
    OrderDto create(CartDto cartDto);

}
