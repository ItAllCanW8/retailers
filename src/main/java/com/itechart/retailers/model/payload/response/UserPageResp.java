package com.itechart.retailers.model.payload.response;

import com.itechart.retailers.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserPageResp {
    private List<User> users;
    private Integer totalPages;
}
