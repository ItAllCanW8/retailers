package com.itechart.retailers.security.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.itechart.retailers.model.payload.request.LogInReq;
import com.itechart.retailers.model.payload.response.MessageResp;
import com.itechart.retailers.model.payload.response.UserAuthenticationResp;
import com.itechart.retailers.security.model.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Date;

import static com.itechart.retailers.controller.constant.Message.INCORRECT_EMAIL_OR_PASSWORD_MSG;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthorizationController {

    private static final String LOGIN_POST_MAPPING = "/login";
    private static final String EMAIL_CLAIM = "email";
    private static final String ROLE_CLAIM = "role";
    private static final String NAME_CLAIM = "name";
    private static final String SECRET_KEY = "secret";

    private final AuthenticationManager authenticationManager;

    @PostMapping(LOGIN_POST_MAPPING)
    public ResponseEntity<?> authenticate(@RequestBody LogInReq requestDto) {
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword()));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(new MessageResp(INCORRECT_EMAIL_OR_PASSWORD_MSG));
        }

        UserDetailsImpl authenticatedUser = (UserDetailsImpl) authentication.getPrincipal();
        String accessToken = JWT.create()
                .withSubject(authenticatedUser.getEmail())
                .withExpiresAt(new Date(Long.MAX_VALUE))
                .withIssuer(ServletUriComponentsBuilder.fromCurrentRequest().toUriString())
                .withClaim(EMAIL_CLAIM, authenticatedUser.getEmail())
                .withClaim(ROLE_CLAIM, authenticatedUser.getAuthorities().stream().map(SimpleGrantedAuthority::getAuthority).toList())
                .withClaim(NAME_CLAIM, authenticatedUser.getUsername())
                .sign(Algorithm.HMAC256(SECRET_KEY));

        return ResponseEntity.ok(new UserAuthenticationResp(accessToken));
    }
}
