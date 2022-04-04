package com.example.springcore_homework.repository;

import com.example.springcore_homework.models.SpicyFood;
import com.example.springcore_homework.models.SpicyRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpicyFoodRepository extends JpaRepository<SpicyFood, Long> {
    List<SpicyFood> findBySpicyRestaurant(SpicyRestaurant spicyRestaurant);
}
