package com.glanner.api.controller;


import com.glanner.api.dto.request.*;
import com.glanner.api.dto.response.BaseResponseEntity;
import com.glanner.api.exception.UserNotFoundException;
import com.glanner.api.service.BoardService;
import com.glanner.core.domain.board.FreeBoard;
import com.glanner.core.domain.board.NoticeBoard;
import com.glanner.core.repository.FreeBoardRepository;
import com.glanner.core.repository.NoticeBoardRepository;
import com.glanner.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    public ResponseEntity<BaseResponseEntity> saveFreeBoard(@Valid BoardSaveReqDto boardSaveReqDto){
        String userEmail = getUsername(SecurityUtils.getCurrentUsername());
        boardService.saveFreeBoard(userEmail, boardSaveReqDto);
        return ResponseEntity.status(200).body(new BaseResponseEntity(200, "Success"));
    }

    @PostMapping("/saveNoticeBoard")
    public ResponseEntity<BaseResponseEntity> saveNoticeBoard(@RequestBody @Valid BoardSaveReqDto boardSaveReqDto, @RequestParam("files") List<MultipartFile> files){
        String userEmail = getUsername(SecurityUtils.getCurrentUsername());
        boardService.saveNoticeBoard(userEmail, boardSaveReqDto, files);
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

    @RequestMapping("/download")
    @ResponseBody
    public ResponseEntity<Resource> download(@RequestBody @Valid BoardDownloadReqDto reqDto, HttpServletRequest request){
        String filePath = "uploads" + File.separator + reqDto.getSavedFolder() + File.separator + reqDto.getSavedFile();
        File target = new File(filePath);
        HttpHeaders header = new HttpHeaders();
        Resource rs = null;
        if(target.exists()) {
            try {
                String mimeType = Files.probeContentType(Paths.get(target.getAbsolutePath()));
                if(mimeType == null) {
                    mimeType = "apllication/download; charset=UTF-8";
                }
                rs = new UrlResource(target.toURI());
                String userAgent = request.getHeader("User-Agent");
                boolean isIE = userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1;
                String fileName = null;
                String originalFile = reqDto.getOriginalFile();
                // IE는 다르게 처리
                if (isIE) {
                    fileName = URLEncoder.encode(originalFile, "UTF-8").replaceAll("\\+", "%20");
                } else {
                    fileName = new String(originalFile.getBytes("UTF-8"), "ISO-8859-1");
                }
                header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ fileName +"\"");
                header.setCacheControl("no-cache");
                header.setContentType(MediaType.parseMediaType(mimeType));
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<Resource>(rs, header, HttpStatus.OK);
    }


    public String getUsername(Optional<String> username){
        return username.orElseThrow(UserNotFoundException::new);
    }
}
