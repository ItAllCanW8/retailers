package com.itechart.retailers.model.payload.request;

import com.itechart.retailers.model.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileReq {
    private User user;
    private String currentPassword;
    private String newPassword;
}
