package com.example.springcore_homework.repository;

import com.example.springcore_homework.models.OrderRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRequestRepository extends JpaRepository<OrderRequest, Long> {
}
