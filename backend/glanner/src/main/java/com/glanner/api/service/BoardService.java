package com.glanner.api.service;

import com.glanner.api.dto.request.BoardAddCommentReqDto;
import com.glanner.api.dto.request.BoardCountReqDto;
import com.glanner.api.dto.request.BoardSaveReqDto;
import com.glanner.api.dto.request.BoardUpdateReqDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardService {
    public void saveFreeBoard(String userEmail, BoardSaveReqDto reqDto);
    public void saveNoticeBoard(String userEmail, BoardSaveReqDto reqDto, List<MultipartFile> files);
    public void editBoard(Long boardId, BoardUpdateReqDto reqDto);
    public void deleteBoard(Long boardId);
    public void addComment(String userEmail, BoardAddCommentReqDto reqDto);
    public void updateCount(Long boardId, BoardCountReqDto reqDto);
}
