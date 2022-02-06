package com.glanner.api.service;

import com.glanner.api.dto.request.*;

public interface GlannerService {

    void saveGlanner(String hostEmail);
    void deleteGlanner(Long id);
    void addUser(AddUserToGlannerReqDto reqDto, String hostEmail);
    void deleteUser(Long glannerId, Long userId);
    void addDailyWork(AddGlannerWorkReqDto reqDto);
    void deleteDailyWork(Long glannerId, Long workId);
    void updateDailyWork(UpdateGlannerWorkReqDto reqDto);

}
