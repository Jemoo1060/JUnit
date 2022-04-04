package com.example.springcore_homework.repository;

import com.example.springcore_homework.models.Food;
import com.example.springcore_homework.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByRestaurant(Restaurant restaurant);
}
