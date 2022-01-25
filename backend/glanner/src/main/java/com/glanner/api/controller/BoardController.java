package com.glanner.api.controller;


import com.glanner.api.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {
    private final BoardService boardService;

//    @PostMapping("/saveFreeBoard")
//    public ResponseEntity<BaseResponseEntity> saveFreeBoard(Authentication authentication, BoardSaveReqDto boardSaveReqDto){
//        User user = (User)authentication.getPrincipal();
//        boardService.saveFreeBoard(user.getId(), boardSaveReqDto);
//        return ResponseEntity.status(200).body(new BaseResponseEntity(200, "Success"));
//    }
}
