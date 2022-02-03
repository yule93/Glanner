package com.glanner.api.controller;

import com.glanner.api.dto.request.SaveGlannerBoardReqDto;
import com.glanner.api.dto.response.BaseResponseEntity;
import com.glanner.api.service.BoardService;
import com.glanner.api.service.GlannerBoardService;
import com.glanner.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/glanner-board")
public class GlannerBoardController extends BoardController<SaveGlannerBoardReqDto>{
    private final GlannerBoardService glannerBoardService;

    @Autowired
    public GlannerBoardController(BoardService boardService, GlannerBoardService glannerBoardService) {
        super(boardService);
        this.glannerBoardService = glannerBoardService;
    }

    @Override
    @PostMapping("/save")
    public ResponseEntity<BaseResponseEntity> saveBoard(@RequestBody @Valid SaveGlannerBoardReqDto requestDto) {
        String userEmail = getUsername(SecurityUtils.getCurrentUsername());
        glannerBoardService.saveGlannerBoard(userEmail, requestDto);
        return ResponseEntity.status(200).body(new BaseResponseEntity(200, "Success"));
    }
}
