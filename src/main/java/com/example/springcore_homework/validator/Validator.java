package com.example.springcore_homework.validator;

import com.example.springcore_homework.dto.FoodDto;
import com.example.springcore_homework.dto.FoodOrderRequestDto;
import com.example.springcore_homework.dto.RestaurantDto;
import com.example.springcore_homework.dto.SpicyRestaurantDto;
import com.example.springcore_homework.models.Food;
import com.example.springcore_homework.models.SpicyFood;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Validator {

    public static final int MIN_ORDER_PRICE = 1000;
    public static final int MAX_ORDER_PRICE = 100000;
    public static final int ORDER_PRICE_UNIT = 100;

    public static final int MIN_DELIVERY_FEE = 0;
    public static final int MAX_DELIVERY_FEE = 10000;
    public static final int DELIVERY_FEE_UNIT = 500;

    public static final int MIN_FOOD_PRICE = 100;
    public static final int MAX_FOOD_PRICE = 1000000;
    public static final int FOOD_PRICE_UNIT = 100;

    public static final int MIN_ORDER_FOOD = 1;
    public static final int MAX_ORDER_FOOD = 100;



    public static void restaurantValidator(RestaurantDto restaurantDto){

        if (restaurantDto.getMinOrderPrice() < MIN_ORDER_PRICE || restaurantDto.getMinOrderPrice() > MAX_ORDER_PRICE) {
            throw new IllegalArgumentException("허용값: 1,000원 ~ 100,000원 입력");
        }

        if (restaurantDto.getMinOrderPrice() % ORDER_PRICE_UNIT != 0) {
            throw new IllegalArgumentException("100 원 단위로만 입력 가능 (예. 2,220원 입력 시 에러발생. 2,300원은 입력 가능)");
        }

        if (restaurantDto.getDeliveryFee() < MIN_DELIVERY_FEE || restaurantDto.getDeliveryFee() > MAX_DELIVERY_FEE) {
            throw new IllegalArgumentException("허용값: 0원 ~ 10,000원");
        }

        if (restaurantDto.getDeliveryFee() % DELIVERY_FEE_UNIT != 0) {
            throw new IllegalArgumentException("500 원 단위로만 입력 가능 (예. 2,200원 입력 시 에러발생. 2,500원 입력 가능)");
        }
    }

    public static void foodValidator(List<FoodDto> foodList){

        for(FoodDto food : foodList){

            if (food.getPrice() < MIN_FOOD_PRICE || food.getPrice() > MAX_FOOD_PRICE) {
                throw new IllegalArgumentException("허용값: 100원 ~ 1,000,000원 입력");
            }

            if (food.getPrice() % FOOD_PRICE_UNIT != 0) {
                throw new IllegalArgumentException("100 원 단위로만 입력 가능 (예. 2,220원 입력 시 에러발생. 2,300원 입력 가능)");
            }

        }


    }

    public static void foodOverlapValidator(List<Food> foodMenu,  List<FoodDto> foodList){

        List<String> menulist = new ArrayList<>();

        for(Food food : foodMenu){
            menulist.add(food.getName());
        }

        for(FoodDto food : foodList){
            if(menulist.contains(food.getName())){
                throw new IllegalArgumentException("기존 저장된 음식명과 중복");
            }
        }
    }

    public static void registFoodOverlapValidator(List<FoodDto> foodList){

        List<String> foodNameList = new ArrayList<>();

        for(FoodDto food : foodList){
            foodNameList.add(food.getName());
        }

        for(FoodDto food : foodList){
            if(Collections.frequency(foodNameList,food.getName()) >= 2){
                throw new IllegalArgumentException("입력된 음식명에 중복 에러");
            }
        }
    }

    public static void orderValidator(List<FoodOrderRequestDto> foods){

        for(FoodOrderRequestDto food : foods){

            if(food.getQuantity() < MIN_ORDER_FOOD || food.getQuantity() > MAX_ORDER_FOOD){
                throw new IllegalArgumentException("허용값: 1 ~ 100 입력");
            }
        }
    }

    public static void orderPriceValidator(int totalPrice, int minOrderPrice){

        if(totalPrice < minOrderPrice){
            throw new IllegalArgumentException("최소 주문 가격 이상 입력");
        }

    }

    public static void spicyRestaurantValidator(SpicyRestaurantDto restaurantDto){

        if (restaurantDto.getMinOrderPrice() < MIN_ORDER_PRICE || restaurantDto.getMinOrderPrice() > MAX_ORDER_PRICE) {
            throw new IllegalArgumentException("허용값: 1,000원 ~ 100,000원 입력");
        }

        if (restaurantDto.getMinOrderPrice() % ORDER_PRICE_UNIT != 0) {
            throw new IllegalArgumentException("100 원 단위로만 입력 가능 (예. 2,220원 입력 시 에러발생. 2,300원은 입력 가능)");
        }

        if (restaurantDto.getDeliveryFee() < MIN_DELIVERY_FEE || restaurantDto.getDeliveryFee() > MAX_DELIVERY_FEE) {
            throw new IllegalArgumentException("허용값: 0원 ~ 10,000원");
        }

        if (restaurantDto.getDeliveryFee() % DELIVERY_FEE_UNIT != 0) {
            throw new IllegalArgumentException("500 원 단위로만 입력 가능 (예. 2,200원 입력 시 에러발생. 2,500원 입력 가능)");
        }
    }

    public static void spicyFoodOverlapValidator(List<SpicyFood> foodMenu, List<FoodDto> foodList){

        List<String> menulist = new ArrayList<>();

        for(SpicyFood food : foodMenu){
            menulist.add(food.getName());
        }

        for(FoodDto food : foodList){
            if(menulist.contains(food.getName())){
                throw new IllegalArgumentException("기존 저장된 음식명과 중복");
            }
        }
    }
}
