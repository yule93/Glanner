package com.glanner.api.controller;

import com.glanner.api.dto.request.SaveGroupBoardReqDto;
import com.glanner.api.dto.response.FindGroupBoardResDto;
import com.glanner.api.queryrepository.GroupBoardQueryRepository;
import com.glanner.api.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/group-board")
public class GroupBoardController extends BoardController<SaveGroupBoardReqDto> {
    private final GroupBoardQueryRepository queryRepository;

    @Autowired
    public GroupBoardController(BoardService boardService, GroupBoardQueryRepository queryRepository) {
        super(boardService);
        this.queryRepository = queryRepository;
    }

    @GetMapping("/{page}/{limit}")
    public ResponseEntity<List<FindGroupBoardResDto>> getBoards(@PathVariable int page, @PathVariable int limit){
        List<FindGroupBoardResDto> responseDto = queryRepository.findPage(page, limit);
        return ResponseEntity.status(200).body(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FindGroupBoardResDto> getBoard(@PathVariable Long id){
        FindGroupBoardResDto responseDto = queryRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return ResponseEntity.status(200).body(responseDto);
    }

}
