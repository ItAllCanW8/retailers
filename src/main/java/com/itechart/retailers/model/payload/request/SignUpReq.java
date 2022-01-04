package com.itechart.retailers.model.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpReq {
    private String name;
    private String email;
    private String role;
    private String password;
}
