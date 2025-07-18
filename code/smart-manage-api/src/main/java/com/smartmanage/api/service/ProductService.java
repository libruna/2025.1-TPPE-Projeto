package com.smartmanage.api.service;

import com.smartmanage.api.dto.request.ProductRequestDto;
import com.smartmanage.api.dto.response.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductService {

    ProductResponseDto saveProduct(ProductRequestDto productRequestDto);
    Page<ProductResponseDto> getProducts(String search, Pageable pageable);
    ProductResponseDto getProductById(UUID id);
}
