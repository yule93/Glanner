package com.glanner.api.service;

import com.glanner.api.dto.request.*;
import com.glanner.api.queryrepository.UserQueryRepository;
import com.glanner.core.domain.board.Board;
import com.glanner.core.domain.board.Comment;
import com.glanner.core.domain.board.FreeBoard;
import com.glanner.core.domain.board.NoticeBoard;
import com.glanner.core.domain.user.User;
import com.glanner.core.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    private final UserQueryRepository userQueryRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final FreeBoardRepository freeBoardRepository;
    private final NoticeBoardRepository noticeBoardRepository;

    @Override
    public void saveFreeBoard(String userEmail, BoardSaveReqDto reqDto) {
        User user = userQueryRepository.findByEmail(userEmail).orElseThrow(()->new IllegalStateException("존재하지 않는 회원입니다."));
        FreeBoard freeBoard = reqDto.toFreeBoardEntity(user);
        boardRepository.save(freeBoard);
    }

    @Override
    public void saveNoticeBoard(String userEmail, BoardSaveReqDto reqDto) {
        User user = userQueryRepository.findByEmail(userEmail).orElseThrow(()->new IllegalStateException("존재하지 않는 회원입니다."));
        NoticeBoard noticeBoard = reqDto.toNoticeBoardEntity(user);
        boardRepository.save(noticeBoard);
    }

    @Override
    public void editBoard(Long boardId, BoardUpdateReqDto reqDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(()->new IllegalStateException("존재하지 않는 게시물입니다."));
        board.changeBoard(reqDto.getTitle(), reqDto.getContent(), reqDto.getFileUrls());
        boardRepository.save(board);
    }

    @Override
    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(()->new IllegalStateException("존재하지 않는 게시물입니다"));
        boardRepository.delete(board);
    }

    @Override
    public void addComment(String userEmail, BoardAddCommentReqDto reqDto) {
        User user = userQueryRepository.findByEmail(userEmail).orElseThrow(()->new IllegalStateException("존재하지 않는 회원입니다."));
        Board board = boardRepository.findById(reqDto.getBoardId()).orElseThrow(()->new IllegalStateException("존재하지 않는 게시물입니다."));
        Comment parent = (reqDto.getParentId() == null)?
                null:commentRepository.findById(reqDto.getParentId()).orElseThrow(()->new IllegalStateException("존재하지 않는 댓글입니다."));
        Comment comment = reqDto.toEntity(user, board, parent);
        commentRepository.save(comment);
    }

    @Override
    public void updateCount(Long boardId, BoardCountReqDto reqDto) {
        if(reqDto.getCountType().equals("COUNT")){
            Board board = boardRepository.findById(boardId).orElseThrow(()-> new IllegalStateException("존재하지 않는 게시글입니다"));
            board.updateCount();
            boardRepository.save(board);
        }
        else{
            FreeBoard freeBoard = (FreeBoard) boardRepository.findById(boardId).orElseThrow(()-> new IllegalStateException("존재하지 않는 게시글입니다"));
            freeBoard.updateCount(reqDto.getCountType());
            boardRepository.save(freeBoard);
        }
    }
}
