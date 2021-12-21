package com.itechart.retailers.model.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogInReq {

    private String email;
    private String password;
}
