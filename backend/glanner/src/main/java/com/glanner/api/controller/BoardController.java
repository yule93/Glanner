package com.glanner.api.controller;


import com.glanner.api.dto.request.BoardAddCommentReqDto;
import com.glanner.api.dto.request.BoardCountReqDto;
import com.glanner.api.dto.request.BoardSaveReqDto;
import com.glanner.api.dto.request.BoardUpdateReqDto;
import com.glanner.api.dto.response.BaseResponseEntity;
import com.glanner.api.exception.UserNotFoundException;
import com.glanner.api.service.BoardService;
import com.glanner.core.domain.board.FreeBoard;
import com.glanner.core.domain.board.NoticeBoard;
import com.glanner.core.repository.FreeBoardRepository;
import com.glanner.core.repository.NoticeBoardRepository;
import com.glanner.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final FreeBoardRepository freeBoardRepository;
    private final NoticeBoardRepository noticeBoardRepository;

    @GetMapping("/getFreeBoard")
    public ResponseEntity<List<FreeBoard>> getFreeBoards(){
        List<FreeBoard> freeBoards= freeBoardRepository.findAll();
        return ResponseEntity.status(200).body(freeBoards);
    }

    @GetMapping("/getFreeBoard/{boardId}")
    public ResponseEntity<FreeBoard> getFreeBoard(@PathVariable Long boardId){
        FreeBoard freeBoard= freeBoardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시판 입니다."));
        return ResponseEntity.status(200).body(freeBoard);
    }

    @GetMapping("/getNoticeBoard")
    public ResponseEntity<List<NoticeBoard>> getNoticeBoard(){
        List<NoticeBoard> noticeBoards= noticeBoardRepository.findAll();
        return ResponseEntity.status(200).body(noticeBoards);
    }

    @GetMapping("/getNoticeBoard/{boardId}")
    public ResponseEntity<NoticeBoard> getNoticeBoard(@PathVariable Long boardId){
        NoticeBoard noticeBoard = noticeBoardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시판 입니다."));
        return ResponseEntity.status(200).body(noticeBoard);
    }

    @PostMapping("/saveFreeBoard")
    public ResponseEntity<BaseResponseEntity> saveFreeBoard(@RequestBody @Valid BoardSaveReqDto boardSaveReqDto){
        String userEmail = getUsername(SecurityUtils.getCurrentUsername());
        boardService.saveFreeBoard(userEmail, boardSaveReqDto);
        return ResponseEntity.status(200).body(new BaseResponseEntity(200, "Success"));
    }

    @PostMapping("/saveNoticeBoard")
    public ResponseEntity<BaseResponseEntity> saveNoticeBoard(@RequestBody @Valid BoardSaveReqDto boardSaveReqDto){
        String userEmail = getUsername(SecurityUtils.getCurrentUsername());
        boardService.saveNoticeBoard(userEmail, boardSaveReqDto);
        return ResponseEntity.status(200).body(new BaseResponseEntity(200, "Success"));
    }

    @PutMapping("/editBoard/{boardId}")
    public ResponseEntity<BaseResponseEntity> editBoard(@PathVariable Long boardId, @RequestBody @Valid BoardUpdateReqDto reqDto){
        boardService.editBoard(boardId, reqDto);
        return ResponseEntity.status(200).body(new BaseResponseEntity(200, "Success"));
    }

    @DeleteMapping("/deleteBoard/{boardId}")
    public ResponseEntity<BaseResponseEntity> deleteBoard(@PathVariable Long boardId){
        boardService.deleteBoard(boardId);
        return ResponseEntity.status(200).body(new BaseResponseEntity(200, "Success"));
    }

    @PostMapping("/addComment")
    public ResponseEntity<BaseResponseEntity> addComment(@RequestBody @Valid BoardAddCommentReqDto reqDto){
        String userEmail = getUsername(SecurityUtils.getCurrentUsername());
        boardService.addComment(userEmail, reqDto);
        return ResponseEntity.status(200).body(new BaseResponseEntity(200, "Success"));
    }

    @PutMapping("/updateCount/{boardId}")
    public ResponseEntity<BaseResponseEntity> updateCount(@PathVariable Long boardId, @RequestBody @Valid BoardCountReqDto reqDto){
        boardService.updateCount(boardId, reqDto);
        return ResponseEntity.status(200).body(new BaseResponseEntity(200, "Success"));
    }

    public String getUsername(Optional<String> username){
        return username.orElseThrow(UserNotFoundException::new);
    }
}
