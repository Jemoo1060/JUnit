package com.example.springcore_homework.controller;

import com.example.springcore_homework.dto.OrderDto;
import com.example.springcore_homework.models.OrderTable;
import com.example.springcore_homework.repository.RestaurantRepository;
import com.example.springcore_homework.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor // final로 선언된 멤버 변수를 자동으로 생성합니다.
@Controller
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order/request")
    @ResponseBody
    public OrderTable order(@RequestBody OrderDto orderDto){

        return orderService.order(orderDto);

    }

    @GetMapping("/orders")
    @ResponseBody
    public List<OrderTable> orders(){

        return orderService.orders();
    }

}
