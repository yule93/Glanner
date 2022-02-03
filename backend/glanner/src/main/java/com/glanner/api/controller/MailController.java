package com.glanner.api.controller;

import com.glanner.api.dto.request.SendMailReqDto;
import com.glanner.api.dto.response.BaseResponseEntity;
import com.glanner.api.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mail")
public class MailController {
    private final MailService mailService;

    @PostMapping("/send")
    public ResponseEntity<BaseResponseEntity> send(@RequestBody SendMailReqDto reqDto){

        mailService.sendMail(reqDto);

        return ResponseEntity.status(200).body(new BaseResponseEntity(200, "Success"));
    }
}
