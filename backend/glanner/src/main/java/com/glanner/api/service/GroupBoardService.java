package com.glanner.api.service;

import com.glanner.api.dto.request.SaveGroupBoardReqDto;

public interface GroupBoardService {
    public void saveGroupBoard(String userEmail, SaveGroupBoardReqDto reqDto);
}
