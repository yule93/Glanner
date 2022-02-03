package com.glanner.api.service;

import com.glanner.api.dto.request.SendMailReqDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
class MailServiceTest {
    @Autowired
    private JavaMailSender javaMailSender;

    @Test
    public void testSendMail() throws Exception{
        //given
        SendMailReqDto reqDto = new SendMailReqDto("aldlswjddma@naver.com", "title", "Content");

        //when
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(reqDto.getAddress());
        msg.setSubject(reqDto.getTitle());
        msg.setText(reqDto.getContent());
        javaMailSender.send(msg);
    }
}