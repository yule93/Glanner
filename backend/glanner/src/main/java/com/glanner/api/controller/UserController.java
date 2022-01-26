package com.glanner.api.controller;

import com.glanner.api.dto.request.UserSaveReqDto;
import com.glanner.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // 탈퇴
//    @GetMapping("/withdrawal")
    @DeleteMapping("/withdrawal/{email}")
    public ResponseEntity<String> delete(@PathVariable("email") String email) {
        userService.deleteUser(email);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }


}