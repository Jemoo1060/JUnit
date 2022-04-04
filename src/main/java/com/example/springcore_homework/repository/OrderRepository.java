package com.example.springcore_homework.repository;


import com.example.springcore_homework.models.OrderTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderTable, Long> {
}
