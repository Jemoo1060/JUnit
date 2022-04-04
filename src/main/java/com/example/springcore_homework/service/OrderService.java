package com.example.springcore_homework.service;

import com.example.springcore_homework.dto.FoodOrderRequestDto;
import com.example.springcore_homework.dto.OrderDto;
import com.example.springcore_homework.models.Food;
import com.example.springcore_homework.models.OrderTable;
import com.example.springcore_homework.models.OrderRequest;
import com.example.springcore_homework.models.OrderTable;
import com.example.springcore_homework.models.Restaurant;
import com.example.springcore_homework.repository.FoodRepository;
import com.example.springcore_homework.repository.OrderRepository;
import com.example.springcore_homework.repository.OrderRequestRepository;
import com.example.springcore_homework.repository.RestaurantRepository;
import com.example.springcore_homework.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor // final로 선언된 멤버 변수를 자동으로 생성합니다.
@Service // 서비스임을 선언합니다.
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderRequestRepository orderRequestRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;

    @Transactional
    public OrderTable order(OrderDto orderDto) {

        int totalPrice = 0;

        Restaurant restaurant = restaurantRepository.findById(orderDto.getRestaurantId())
                .orElseThrow(() -> new NullPointerException("해당 가게가 존재하지 않습니다."));

        Validator.orderValidator(orderDto.getFoods());

        for(FoodOrderRequestDto orderFood : orderDto.getFoods()){

            Food food = foodRepository.findById(orderFood.getId())
                    .orElseThrow(() -> new NullPointerException("해당 음식이 존재하지 않습니다."));

            totalPrice += food.getPrice() * orderFood.getQuantity();

        }

        Validator.orderPriceValidator(totalPrice,restaurant.getMinOrderPrice());

        totalPrice += restaurant.getDeliveryFee();

        OrderTable order =  new OrderTable(restaurant,totalPrice);

        List<OrderRequest> orderRequestList = new ArrayList<>();

        for(FoodOrderRequestDto orderFood : orderDto.getFoods()){

            Food food = foodRepository.findById(orderFood.getId())
                    .orElseThrow(() -> new NullPointerException("해당 음식이 존재하지 않습니다."));

            OrderRequest orderRequest = new OrderRequest(food,orderFood.getQuantity());
            orderRequestList.add(orderRequest);

        }
        order.setFoods(orderRequestList);

        orderRepository.save(order);

        orderRequestRepository.saveAll(orderRequestList);


        return  order;
    }

    public List<OrderTable> orders() {

        return orderRepository.findAll();
    }
}
