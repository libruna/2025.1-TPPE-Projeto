package com.smartmanage.api.service;

import com.smartmanage.api.dto.request.CategoryRequestDTO;
import com.smartmanage.api.dto.response.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {

    CategoryResponseDTO save(CategoryRequestDTO categoryRequestDTO);
    List<CategoryResponseDTO> getCategories();
    CategoryResponseDTO getCategoryById(Long id);
}
