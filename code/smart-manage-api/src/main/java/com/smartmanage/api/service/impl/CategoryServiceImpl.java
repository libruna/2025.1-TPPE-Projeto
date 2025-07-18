package com.smartmanage.api.service.impl;

import com.smartmanage.api.dto.request.CategoryRequestDto;
import com.smartmanage.api.dto.response.CategoryResponseDto;
import com.smartmanage.api.exception.BusinessException;
import com.smartmanage.api.model.entity.Category;
import com.smartmanage.api.repository.CategoryRepository;
import com.smartmanage.api.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final ModelMapper mapper;
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.mapper = new ModelMapper();
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryResponseDto saveCategory(CategoryRequestDto categoryRequestDto) {
        categoryRepository.findByName(categoryRequestDto.getName())
                .ifPresent(c -> {
                    throw new BusinessException("Uma categoria com mesmo nome já existe.", HttpStatus.CONFLICT);
                });

        return CategoryResponseDto.builder()
                .id(categoryRepository.save(mapper.map(categoryRequestDto, Category.class)).getId())
                .build();
    }

    @Override
    public List<CategoryResponseDto> getCategories() {
        return mapper.map(categoryRepository.findAll(), new TypeToken<List<CategoryResponseDto>>() {}.getType());
    }

    @Override
    public CategoryResponseDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Categoria não encontrada.", HttpStatus.NOT_FOUND));

        return mapper.map(category, CategoryResponseDto.class);
    }
}
