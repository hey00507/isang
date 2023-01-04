package com.isang.api.controller;

import com.isang.api.domain.User;
import com.isang.api.entity.request.Login;
import com.isang.api.repository.UserRepository;
import com.isang.common.exception.custom.InvalidSigninInformation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;

    @PostMapping("/auth/login")
    public User login(@RequestBody Login login){
        //json 으로 ID, PW 를 받음
        log.info("login info>> {}" , login);
        // DB 에서 조회
        User user = userRepository
                .findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(InvalidSigninInformation::new);
        // 토큰을 발급
        return user;
    }
}
