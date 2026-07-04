package com.ecommerce.controller;

import com.ecommerce.dto.AuthResponse;
import com.ecommerce.dto.LoginRequest;
import com.ecommerce.dto.PasswordResetRequest;
import com.ecommerce.dto.RegisterRequest;
import com.ecommerce.entity.Role;
import com.ecommerce.entity.User;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role userRole = roleRepository.findByName("USER").orElseGet(() -> {
            Role role = new Role();
            role.setName("USER");
            return roleRepository.save(role);
        });
        user.setRoles(Set.of(userRole));

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtService.generateToken(new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new org.springframework.security.core.authority.SimpleGrantedAuthority(
                                "ROLE_" + role.getName()))
                        .toList()));
        return ResponseEntity.ok(new AuthResponse(token, user.getEmail(), user.getName(),
                user.getRoles().stream().findFirst().orElseThrow().getName()));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        return ResponseEntity.ok("Password reset link sent to " + email);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("Password reset successfully");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Logged out successfully");
    }
}
