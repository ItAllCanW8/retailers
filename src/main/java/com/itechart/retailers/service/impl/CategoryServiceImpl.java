package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.entity.Category;
import com.itechart.retailers.repository.CategoryRepository;
import com.itechart.retailers.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;

	@Override
	public Category saveIfNotExists(Category category) {
		if (categoryRepository.findByName(category.getName()).isPresent()) {
			return categoryRepository.findByName(category.getName()).get();
		} else {
			return categoryRepository.save(category);
		}
	}

}
