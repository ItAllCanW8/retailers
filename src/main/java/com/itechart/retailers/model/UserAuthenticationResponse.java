package com.itechart.retailers.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserAuthenticationResponse {
    private String jwt;
}
