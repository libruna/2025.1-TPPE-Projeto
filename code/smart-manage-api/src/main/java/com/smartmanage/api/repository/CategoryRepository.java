package com.smartmanage.api.repository;

import com.smartmanage.api.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

    @Query("SELECT c FROM Category c WHERE c.id IN :ids")
    Set<Category> findAllById(List<Long> ids);
}
