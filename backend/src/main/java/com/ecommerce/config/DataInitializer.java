package com.ecommerce.config;

import com.ecommerce.entity.Brand;
import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.Role;
import com.ecommerce.entity.User;
import com.ecommerce.repository.BrandRepository;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        Role adminRole = roleRepository.findByName("ADMIN").orElseGet(() -> {
            Role role = new Role();
            role.setName("ADMIN");
            return roleRepository.save(role);
        });

        Role userRole = roleRepository.findByName("USER").orElseGet(() -> {
            Role role = new Role();
            role.setName("USER");
            return roleRepository.save(role);
        });

        if (!userRepository.existsByEmail("admin@novacart.com")) {
            User admin = new User();
            admin.setName("Admin User");
            admin.setEmail("admin@novacart.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRoles(Set.of(adminRole, userRole));
            userRepository.save(admin);
        }

        if (categoryRepository.count() == 0) {
            Category electronics = new Category();
            electronics.setName("Electronics");
            electronics.setDescription("Latest devices");
            categoryRepository.save(electronics);

            Category fashion = new Category();
            fashion.setName("Fashion");
            fashion.setDescription("Modern apparel");
            categoryRepository.save(fashion);
        }

        if (brandRepository.count() == 0) {
            Brand apple = new Brand();
            apple.setName("Apple");
            apple.setDescription("Premium devices");
            brandRepository.save(apple);

            Brand nike = new Brand();
            nike.setName("Nike");
            nike.setDescription("Performance wear");
            brandRepository.save(nike);
        }

        if (productRepository.count() == 0) {
            Category category = categoryRepository.findAll().get(0);
            Brand brand = brandRepository.findAll().get(0);

            Product product = new Product();
            product.setName("Nova Smart Watch");
            product.setDescription("A sleek smartwatch with a vivid display and health tracking.");
            product.setPrice(BigDecimal.valueOf(199));
            product.setDiscountPrice(BigDecimal.valueOf(149));
            product.setRating(4.8);
            product.setStock(24);
            product.setSku("NW-1001");
            product.setStatus("ACTIVE");
            product.setCategory(category);
            product.setBrand(brand);
            productRepository.save(product);
        }
    }
}
