package com.itechart.retailers.controller;

import com.itechart.retailers.model.entity.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dispatcher")
public class DispatcherController {

	public void registerItems(List<Item> items){

	}


}
