package com.glanner.api.controller;

import com.glanner.api.dto.request.SaveBoardReqDto;
import com.glanner.api.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notice")
public class NoticeBoardController extends BoardController<SaveBoardReqDto> {
    @Autowired
    public NoticeBoardController(BoardService boardService) {
        super(boardService);
    }
}
