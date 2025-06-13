package com.code.smartmanage.service;

import com.smartmanage.api.dto.request.CategoryRequestDTO;
import com.smartmanage.api.dto.response.CategoryResponseDTO;
import com.smartmanage.api.exception.BusinessException;
import com.smartmanage.api.model.entity.Category;
import com.smartmanage.api.repository.CategoryRepository;
import com.smartmanage.api.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save() {
        CategoryRequestDTO categoryRequestDTO = new CategoryRequestDTO();
        categoryRequestDTO.setName("name");
        categoryRequestDTO.setDescription("desc");

        Category categoryEntity = new Category();
        categoryEntity.setId(1L);
        categoryEntity.setName("name");
        categoryEntity.setDescription("desc");

        when(this.categoryRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(this.categoryRepository.save(any(Category.class))).thenReturn(categoryEntity);

        Long categoryId = this.categoryService.save(categoryRequestDTO).getId();

        assertNotNull(categoryId);
        assertEquals(categoryEntity.getId(), categoryId);

        verify(categoryRepository, times(1)).findByName(anyString());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void save_WhenCategoryAlreadyExists() {
        CategoryRequestDTO categoryRequestDTO = new CategoryRequestDTO();
        categoryRequestDTO.setName("name");
        categoryRequestDTO.setDescription("desc");

        Category categoryEntity = new Category();
        categoryEntity.setId(1L);
        categoryEntity.setName("name");
        categoryEntity.setDescription("desc");

        when(this.categoryRepository.findByName(anyString())).thenReturn(Optional.of(categoryEntity));

        assertThrows(BusinessException.class, () -> {
            categoryService.save(categoryRequestDTO);
        });

        verify(categoryRepository, times(1)).findByName(anyString());
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void getCategories() {
        Category categoryEntity = new Category();
        categoryEntity.setId(1L);
        categoryEntity.setName("name");
        categoryEntity.setDescription("desc");

        when(this.categoryRepository.findAll()).thenReturn(List.of(categoryEntity));

        List<CategoryResponseDTO> categories = this.categoryService.getCategories();

        assertNotNull(categories);
        assertEquals(1, categories.size());

        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void getCategoryById() {
        Long categoryId = 1L;

        Category categoryEntity = new Category();
        categoryEntity.setId(1L);
        categoryEntity.setName("name");
        categoryEntity.setDescription("desc");

        when(this.categoryRepository.findById(anyLong())).thenReturn(Optional.of(categoryEntity));

        CategoryResponseDTO category = this.categoryService.getCategoryById(categoryId);

        assertNotNull(category);
        assertEquals(categoryId, category.getId());

        verify(categoryRepository, times(1)).findById(anyLong());
    }

    @Test
    void getCategoryById_WhenCategoryNotFound() {
        Long categoryId = 1L;

        when(this.categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> {
            categoryService.getCategoryById(categoryId);
        });

        verify(categoryRepository, times(1)).findById(anyLong());
    }
}
