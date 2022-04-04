package com.example.springcore_homework.controller;

import com.example.springcore_homework.dto.FoodDto;
import com.example.springcore_homework.models.Food;
import com.example.springcore_homework.models.UserRoleEnum;
import com.example.springcore_homework.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor // final로 선언된 멤버 변수를 자동으로 생성합니다.
@Controller
public class FoodController {

    private final FoodService foodService;


    @PostMapping("/restaurant/{id}/food/register")
    @ResponseBody
    public void registerFood(@PathVariable Long id, @RequestBody List<FoodDto> foodDtoList){

        foodService.registerFood(id, foodDtoList);
    }

    @GetMapping("/restaurant/{id}/foods")
    @ResponseBody
    public List<Food> FoodMenu(@PathVariable Long id){

       return foodService.FoodMenu(id);
    }


    @Secured(value = UserRoleEnum.Authority.ADMIN)
    @PostMapping("/spicy/restaurant/{id}/food/register")
    @ResponseBody
    public void spicyRegisterFood(@PathVariable Long id, @RequestBody List<FoodDto> foodDtoList){

        foodService.spicyRegisterFood(id, foodDtoList);
    }


}
