package org.objectworld.shopping.product.service;

import java.util.List;
import java.util.stream.Collectors;

import org.objectworld.shopping.common.dto.CategoryDto;
import org.objectworld.shopping.common.util.ObjectMapper;
import org.objectworld.shopping.product.domain.Category;
import org.objectworld.shopping.product.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public static CategoryDto mapToDto(Category category) {
    	CategoryDto categoryDto = ObjectMapper.map(category, CategoryDto.class);
    	if(categoryDto != null) {
    		if(category.getProducts() != null) {
    			categoryDto.setProductCount(category.getProducts().size());
    		} else {
    			categoryDto.setProductCount(9);
    		}
    	}
    	return categoryDto;
    }

    public static Category createFromDto(CategoryDto categoryDto) {
    	return ObjectMapper.map(categoryDto, Category.class);
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> findAll() {
        log.debug("Request to get all Categories");
        return this.categoryRepository.findAll()
                .stream()
                .map(CategoryService::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDto findById(Long id) {
        log.debug("Request to get Category : {}", id);
        return this.categoryRepository.findById(id)
        		.map(CategoryService::mapToDto)
        		.orElseThrow(IllegalStateException::new);
    }

    public CategoryDto create(CategoryDto categoryDto) {
        log.debug("Request to create Category : {}", categoryDto);
//        return mapToDto(this.categoryRepository.save(
//                Category.builder()
//                	.name(categoryDto.getName())
//                	.description(categoryDto.getDescription())
//                	.products(Collections.emptySet())
//                	.build()
//                )
//        );
        return mapToDto(this.categoryRepository.save(createFromDto(categoryDto)));
    }

    public void delete(Long id) {
        log.debug("Request to delete Category : {}", id);
        this.categoryRepository.deleteById(id);
    }
}
