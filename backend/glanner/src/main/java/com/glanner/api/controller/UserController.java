package com.glanner.api.controller;

import com.glanner.api.dto.request.UserSaveReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    @PostMapping("/join")
    public Long join(@RequestBody UserSaveReqDto userSaveReqDto) {
        UserSaveReqDto reqDto = UserSaveReqDto.builder()
                .email("ssafy@ssafy.com")
                .name("싸피")
                .password("1234")
                .phoneNumber("010-1234-5678")
                .build();

        System.out.println(reqDto.toEntity().getEmail());

        return userSaveReqDto.toEntity().getId();
    }

}
