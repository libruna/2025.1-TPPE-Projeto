package com.smartmanage.api.repository;

import com.smartmanage.api.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findAllByClientId(UUID clientId);
}
