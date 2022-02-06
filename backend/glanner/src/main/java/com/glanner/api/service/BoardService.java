package com.glanner.api.service;

import com.glanner.api.dto.request.*;


public interface BoardService {
    void saveBoard(String userEmail, SaveBoardReqDto requestDto);
    void modifyBoard(Long boardId, SaveBoardReqDto requestDto);
    void deleteBoard(Long boardId);
    void addComment(String userEmail, AddCommentReqDto requestDto);
    void modifyComment(Long commentId, UpdateCommentReqDto requestDto);
    void deleteComment(Long commentId);
}
