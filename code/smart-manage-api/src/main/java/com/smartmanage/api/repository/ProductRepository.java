package com.smartmanage.api.repository;

import com.smartmanage.api.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("SELECT p FROM Product p "
            + "WHERE UPPER(p.name) LIKE CONCAT('%', UPPER(:search), '%') OR :search IS NULL "
            + "ORDER BY p.name")
    Page<Product> findByFilters(@Param("search") String search, Pageable pageable);

    Optional<Product> findById(@Param("id") UUID id);
}
