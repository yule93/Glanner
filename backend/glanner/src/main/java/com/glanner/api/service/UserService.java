package com.glanner.api.service;

import com.glanner.api.dto.request.SaveUserReqDto;

public interface UserService {
    public Long saveUser(SaveUserReqDto reqDto);
}
