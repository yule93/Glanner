package com.glanner.api.service;

import com.glanner.api.dto.request.SendMailReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class sendMailImpl implements MailService{

    private final JavaMailSender javaMailSender;

    @Override
    public void sendMail(SendMailReqDto reqDto) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(reqDto.getAddress());
        msg.setSubject(reqDto.getTitle());
        msg.setText(reqDto.getContent());
        javaMailSender.send(msg);
    }
}
