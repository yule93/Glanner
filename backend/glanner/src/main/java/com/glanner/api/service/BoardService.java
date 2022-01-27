package com.glanner.api.service;

import com.glanner.api.dto.request.*;
import com.glanner.core.domain.board.FreeBoard;
import com.glanner.core.domain.board.NoticeBoard;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardService {
    public FreeBoard getFreeBoard(Long boardId);
    public NoticeBoard getNoticeBoard(Long boardId);
    public void saveFreeBoard(String userEmail, BoardSaveReqDto reqDto, List<MultipartFile> files);
    public void saveNoticeBoard(String userEmail, BoardSaveReqDto reqDto, List<MultipartFile> files);
    public void editBoard(Long boardId, BoardUpdateReqDto reqDto);
    public void deleteBoard(Long boardId);
    public void addComment(String userEmail, BoardAddCommentReqDto reqDto);
    public void editComment(Long commentId, BoardUpdateCommentReqDto reqDto);
    public void deleteComment(Long commentId);
    public void updateCount(Long boardId, BoardCountReqDto reqDto);
}
