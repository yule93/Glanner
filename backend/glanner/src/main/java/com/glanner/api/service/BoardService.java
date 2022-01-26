package com.glanner.api.service;

import com.glanner.api.dto.request.*;

public interface BoardService {
    public void saveFreeBoard(String userEmail, BoardSaveReqDto reqDto);
    public void saveNoticeBoard(String userEmail, BoardSaveReqDto reqDto);
    public void editBoard(Long boardId, BoardUpdateReqDto reqDto);
    public void deleteBoard(Long boardId);
    public void addComment(String userEmail, BoardAddCommentReqDto reqDto);
    public void updateCount(Long boardId, BoardCountReqDto reqDto);
}
