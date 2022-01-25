package com.glanner.api.service;

import com.glanner.api.dto.request.*;

public interface GlannerService {

    public void saveGlanner(String hostEmail);
    public void deleteGlanner(DeleteGlannerReqDto reqDto);
    public void addUser(AddUserToGlannerReqDto reqDto, String hostEmail);
    public void deleteUser(DeleteUserFromGlannerReqDto reqDto);
    public void addDailyWork(AddGlannerWorkReqDto reqDto);
    public void deleteDailyWork(DeleteGlannerWorkReqDto reqDto);
    public void updateDailyWork(UpdateGlannerWorkReqDto reqDto);

}
