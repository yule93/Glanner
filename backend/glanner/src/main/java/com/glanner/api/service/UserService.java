package com.glanner.api.service;

import com.glanner.api.dto.request.UserSaveReqDto;

public interface UserService {
    public Long saveUser(UserSaveReqDto reqDto);
}
