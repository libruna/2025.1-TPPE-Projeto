package com.smartmanage.api.service;

import com.smartmanage.api.dto.request.CategoryRequestDto;
import com.smartmanage.api.dto.response.CategoryResponseDto;

import java.util.List;

public interface CategoryService {

    CategoryResponseDto saveCategory(CategoryRequestDto categoryRequestDTO);
    List<CategoryResponseDto> getCategories();
    CategoryResponseDto getCategoryById(Long id);
}
