package com.itechart.retailers.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.itechart.retailers.model.entity.Role;
import com.itechart.retailers.model.entity.User;
import com.itechart.retailers.model.payload.request.LogInRequest;
import com.itechart.retailers.model.payload.request.SignUpRequest;
import com.itechart.retailers.model.payload.response.MessageResponse;
import com.itechart.retailers.model.payload.response.UserAuthenticationResponse;
import com.itechart.retailers.repository.RoleRepository;
import com.itechart.retailers.repository.UserRepository;
import com.itechart.retailers.security.model.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Date;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthorizationController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LogInRequest requestDto) {
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword()));
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        UserDetailsImpl authenticatedUser = (UserDetailsImpl) authentication.getPrincipal();

        String accessToken = JWT.create()
                .withSubject(authenticatedUser.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .withIssuer(ServletUriComponentsBuilder.fromCurrentRequest().toUriString())
                .withClaim("email", authenticatedUser.getEmail())
                .withClaim("role", authenticatedUser.getAuthorities().stream().map(SimpleGrantedAuthority::getAuthority).toList())
                .withClaim("name", authenticatedUser.getUsername())
                .sign(Algorithm.HMAC256("secret"));

        return ResponseEntity.ok(new UserAuthenticationResponse(accessToken));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        Role role = roleRepository.save(Role.builder()
                .role(signUpRequest.getRole())
                .build());
        User user = User.builder()
                .name(signUpRequest.getName())
                .email(signUpRequest.getEmail())
                .role(role)
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .isActive(true)
                .build();
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
