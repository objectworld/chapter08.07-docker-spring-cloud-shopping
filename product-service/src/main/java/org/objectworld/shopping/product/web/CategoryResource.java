package org.objectworld.shopping.product.web;

import org.objectworld.shopping.common.dto.CategoryDto;
import org.objectworld.shopping.product.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import static org.objectworld.shopping.common.util.RestBase.BASE_URL;

import java.util.List;

@RestController
@RequestMapping(BASE_URL + "/categories")
public class CategoryResource {

    private final CategoryService categoryService;

    public CategoryResource(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryDto> findAll() {
        return this.categoryService.findAll();
    }

    @GetMapping("/{id}")
    public CategoryDto findById(@PathVariable Long id) {
        return this.categoryService.findById(id);
    }

    @PostMapping
    public CategoryDto create(CategoryDto categoryDto) {
        return this.categoryService.create(categoryDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.categoryService.delete(id);
    }
}
