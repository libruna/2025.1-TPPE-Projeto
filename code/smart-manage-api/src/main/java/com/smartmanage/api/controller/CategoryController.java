package com.smartmanage.api.controller;

import com.smartmanage.api.dto.request.CategoryRequestDTO;
import com.smartmanage.api.dto.response.CategoryResponseDTO;
import com.smartmanage.api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    public CategoryService categoryService;

    @PostMapping()
    public ResponseEntity<CategoryResponseDTO> saveCategory(@RequestBody CategoryRequestDTO categoryRequestDTO) {
        return ResponseEntity.created(null).body(this.categoryService.save(categoryRequestDTO));
    }

    @GetMapping()
    public ResponseEntity<List<CategoryResponseDTO>> getCategories() {
        return ResponseEntity.ok().body(this.categoryService.getCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategory(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(this.categoryService.getCategoryById(id));
    }
}
