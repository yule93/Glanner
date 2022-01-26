package com.glanner.api.controller;

import com.glanner.api.dto.request.UserSaveReqDto;
import com.glanner.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UserSaveReqDto userSaveReqDto) {

        userService.saveUser(userSaveReqDto);

        return new ResponseEntity<>("Success",HttpStatus.OK);
    }

}