package org.objectworld.shopping.common.service;

import org.objectworld.shopping.common.domain.Address;
import org.objectworld.shopping.common.dto.AddressDto;
import org.objectworld.shopping.common.util.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    public static AddressDto mapToDto(Address address) {
    	return ObjectMapper.map(address, AddressDto.class);
    }

    public static Address createFromDto(AddressDto addressDto) {
        return ObjectMapper.map(addressDto, Address.class);
    }
}
	