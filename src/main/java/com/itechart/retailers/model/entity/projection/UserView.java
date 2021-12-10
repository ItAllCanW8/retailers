package com.itechart.retailers.model.entity.projection;

import com.itechart.retailers.model.entity.Role;

import java.time.LocalDate;

public interface UserView {
    Long getId();
    String getName();
    String getSurname();
    LocalDate getBirthday();
    Boolean getIsActive();
    Role getRole();
}
