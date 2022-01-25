package com.glanner.api.service;

import com.glanner.api.dto.request.BoardAddCommentReqDto;
import com.glanner.api.dto.request.BoardSaveReqDto;
import com.glanner.api.dto.request.BoardUpdateReqDto;
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
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final FreeBoardRepository freeBoardRepository;
    private final NoticeBoardRepository noticeBoardRepository;

    @Override
    public FreeBoard saveFreeBoard(Long userId, BoardSaveReqDto reqDto) {
        User user = userRepository.findById(userId).orElseThrow(()->new IllegalStateException("존재하지 않는 회원입니다."));

        FreeBoard freeBoard = reqDto.toFreeBoardEntity(user);

        return boardRepository.save(freeBoard);
    }

    @Override
    public NoticeBoard saveNoticeBoard(Long userId, BoardSaveReqDto reqDto) {
        User user = userRepository.findById(userId).orElseThrow(()->new IllegalStateException("존재하지 않는 회원입니다."));

        NoticeBoard noticeBoard = reqDto.toNoticeBoardEntity(user);

        return boardRepository.save(noticeBoard);
    }

    @Override
    public Board editBoard(BoardUpdateReqDto reqDto) {
        Board board = boardRepository.findById(reqDto.getBoardId()).orElseThrow(()->new IllegalStateException("존재하지 않는 게시물입니다."));

        board.changeBoard(reqDto.getTitle(), reqDto.getContent(), reqDto.getFileUrls());

        return boardRepository.save(board);
    }

    @Override
    public Comment addComment(Long userId, BoardAddCommentReqDto reqDto) {
        User user = userRepository.findById(userId).orElseThrow(()->new IllegalStateException("존재하지 않는 회원입니다."));
        Board board = boardRepository.findById(reqDto.getBoardId()).orElseThrow(()->new IllegalStateException("존재하지 않는 게시물입니다."));
        Comment parent = (reqDto.getParentId() == null)?
                null:commentRepository.findById(reqDto.getParentId()).orElseThrow(()->new IllegalStateException("존재하지 않는 댓글입니다."));
        Comment comment = reqDto.toEntity(user, board, parent);
        return commentRepository.save(comment);
    }


}
