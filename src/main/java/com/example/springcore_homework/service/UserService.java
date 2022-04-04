package com.example.springcore_homework.service;

import com.example.springcore_homework.dto.UserDto;
import com.example.springcore_homework.models.User;
import com.example.springcore_homework.models.UserRoleEnum;
import com.example.springcore_homework.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";

    public User registerUser(UserDto userDto) {

        // 회원 ID 중복 확인
        String username = userDto.getUsername();
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }

        // 패스워드 암호화
        String password = userDto.getPassword();

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (userDto.isAdmin()) {
            if (!userDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username, password, role);
        userRepository.save(user);

        return userRepository.findByUsername(username).get();
    }


}
