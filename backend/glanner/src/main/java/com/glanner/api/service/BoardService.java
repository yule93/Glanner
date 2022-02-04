package com.glanner.api.service;

import com.glanner.api.dto.request.*;


public interface BoardService {
    public void saveBoard(String userEmail, SaveBoardReqDto requestDto);
    public void modifyBoard(Long boardId, SaveBoardReqDto requestDto);
    public void deleteBoard(Long boardId);
    public void addComment(String userEmail, AddCommentReqDto requestDto);
    public void modifyComment(Long commentId, UpdateCommentReqDto requestDto);
    public void deleteComment(Long commentId);
}
