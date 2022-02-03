package com.glanner.api.service;

import com.glanner.api.dto.request.SendMailReqDto;

public interface MailService {
    public void sendMail(SendMailReqDto reqDto);
}
