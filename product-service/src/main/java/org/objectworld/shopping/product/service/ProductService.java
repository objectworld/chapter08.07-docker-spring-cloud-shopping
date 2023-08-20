package org.objectworld.shopping.product.service;

import java.util.List;
import java.util.stream.Collectors;

import org.objectworld.shopping.common.dto.ProductDto;
import org.objectworld.shopping.common.util.ObjectMapper;
import org.objectworld.shopping.product.domain.Product;
import org.objectworld.shopping.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public static ProductDto mapToDto(Product product) {
    	return ObjectMapper.map(product, ProductDto.class);
    }

	public static Product createFromDto(ProductDto productDto) {
    	return ObjectMapper.map(productDto, Product.class);
	}

    @Transactional(readOnly = true)
    public List<ProductDto> findAll() {
        log.debug("Request to get all Products");
        return this.productRepository.findAll()
            .stream()
            .map(ProductService::mapToDto)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductDto findById(Long id) {
        log.debug("Request to get Product : {}", id);
        return this.productRepository.findById(id)
        	.map(ProductService::mapToDto).orElse(null);
    }

    public ProductDto create(ProductDto productDto) {
        log.debug("Request to create Product : {}", productDto);
        return mapToDto(this.productRepository.save(createFromDto(productDto)));
    }

    public void delete(Long id) {
        log.debug("Request to delete Product : {}", id);
        this.productRepository.deleteById(id);
    }
}
