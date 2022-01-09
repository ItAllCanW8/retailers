package com.itechart.retailers.model.payload.response;

import com.itechart.retailers.model.entity.Application;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ApplicationPageResp {
    private List<Application> applications;
    private Integer totalPages;
}
