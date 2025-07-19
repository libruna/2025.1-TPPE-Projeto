package com.smartmanage.api.unit.controller;

import com.smartmanage.api.controller.CategoryController;
import com.smartmanage.api.dto.request.CategoryRequestDto;
import com.smartmanage.api.dto.response.CategoryResponseDto;
import com.smartmanage.api.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.Random.class)
public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest(name = "Should save category: {0}")
    @MethodSource("provideCategoriesForSave")
    void saveCategory(String name, String description, Long expectedId) {
        CategoryRequestDto request = new CategoryRequestDto();
        request.setName(name);
        request.setDescription(description);

        CategoryResponseDto responseDto = new CategoryResponseDto();
        responseDto.setId(expectedId);
        responseDto.setName(name);
        responseDto.setDescription(description);

        when(categoryService.saveCategory(any(CategoryRequestDto.class))).thenReturn(responseDto);

        ResponseEntity<CategoryResponseDto> response = categoryController.saveCategory(request);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedId, response.getBody().getId());
        assertEquals(name, response.getBody().getName());
        assertEquals(description, response.getBody().getDescription());
    }

    private static Stream<Arguments> provideCategoriesForSave() {
        return Stream.of(
                Arguments.of("Brinquedos", "Jogos e diversão", 1L),
                Arguments.of("Livros", "Leitura geral", 2L)
        );
    }

    @ParameterizedTest(name = "Should return category {1}")
    @CsvSource({
            "1, Brinquedos, Jogos e diversão",
            "2, Livros, Leitura geral"
    })
    void getCategory(Long id, String name, String description) {
        CategoryResponseDto responseDto = new CategoryResponseDto();
        responseDto.setId(id);
        responseDto.setName(name);
        responseDto.setDescription(description);

        when(categoryService.getCategoryById(id)).thenReturn(responseDto);

        ResponseEntity<CategoryResponseDto> response = categoryController.getCategory(id);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(id, response.getBody().getId());
        assertEquals(name, response.getBody().getName());
        assertEquals(description, response.getBody().getDescription());
    }

    @ParameterizedTest(name = "Should return list with {0} and {2}")
    @CsvSource({
            "1, Brinquedos, 2, Livros"
    })
    void getCategories(Long id1, String name1, Long id2, String name2) {
        CategoryResponseDto cat1 = new CategoryResponseDto(id1, name1, "Jogos e diversão");
        CategoryResponseDto cat2 = new CategoryResponseDto(id2, name2, "Leitura geral");

        when(categoryService.getCategories()).thenReturn(List.of(cat1, cat2));

        ResponseEntity<List<CategoryResponseDto>> response = categoryController.getCategories();

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(name1, response.getBody().get(0).getName());
        assertEquals(name2, response.getBody().get(1).getName());
    }
}