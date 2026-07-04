package com.ecommerce.controller;

import com.ecommerce.entity.Category;
import com.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryRepository categoryRepository;

    @GetMapping
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @PostMapping
    public Category create(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody Category categoryDetails) {
        return categoryRepository.findById(id).map(category -> {
            category.setName(categoryDetails.getName());
            category.setDescription(categoryDetails.getDescription());
            return ResponseEntity.ok(categoryRepository.save(category));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!categoryRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
