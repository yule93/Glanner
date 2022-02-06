package com.glanner.api.service;

import com.glanner.api.dto.request.SaveUserReqDto;
import com.glanner.api.dto.response.FindPlannerWorkResDto;

import java.time.LocalDateTime;
import java.util.List;

public interface UserService {
    public Long saveUser(SaveUserReqDto reqDto);
    List<FindPlannerWorkResDto> getWorks(String userEmail, LocalDateTime month);
}
