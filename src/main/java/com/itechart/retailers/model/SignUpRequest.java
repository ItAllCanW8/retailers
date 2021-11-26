package com.itechart.retailers.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SignUpRequest {

    private String name;

    private String email;

    private String role;

    private String password;

}
