package com.example.springcore_homework.service;

import com.example.springcore_homework.dto.FoodDto;
import com.example.springcore_homework.models.Food;
import com.example.springcore_homework.models.Restaurant;
import com.example.springcore_homework.models.SpicyFood;
import com.example.springcore_homework.models.SpicyRestaurant;
import com.example.springcore_homework.repository.FoodRepository;
import com.example.springcore_homework.repository.RestaurantRepository;
import com.example.springcore_homework.repository.SpicyFoodRepository;
import com.example.springcore_homework.repository.SpicyRestaurantRepository;
import com.example.springcore_homework.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor // final로 선언된 멤버 변수를 자동으로 생성합니다.
@Service // 서비스임을 선언합니다.
public class FoodService {

    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;
    private final SpicyRestaurantRepository spicyRestaurantRepository;
    private final SpicyFoodRepository spicyFoodRepository;

    public void registerFood(Long id, List<FoodDto> foodDtoList) {

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("해당 가게가 존재하지 않습니다."));

        List<Food> foodMenu = foodRepository.findByRestaurant(restaurant);

        Validator.registFoodOverlapValidator(foodDtoList);
        Validator.foodValidator(foodDtoList);
        Validator.foodOverlapValidator(foodMenu,foodDtoList);

        for(FoodDto foodDto : foodDtoList){
                Food food = new Food(foodDto,restaurant);
                foodRepository.save(food);
        }

    }

    public List<Food> FoodMenu(Long id) {

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("해당 가게가 존재하지 않습니다."));

        return foodRepository.findByRestaurant(restaurant);
    }

    public void spicyRegisterFood(Long id, List<FoodDto> foodDtoList) {

        SpicyRestaurant spicyRestaurant = spicyRestaurantRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("해당 가게가 존재하지 않습니다."));

        List<SpicyFood> foodMenu = spicyFoodRepository.findBySpicyRestaurant(spicyRestaurant);

        Validator.registFoodOverlapValidator(foodDtoList);
        Validator.foodValidator(foodDtoList);
        Validator.spicyFoodOverlapValidator(foodMenu,foodDtoList);

        for(FoodDto foodDto : foodDtoList){
            SpicyFood food = new SpicyFood(foodDto,spicyRestaurant);
            spicyFoodRepository.save(food);
        }
    }
}
