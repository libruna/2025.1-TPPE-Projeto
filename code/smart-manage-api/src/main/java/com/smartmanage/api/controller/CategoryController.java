package com.smartmanage.api.controller;

import com.smartmanage.api.dto.request.CategoryRequestDto;
import com.smartmanage.api.dto.response.CategoryResponseDto;
import com.smartmanage.api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    public CategoryService categoryService;

    @PostMapping()
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<CategoryResponseDto> saveCategory(@RequestBody CategoryRequestDto categoryRequestDTO) {
        return ResponseEntity.created(null).body(categoryService.saveCategory(categoryRequestDTO));
    }

    @GetMapping()
    public ResponseEntity<List<CategoryResponseDto>> getCategories() {
        return ResponseEntity.ok().body(categoryService.getCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategory(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(categoryService.getCategoryById(id));
    }
}
