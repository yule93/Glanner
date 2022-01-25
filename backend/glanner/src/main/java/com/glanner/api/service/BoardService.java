package com.glanner.api.service;

import com.glanner.api.dto.request.BoardSaveReqDto;
import com.glanner.api.dto.request.BoardUpdateReqDto;
import com.glanner.core.domain.board.Board;
import com.glanner.core.domain.board.FreeBoard;
import com.glanner.core.domain.board.NoticeBoard;

public interface BoardService {
    public FreeBoard saveFreeBoard(Long userId, BoardSaveReqDto reqDto);
    public NoticeBoard saveNoticeBoard(Long userId, BoardSaveReqDto reqDto);
    public Board editBoard(BoardUpdateReqDto reqDto);
}
