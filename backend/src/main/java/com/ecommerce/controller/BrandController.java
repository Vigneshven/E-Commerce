package com.ecommerce.controller;

import com.ecommerce.entity.Brand;
import com.ecommerce.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {
    private final BrandRepository brandRepository;

    @GetMapping
    public List<Brand> getAll() {
        return brandRepository.findAll();
    }

    @PostMapping
    public Brand create(@RequestBody Brand brand) {
        return brandRepository.save(brand);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Brand> update(@PathVariable Long id, @RequestBody Brand brandDetails) {
        return brandRepository.findById(id).map(brand -> {
            brand.setName(brandDetails.getName());
            brand.setDescription(brandDetails.getDescription());
            return ResponseEntity.ok(brandRepository.save(brand));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!brandRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        brandRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
