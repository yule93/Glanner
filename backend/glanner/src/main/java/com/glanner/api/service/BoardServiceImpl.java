package com.glanner.api.service;

import com.glanner.api.dto.request.*;
import com.glanner.api.queryrepository.UserQueryRepository;
import com.glanner.core.domain.board.*;
import com.glanner.core.domain.user.User;
import com.glanner.core.repository.BoardRepository;
import com.glanner.core.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    private final UserQueryRepository userQueryRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Override
    public FreeBoard getFreeBoard(Long boardId) {
        FreeBoard freeBoard= (FreeBoard) getBoard(boardRepository.findById(boardId));
        freeBoard.updateCount();
        return freeBoard;
    }

    @Override
    public NoticeBoard getNoticeBoard(Long boardId) {
        NoticeBoard noticeBoard = (NoticeBoard) getBoard(boardRepository.findById(boardId));
        noticeBoard.updateCount();
        return noticeBoard;
    }

    @Override
    public void saveFreeBoard(String userEmail, BoardSaveReqDto reqDto, List<MultipartFile> files) {
        User user = getUser(userQueryRepository.findByEmail(userEmail));
        FreeBoard freeBoard = reqDto.toFreeBoardEntity(user);

        saveFiles(freeBoard, files);

        boardRepository.save(freeBoard);
    }

    @Override
    public void saveNoticeBoard(String userEmail, BoardSaveReqDto reqDto, List<MultipartFile> files) {
        User user = getUser(userQueryRepository.findByEmail(userEmail));
        NoticeBoard noticeBoard = reqDto.toNoticeBoardEntity(user);

        saveFiles(noticeBoard, files);

        boardRepository.save(noticeBoard);
    }

    @Override
    public void editBoard(Long boardId, BoardUpdateReqDto reqDto) {
        Board board = getBoard(boardRepository.findById(boardId));
        board.changeBoard(reqDto.getTitle(), reqDto.getContent(), reqDto.getFileUrls());
    }

    @Override
    public void deleteBoard(Long boardId) {
        Board board = getBoard(boardRepository.findById(boardId));
        boardRepository.delete(board);
    }

    @Override
    public void addComment(String userEmail, BoardAddCommentReqDto reqDto) {
        User user = getUser(userQueryRepository.findByEmail(userEmail));
        Board board = getBoard(boardRepository.findById(reqDto.getBoardId()));
        Comment parent = (reqDto.getParentId() == null)?
                null:getComment(commentRepository.findById(reqDto.getParentId()));
        board.addComment(reqDto.toEntity(user, board, parent));
        boardRepository.save(board);
    }

    @Override
    public void editComment(Long commentId, BoardUpdateCommentReqDto reqDto) {
        Comment comment = getComment(commentRepository.findById(commentId));
        comment.changeContent(reqDto.getContent());
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment savedComment = getComment(commentRepository.findById(commentId));
        Board board = savedComment.getBoard();
        board.getComments().remove(savedComment);
        commentRepository.delete(savedComment);
    }

    @Override
    public void updateCount(Long boardId, BoardCountReqDto reqDto) {
        if(reqDto.getCountType().equals("COUNT")){
            Board board = getBoard(boardRepository.findById(boardId));
            board.updateCount();
            boardRepository.save(board);
        }
        else{
            FreeBoard freeBoard = (FreeBoard) getBoard(boardRepository.findById(boardId));
            freeBoard.updateCount(reqDto.getCountType());
            boardRepository.save(freeBoard);
        }
    }

    private void saveFiles(Board board, List<MultipartFile> files) {
        if(!files.isEmpty()){
            String realPath = "uploads/";
            String date = new SimpleDateFormat("yyMMdd").format(new Date());
            String saveFolder = realPath + File.separator + date;

            File folder = new File(saveFolder);

            if(!folder.exists()) folder.mkdir();
            List<FileInfo> fileInfos = new ArrayList<>();
            for(MultipartFile file: files){
                String originalFileName = file.getOriginalFilename();
                FileInfo fileInfo = null;
                if(!originalFileName.isEmpty()){
                    String saveFileName = UUID.randomUUID().toString()
                            + originalFileName.substring(originalFileName.lastIndexOf('.'));
                    fileInfo = FileInfo.builder()
                            .saveFolder(date)
                            .originFile(originalFileName)
                            .saveFile(saveFileName).build();
                    try {
                        file.transferTo(new File(folder, saveFileName));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                board.addFile(fileInfo);
            }
        }
    }

    public Board getBoard(Optional<Board> board){
        return board.orElseThrow(()->new IllegalStateException("존재하지 않는 게시물입니다."));
    }

    public Comment getComment(Optional<Comment> comment){
        return comment.orElseThrow(
                ()->new IllegalStateException("존재하지 않는 댓글입니다.")
        );
    }
    public User getUser(Optional<User> user){
        return user.orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
    }
}
