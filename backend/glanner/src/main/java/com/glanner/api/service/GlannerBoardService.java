package com.glanner.api.service;

import com.glanner.api.dto.request.SaveGlannerBoardReqDto;

public interface GlannerBoardService {
    public void saveGlannerBoard(String userEmail, SaveGlannerBoardReqDto reqDto);
}
