package com.itechart.retailers.model.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UpdateUserStatusesReq {
    Set<Long> ids;
    boolean active;
}
