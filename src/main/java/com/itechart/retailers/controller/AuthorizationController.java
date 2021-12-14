package com.itechart.retailers.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.itechart.retailers.model.entity.User;
import com.itechart.retailers.model.payload.request.LogInRequest;
import com.itechart.retailers.model.payload.request.SignUpRequest;
import com.itechart.retailers.model.payload.response.MessageResponse;
import com.itechart.retailers.model.payload.response.UserAuthenticationResponse;
import com.itechart.retailers.repository.UserRepository;
import com.itechart.retailers.security.model.UserDetailsImpl;
import com.itechart.retailers.service.RoleService;
import com.itechart.retailers.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<?> authenticate(@RequestBody LogInRequest requestDto) {
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Incorrect email or password!"));
        } catch (LockedException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Account was disabled. Please, contact your administrator."));
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

        return ResponseEntity.ok(new UserAuthenticationResponse(accessToken));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already taken!"));
        }
        User user = User.builder()
                .name(signUpRequest.getName())
                .email(signUpRequest.getEmail())
                .role(roleService.save(signUpRequest.getRole()))
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .isActive(true)
                .build();
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("Success! Now you can log in to the system"));
    }
}
