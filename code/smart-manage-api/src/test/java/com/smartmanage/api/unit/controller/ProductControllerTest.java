package com.smartmanage.api.unit.controller;

import com.smartmanage.api.controller.ProductController;
import com.smartmanage.api.dto.request.ProductRequestDto;
import com.smartmanage.api.dto.response.ProductResponseDto;
import com.smartmanage.api.service.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.Random.class)
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest(name = "Should save product: {0}")
    @MethodSource("provideProductsForSave")
    void saveProduct(String name, String description, BigDecimal price, String barCode, Long stock, List<Long> categoryIds, UUID expectedId) {
        ProductRequestDto productRequest = new ProductRequestDto();
        productRequest.setName(name);
        productRequest.setDescription(description);
        productRequest.setPrice(price);
        productRequest.setBarCode(barCode);
        productRequest.setStock(stock);
        productRequest.setCategories(categoryIds);

        ProductResponseDto productResponse = new ProductResponseDto();
        productResponse.setId(expectedId);
        productResponse.setName(name);
        productResponse.setDescription(description);
        productResponse.setPrice(price);
        productResponse.setBarCode(barCode);
        productResponse.setStock(stock);

        when(productService.saveProduct(any(ProductRequestDto.class))).thenReturn(productResponse);

        ResponseEntity<ProductResponseDto> response = productController.saveProduct(productRequest);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedId, response.getBody().getId());
        assertEquals(name, response.getBody().getName());
        assertEquals(description, response.getBody().getDescription());
        assertEquals(price, response.getBody().getPrice());
        assertEquals(barCode, response.getBody().getBarCode());
        assertEquals(stock, response.getBody().getStock());
    }

    private static Stream<Arguments> provideProductsForSave() {
        return Stream.of(
                Arguments.of("Televisão", "Televisão grande", new BigDecimal(25000), "1111111111111", 10L, List.of(1L), UUID.fromString("11111111-1111-1111-1111-111111111111")),
                Arguments.of("Tablet", "Galaxy Tablet", new BigDecimal(3000), "2222222222222", 20L, List.of(2L), UUID.fromString("22222222-2222-2222-2222-222222222222"))
        );
    }

    @ParameterizedTest(name = "Should return product {1}")
    @MethodSource("provideProducts")
    void getProduct(UUID id, String name, String description, BigDecimal price, String barCode, Long stock) {
        ProductResponseDto productResponse = new ProductResponseDto();
        productResponse.setId(id);
        productResponse.setName(name);
        productResponse.setDescription(description);
        productResponse.setPrice(price);
        productResponse.setBarCode(barCode);
        productResponse.setStock(stock);

        when(productService.getProductById(id)).thenReturn(productResponse);

        ResponseEntity<ProductResponseDto> response = productController.getProduct(id);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(id, response.getBody().getId());
        assertEquals(name, response.getBody().getName());
        assertEquals(description, response.getBody().getDescription());
        assertEquals(price, response.getBody().getPrice());
        assertEquals(barCode, response.getBody().getBarCode());
        assertEquals(stock, response.getBody().getStock());
    }

    @ParameterizedTest(name = "Should return {2} product(s) for search: \"{0}\"")
    @MethodSource("provideProductPages")
    void getProducts(String search, List<ProductResponseDto> products, int expectedSize) {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductResponseDto> page = new PageImpl<>(products, pageable, products.size());

        when(productService.getProducts(eq(search), eq(pageable))).thenReturn(page);

        ResponseEntity<Page<ProductResponseDto>> response = productController.getProducts(search, 0, 10);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedSize, response.getBody().getContent().size());
    }

    private static Stream<Arguments> provideProducts() {
        return Stream.of(
                Arguments.of(
                        UUID.fromString("33333333-3333-3333-3333-333333333333"),
                        "Televisão",
                        "Televisão grande",
                        new BigDecimal("25000.00"),
                        "1357902468135",
                        12L
                ),
                Arguments.of(
                        UUID.fromString("44444444-4444-4444-4444-444444444444"),
                        "Impressora",
                        "Impressora",
                        new BigDecimal("850.00"),
                        "2468135790246",
                        20L
                )
        );
    }

    private static Stream<Arguments> provideProductPages() {
        ProductResponseDto product1 = new ProductResponseDto(
                UUID.randomUUID(), "Notebook", "Dell i5", new BigDecimal(4000), "1111111111111", 10L);

        ProductResponseDto product2 = new ProductResponseDto(
                UUID.randomUUID(), "Tablet", "Galaxy Tablet", new BigDecimal(2000), "2222222222222", 20L);

        return Stream.of(
                Arguments.of("Notebook", List.of(product1), 1),
                Arguments.of("Tablet", List.of(product2), 1),
                Arguments.of("", List.of(), 0)
        );
    }
}
