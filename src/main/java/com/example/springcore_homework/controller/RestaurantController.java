package com.example.springcore_homework.controller;

import com.example.springcore_homework.dto.RestaurantDto;
import com.example.springcore_homework.dto.SpicyRestaurantDto;
import com.example.springcore_homework.models.Restaurant;
import com.example.springcore_homework.models.SpicyRestaurant;
import com.example.springcore_homework.models.UserRoleEnum;
import com.example.springcore_homework.security.UserDetailsImpl;
import com.example.springcore_homework.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor // final로 선언된 멤버 변수를 자동으로 생성합니다.
@Controller
public class RestaurantController {

    private  final RestaurantService restaurantService;


    @PostMapping("/restaurant/register")
    @ResponseBody
    public Restaurant registerRestaurant(@RequestBody RestaurantDto restaurantDto) {



        return restaurantService.registerRestaurant(restaurantDto);
    }

    @GetMapping("/restaurants")
    @ResponseBody
    public List<Restaurant> restaurants(){

        return restaurantService.restaurants();
    }

    // (관리자용)
    @Secured(value = UserRoleEnum.Authority.ADMIN)
    @PostMapping("/spicy/restaurant/register")
    @ResponseBody
    public SpicyRestaurant spicyRegisterRestaurant(@RequestBody SpicyRestaurantDto spicyRestaurantDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {


        return restaurantService.spicyRegisterRestaurant(spicyRestaurantDto);
    }

    @Secured(value = UserRoleEnum.Authority.USER)
    @GetMapping("/spicy/restaurants")
    @ResponseBody
    public List<SpicyRestaurant> spicyRestaurants(@RequestParam int x,@RequestParam int y){

        return restaurantService.spicyRestaurants(x,y);


    }

}
