package com.smartmanage.api.service.impl;

import com.smartmanage.api.dto.request.CategoryRequestDTO;
import com.smartmanage.api.dto.response.CategoryResponseDTO;
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
    public CategoryResponseDTO save(CategoryRequestDTO categoryRequestDTO) {
        Optional<Category> categoryOptional = this.categoryRepository.findByName(categoryRequestDTO.getName());
        if (categoryOptional.isPresent()) {
            throw new BusinessException("Uma categoria com esse nome já foi registrada.", HttpStatus.CONFLICT);
        }

        Category category = this.categoryRepository.save(this.mapper.map(categoryRequestDTO, Category.class));

        return CategoryResponseDTO.builder()
                .id(category.getId())
                .build();
    }

    @Override
    public List<CategoryResponseDTO> getCategories() {
        return this.mapper.map(this.categoryRepository.findAll(), new TypeToken<List<CategoryResponseDTO>>() {}.getType());
    }

    @Override
    public CategoryResponseDTO getCategoryById(Long id) {
        Optional<Category> category = this.categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new BusinessException("Categoria não encontrada.", HttpStatus.NOT_FOUND);
        }

        return this.mapper.map(category.get(), CategoryResponseDTO.class);
    }
}
