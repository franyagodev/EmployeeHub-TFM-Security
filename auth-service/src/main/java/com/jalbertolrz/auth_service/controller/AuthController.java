package com.jalbertolrz.auth_service.controller;

import com.jalbertolrz.auth_service.model.Role;
import com.jalbertolrz.auth_service.model.User;
import com.jalbertolrz.auth_service.repository.UserRepository;
import com.jalbertolrz.auth_service.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) {
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            // Por defecto USER
            user.setRoles(Collections.singletonList(Role.USER));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(new AuthResponse("User registered successfully", null, null));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody User loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body(new AuthResponse("Invalid credentials", null, null));
        }

        List<Role> roles = user.getRoles();
        String accessToken = jwtService.generateToken(user.getUsername(), roles);
        String refreshToken = jwtService.generateRefreshToken(user.getUsername());

        return ResponseEntity.ok(new AuthResponse("Login successful", accessToken, refreshToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refresh_token");
        if (!jwtService.validateToken(refreshToken)) {
            return ResponseEntity.status(401).body(new AuthResponse("Invalid refresh token", null, null));
        }
        String username = jwtService.extractUsername(refreshToken);
        // Aquí no tenemos roles en el refresh, normalmente se vuelven a cargar desde DB si es necesario
        // Para simplificar usamos USER por defecto
        String newAccessToken = jwtService.generateToken(username, Collections.singletonList(Role.USER));
        return ResponseEntity.ok(new AuthResponse("Token refreshed", newAccessToken, refreshToken));
    }

    // DTO
    public record AuthResponse(String message, String accessToken, String refreshToken) {}
}
