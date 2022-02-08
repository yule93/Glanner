package com.glanner.api.controller;

import com.glanner.api.dto.request.SaveBoardReqDto;
import com.glanner.api.dto.response.FindNoticeBoardResDto;
import com.glanner.api.queryrepository.NoticeBoardQueryRepository;
import com.glanner.api.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notice")
public class NoticeBoardController extends BoardController<SaveBoardReqDto> {
    private final NoticeBoardQueryRepository queryRepository;

    @Autowired
    public NoticeBoardController(BoardService boardService, NoticeBoardQueryRepository queryRepository) {
        super(boardService);
        this.queryRepository = queryRepository;
    }

    @GetMapping("/{page}/{limit}")
    public ResponseEntity<List<FindNoticeBoardResDto>> getBoards(@PathVariable int page, @PathVariable int limit){
        List<FindNoticeBoardResDto> responseDto = queryRepository.findPage(page, limit);
        return ResponseEntity.status(200).body(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FindNoticeBoardResDto> getBoard(@PathVariable Long id){
        FindNoticeBoardResDto responseDto = queryRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return ResponseEntity.status(200).body(responseDto);
    }
}
