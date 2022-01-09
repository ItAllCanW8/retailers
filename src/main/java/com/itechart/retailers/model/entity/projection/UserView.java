package com.itechart.retailers.model.entity.projection;

import com.itechart.retailers.model.entity.Location;
import com.itechart.retailers.model.entity.Role;

import java.time.LocalDate;

public interface UserView {

    Long getId();

    String getEmail();

    String getName();

    String getSurname();

    LocalDate getBirthday();

    Location getLocation();

    Boolean getIsActive();

    Role getRole();
}
