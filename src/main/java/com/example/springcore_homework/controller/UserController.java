package com.example.springcore_homework.controller;

import com.example.springcore_homework.dto.UserDto;
import com.example.springcore_homework.models.User;
import com.example.springcore_homework.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor // final로 선언된 멤버 변수를 자동으로 생성합니다.
@Controller
public class UserController {

    private final UserService userService;

    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    @ResponseBody
    public User registerUser(@RequestBody UserDto userDto) {
        return userService.registerUser(userDto);
    }



}
