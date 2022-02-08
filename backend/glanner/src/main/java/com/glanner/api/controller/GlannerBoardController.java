package com.glanner.api.controller;

import com.glanner.api.dto.request.SaveGlannerBoardReqDto;
import com.glanner.api.dto.response.BaseResponseEntity;
import com.glanner.api.dto.response.FindGlannerBoardResDto;
import com.glanner.api.queryrepository.GlannerBoardQueryRepository;
import com.glanner.api.service.BoardService;
import com.glanner.api.service.GlannerBoardService;
import com.glanner.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/glanner-board")
public class GlannerBoardController extends BoardController<SaveGlannerBoardReqDto>{
    private final GlannerBoardService glannerBoardService;
    private final GlannerBoardQueryRepository queryRepository;

    @Autowired
    public GlannerBoardController(BoardService boardService, GlannerBoardService glannerBoardService, GlannerBoardQueryRepository queryRepository) {
        super(boardService);
        this.glannerBoardService = glannerBoardService;
        this.queryRepository = queryRepository;
    }

    @Override
    @PostMapping
    public ResponseEntity<BaseResponseEntity> saveBoard(@RequestBody @Valid SaveGlannerBoardReqDto requestDto) {
        String userEmail = getUsername(SecurityUtils.getCurrentUsername());
        glannerBoardService.saveGlannerBoard(userEmail, requestDto);
        return ResponseEntity.status(200).body(new BaseResponseEntity(200, "Success"));
    }

    @GetMapping("{glannerId}/{page}/{limit}")
    public ResponseEntity<List<FindGlannerBoardResDto>> getBoards(@PathVariable Long glannerId, @PathVariable int page, @PathVariable int limit){
        List<FindGlannerBoardResDto> responseDto = queryRepository.findPage(glannerId, page, limit);
        return ResponseEntity.status(200).body(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FindGlannerBoardResDto> getBoard(@PathVariable Long id){
        FindGlannerBoardResDto responseDto = queryRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return ResponseEntity.status(200).body(responseDto);
    }
}
