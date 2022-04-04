package com.example.springcore_homework.service;

import com.example.springcore_homework.dto.RestaurantDto;
import com.example.springcore_homework.dto.SpicyRestaurantDto;
import com.example.springcore_homework.models.Restaurant;
import com.example.springcore_homework.models.SpicyRestaurant;
import com.example.springcore_homework.repository.RestaurantRepository;
import com.example.springcore_homework.repository.SpicyRestaurantRepository;
import com.example.springcore_homework.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor // final로 선언된 멤버 변수를 자동으로 생성합니다.
@Service // 서비스임을 선언합니다.
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final SpicyRestaurantRepository spicyRestaurantRepository;

    public Restaurant registerRestaurant(RestaurantDto restaurantDto) {

        Validator.restaurantValidator(restaurantDto);

        Restaurant restaurant = new Restaurant(restaurantDto);
        restaurantRepository.save(restaurant);

        return restaurant;
    }

    public List<Restaurant> restaurants() {

        return  restaurantRepository.findAll();
    }


    public SpicyRestaurant spicyRegisterRestaurant(SpicyRestaurantDto spicyRestaurantDto) {

        Validator.spicyRestaurantValidator(spicyRestaurantDto);


        SpicyRestaurant spicyRestaurant = new SpicyRestaurant(spicyRestaurantDto);

        spicyRestaurantRepository.save(spicyRestaurant);

        return spicyRestaurant;
    }

    public List<SpicyRestaurant> spicyRestaurants(int x, int y) {

        List<SpicyRestaurant> list = new ArrayList<>();

        List<SpicyRestaurant> spicyRestaurantList = spicyRestaurantRepository.findAll();

        for(SpicyRestaurant spicyRestaurant : spicyRestaurantList){

            int distance = Math.abs(x-spicyRestaurant.getX()) + Math.abs(y-spicyRestaurant.getY());

            if(distance <= 3)
                list.add(spicyRestaurant);
        }

        return list;
    }
}












