package com.glanner.api.controller;

import com.glanner.api.dto.request.AddCommentReqDto;
import com.glanner.api.dto.request.SaveBoardReqDto;
import com.glanner.api.dto.request.UpdateCommentReqDto;
import com.glanner.api.dto.response.BaseResponseEntity;
import com.glanner.api.dto.response.FindBoardResDto;
import com.glanner.api.exception.UserNotFoundException;
import com.glanner.api.service.BoardService;
import com.glanner.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequiredArgsConstructor
@Transactional
public class BoardController<Q extends SaveBoardReqDto> {
    private final BoardService boardService;

    @PostMapping("/save")
    public ResponseEntity<BaseResponseEntity> saveBoard(@RequestBody @Valid Q requestDto){
        String userEmail = getUsername(SecurityUtils.getCurrentUsername());
        boardService.saveBoard(userEmail, requestDto);
        return ResponseEntity.status(200).body(new BaseResponseEntity(200, "Success"));
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<BaseResponseEntity> modifyBoard(@PathVariable Long id, @RequestBody @Valid Q requestDto){
        boardService.modifyBoard(id, requestDto);
        return ResponseEntity.status(200).body(new BaseResponseEntity(200, "Success"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponseEntity> deleteBoard(@PathVariable Long id){
        boardService.deleteBoard(id);
        return ResponseEntity.status(200).body(new BaseResponseEntity(200, "Success"));
    }

    @PostMapping("/add-comment")
    public ResponseEntity<BaseResponseEntity> addComment(@RequestBody @Valid AddCommentReqDto reqDto){
        String userEmail = getUsername(SecurityUtils.getCurrentUsername());
        boardService.addComment(userEmail, reqDto);
        return ResponseEntity.status(200).body(new BaseResponseEntity(200, "Success"));
    }

    @PutMapping("/modify-comment")
    public ResponseEntity<BaseResponseEntity> modifyComment(@RequestBody @Valid UpdateCommentReqDto reqDto){
        boardService.modifyComment(reqDto);
        return ResponseEntity.status(200).body(new BaseResponseEntity(200, "Success"));
    }

    @DeleteMapping("/delete-comment/{commentId}")
    public ResponseEntity<BaseResponseEntity> deleteComment(@PathVariable Long commentId){
        boardService.deleteComment(commentId);
        return ResponseEntity.status(200).body(new BaseResponseEntity(200, "Success"));
    }

    @GetMapping("/download-file/{fileId}")
    public ResponseEntity<BaseResponseEntity> downloadFile(@PathVariable Long fileId){
        return ResponseEntity.status(200).body(new BaseResponseEntity(200, "Success"));
    }

    public String getUsername(Optional<String> username){
        return username.orElseThrow(UserNotFoundException::new);
    }
}
