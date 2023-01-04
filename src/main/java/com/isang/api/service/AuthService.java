package com.isang.api.service;

import com.isang.api.domain.Session;
import com.isang.api.domain.User;
import com.isang.api.entity.request.Login;
import com.isang.api.repository.UserRepository;
import com.isang.common.exception.custom.InvalidSigninInformation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public String signIn(Login login){
        User user = userRepository
                .findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(InvalidSigninInformation::new);

        Session session = user.addSession();

        return session.getAccessToken();

    }
}
