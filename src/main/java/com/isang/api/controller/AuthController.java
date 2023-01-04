package com.isang.api.controller;

import com.isang.api.entity.request.Login;
import com.isang.api.entity.response.SessionResponse;
import com.isang.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody Login login){
        //json 으로 ID, PW 를 받음
        String accessToken = authService.signIn(login);
        // 토큰을 발급
        return new SessionResponse(accessToken);
    }
}
