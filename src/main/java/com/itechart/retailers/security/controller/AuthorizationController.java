package com.itechart.retailers.security.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.itechart.retailers.model.entity.User;
import com.itechart.retailers.model.payload.request.LogInReq;
import com.itechart.retailers.model.payload.request.SignUpReq;
import com.itechart.retailers.model.payload.response.MessageResp;
import com.itechart.retailers.model.payload.response.UserAuthenticationResp;
import com.itechart.retailers.repository.UserRepository;
import com.itechart.retailers.security.model.UserDetailsImpl;
import com.itechart.retailers.service.RoleService;
import lombok.RequiredArgsConstructor;
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
    private final RoleService roleService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LogInReq requestDto) {
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword()));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(new MessageResp("Incorrect email or password!"));
        }

        UserDetailsImpl authenticatedUser = (UserDetailsImpl) authentication.getPrincipal();

        String accessToken = JWT.create()
                .withSubject(authenticatedUser.getEmail())
                .withExpiresAt(new Date(Long.MAX_VALUE))
//                .withExpiresAt(new Date(System.currentTimeMillis() + 20 * 24 * 60 * 60 * 1000))
                .withIssuer(ServletUriComponentsBuilder.fromCurrentRequest().toUriString())
                .withClaim("email", authenticatedUser.getEmail())
                .withClaim("role", authenticatedUser.getAuthorities().stream().map(SimpleGrantedAuthority::getAuthority).toList())
                .withClaim("name", authenticatedUser.getUsername())
                .sign(Algorithm.HMAC256("secret"));

        return ResponseEntity.ok(new UserAuthenticationResp(accessToken));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpReq signUpReq) {
        if (userRepository.existsByEmail(signUpReq.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResp("Error: Email is already taken!"));
        }
        User user = User.builder()
                .name(signUpReq.getName())
                .email(signUpReq.getEmail())
                .role(roleService.save(signUpReq.getRole()))
                .password(passwordEncoder.encode(signUpReq.getPassword()))
                .isActive(true)
                .build();
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResp("Success! Now you can log in to the system"));
    }
}
