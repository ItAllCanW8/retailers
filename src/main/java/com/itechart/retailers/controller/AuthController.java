package com.itechart.retailers.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.itechart.retailers.model.UsedRequestDto;
import com.itechart.retailers.model.UserAuthDetails;
import com.itechart.retailers.model.UserAuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Date;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UsedRequestDto requestDto) {
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword()));
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        UserAuthDetails authenticatedUser = (UserAuthDetails)authentication.getPrincipal();

        String accessToken = JWT.create()
                .withSubject(authenticatedUser.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .withIssuer(ServletUriComponentsBuilder.fromCurrentRequest().toUriString())
                .withClaim("email", authenticatedUser.getEmail())
                .withClaim("role", authenticatedUser.getAuthorities().stream().map(s->s.getAuthority()).toList())
                .sign(Algorithm.HMAC256("secret"));

        return ResponseEntity.ok(new UserAuthenticationResponse(accessToken));

    }

    @PostMapping("/logout")
    public void logout() {

    }
}
