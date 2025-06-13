package com.code.smartmanage.controller;

import com.smartmanage.api.controller.CategoryController;
import com.smartmanage.api.dto.request.CategoryRequestDTO;
import com.smartmanage.api.dto.response.CategoryResponseDTO;
import com.smartmanage.api.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveCategory() {
        CategoryRequestDTO categoryRequestDTO = new CategoryRequestDTO();
        categoryRequestDTO.setName("name");
        categoryRequestDTO.setDescription("desc");

        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setId(1L);

        when(this.categoryService.save(any(CategoryRequestDTO.class))).thenReturn(categoryResponseDTO);

        ResponseEntity<CategoryResponseDTO> response = this.categoryController.saveCategory(categoryRequestDTO);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(categoryResponseDTO.getId(), response.getBody().getId());
    }

    @Test
    void getCategories() {
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setId(1L);
        categoryResponseDTO.setName("name");
        categoryResponseDTO.setDescription("desc");

        when(this.categoryService.getCategories()).thenReturn(List.of(categoryResponseDTO));

        ResponseEntity<List<CategoryResponseDTO>> response = this.categoryController.getCategories();

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getCategory() {
        Long categoryId = 1L;

        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setId(1L);
        categoryResponseDTO.setName("name");
        categoryResponseDTO.setDescription("desc");

        when(this.categoryService.getCategoryById(anyLong())).thenReturn(categoryResponseDTO);

        ResponseEntity<CategoryResponseDTO> response = this.categoryController.getCategory(categoryId);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categoryResponseDTO.getId(), response.getBody().getId());
        assertEquals(categoryResponseDTO.getName(), response.getBody().getName());
        assertEquals(categoryResponseDTO.getDescription(), response.getBody().getDescription());
    }
}
