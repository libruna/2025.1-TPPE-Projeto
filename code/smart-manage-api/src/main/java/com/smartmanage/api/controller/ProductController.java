package com.smartmanage.api.controller;

import com.smartmanage.api.dto.request.ProductRequestDto;
import com.smartmanage.api.dto.response.ProductResponseDto;
import com.smartmanage.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    public ProductService productService;

    @PostMapping()
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ProductResponseDto> saveProduct(@RequestBody ProductRequestDto productRequestDto) {
        return ResponseEntity.created(null).body(productService.saveProduct(productRequestDto));
    }

    @GetMapping()
    public ResponseEntity<Page<ProductResponseDto>> getProducts(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok().body(productService.getProducts(search, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable("id") UUID id) {
        return ResponseEntity.ok().body(productService.getProductById(id));
    }
}
