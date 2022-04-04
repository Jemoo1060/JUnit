package com.example.springcore_homework.repository;

import com.example.springcore_homework.models.SpicyRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpicyRestaurantRepository extends JpaRepository<SpicyRestaurant, Long> {
}
