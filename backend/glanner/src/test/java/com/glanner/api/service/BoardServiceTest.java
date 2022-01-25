package com.glanner.api.service;

import com.glanner.api.dto.request.BoardAddCommentReqDto;
import com.glanner.api.dto.request.BoardSaveReqDto;
import com.glanner.api.dto.request.BoardUpdateReqDto;
import com.glanner.api.queryrepository.UserQueryRepository;
import com.glanner.core.domain.board.Board;
import com.glanner.core.domain.board.Comment;
import com.glanner.core.domain.board.FreeBoard;
import com.glanner.core.domain.board.NoticeBoard;
import com.glanner.core.domain.user.Schedule;
import com.glanner.core.domain.user.User;
import com.glanner.core.domain.user.UserRoleStatus;
import com.glanner.core.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Commit
class BoardServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserQueryRepository userQueryRepository;

    @Autowired
    private FreeBoardRepository freeBoardRepository;

    @Autowired
    private NoticeBoardRepository noticeBoardRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void createUser(){
        User user = User.builder()
                .phoneNumber("010-6575-2938")
                .email("cherish8513@naver.com")
                .name("JeongJooHeon")
                .interests("#난그게재밌더라강식당다시보기#")
                .password("1234")
                .role(UserRoleStatus.ROLE_USER)
                .build();

        Schedule schedule = Schedule.builder()
                .build();
        user.changeSchedule(schedule);
        userRepository.save(user);
    }

    @Test
    public void testSaveFreeBoard() throws Exception{
        //given
        User user = userQueryRepository.findByEmail("cherish8513@naver.com").orElseThrow(()->new IllegalStateException("존재하지 않는 회원입니다."));
        BoardSaveReqDto boardSaveReqDto = BoardSaveReqDto.builder()
                .title("제목")
                .content("내용")
                .fileUrls(null)
                .build();


        //when
        FreeBoard freeBoard = boardSaveReqDto.toFreeBoardEntity(user);
        FreeBoard savedFreeBoard = boardRepository.save(freeBoard);

        //then
        assertThat(savedFreeBoard.getTitle()).isEqualTo("제목");
        assertThat(savedFreeBoard.getContent()).isEqualTo("내용");
        assertThat(savedFreeBoard.getUser()).isEqualTo(user);
    }

    @Test
    public void testSaveNoticeBoard() throws Exception{
        //given
        User user = userQueryRepository.findByEmail("cherish8513@naver.com").orElseThrow(()->new IllegalStateException("존재하지 않는 회원입니다."));
        BoardSaveReqDto boardSaveReqDto = BoardSaveReqDto.builder()
                .title("제목")
                .content("내용")
                .fileUrls(null)
                .build();


        //when
        NoticeBoard noticeBoard = boardSaveReqDto.toNoticeBoardEntity(user);
        NoticeBoard savedNoticeBoard = boardRepository.save(noticeBoard);

        //then
        assertThat(savedNoticeBoard.getTitle()).isEqualTo("제목");
        assertThat(savedNoticeBoard.getContent()).isEqualTo("내용");
        assertThat(savedNoticeBoard.getUser()).isEqualTo(user);
    }

    @Test
    public void testUpdateBoard() throws Exception{
        //given
        User user = userQueryRepository.findByEmail("cherish8513@naver.com").orElseThrow(()->new IllegalStateException("존재하지 않는 회원입니다."));
        BoardSaveReqDto boardSaveReqDto = BoardSaveReqDto.builder()
                .title("제목")
                .content("내용")
                .fileUrls(null)
                .build();

        FreeBoard freeBoard = boardSaveReqDto.toFreeBoardEntity(user);
        freeBoardRepository.save(freeBoard);

        Board board = boardRepository.findByTitleLike("%제목%").orElseThrow(()->new IllegalStateException("없는 게시물 입니다."));
        BoardUpdateReqDto boardUpdateReqDto = BoardUpdateReqDto.builder()
                .boardId(board.getId())
                .title("제목 수정")
                .content("내용 수정")
                .fileUrls(null)
                .build();

        //when
        Board savedBoard = boardRepository.findById(board.getId()).orElseThrow(()->new IllegalStateException("없는 게시물 입니다."));
        savedBoard.changeBoard(boardUpdateReqDto.getTitle(), boardUpdateReqDto.getContent(), boardUpdateReqDto.getFileUrls());
        Board updatedBoard = boardRepository.save(savedBoard);

        //then
        assertThat(updatedBoard.getTitle()).isEqualTo("제목 수정");
        assertThat(updatedBoard.getContent()).isEqualTo("내용 수정");
        assertThat(updatedBoard.getFileUrls()).isEqualTo(null);
        assertThat(updatedBoard.getUser()).isEqualTo(savedBoard.getUser());
    }

    @Test
    public void testAddComment() throws Exception{
        //given
        User user = userQueryRepository.findByEmail("cherish8513@naver.com").orElseThrow(()->new IllegalStateException("존재하지 않는 회원입니다."));
        FreeBoard freeBoard = FreeBoard.builder()
                .title("제목이에요")
                .content("내용입니다")
                .likeCount(0)
                .disLikeCount(0)
                .count(0)
                .fileUrls(null)
                .user(user)
                .build();
        FreeBoard savedFreeBoard = boardRepository.save(freeBoard);

        BoardAddCommentReqDto boardAddCommentReqDto1 = BoardAddCommentReqDto.builder()
                .content("댓글이용")
                .parentId(null)
                .boardId(savedFreeBoard.getId())
                .build();

        //when
        Board board1 = boardRepository.findById(boardAddCommentReqDto1.getBoardId())
                .orElseThrow(()->new IllegalStateException("없는 게시물입니다."));
        Comment parent1 = (boardAddCommentReqDto1.getParentId() == null)?
                null:commentRepository.findById(boardAddCommentReqDto1.getParentId()).orElseThrow(()->new IllegalStateException("없는 댓글 입니다."));
        Comment comment1 = boardAddCommentReqDto1.toEntity(user, board1, parent1);
        Comment savedComment1 = commentRepository.save(comment1);

        // given: 대댓글
        BoardAddCommentReqDto boardAddCommentReqDto2 = BoardAddCommentReqDto.builder()
                .content("대댓글이용")
                .parentId(savedComment1.getId())
                .boardId(savedFreeBoard.getId())
                .build();

        // when: 대댓글
        Board board2 = boardRepository.findById(boardAddCommentReqDto2.getBoardId())
                .orElseThrow(()->new IllegalStateException("없는 게시물입니다."));
        Comment parent2 = (boardAddCommentReqDto2.getParentId() == null)?
                null:commentRepository.findById(boardAddCommentReqDto2.getParentId()).orElseThrow(()->new IllegalStateException("없는 댓글 입니다."));
        Comment comment2 = boardAddCommentReqDto2.toEntity(user, board2, parent2);
        Comment savedComment2 = commentRepository.save(comment2);

        //then
        assertThat(savedComment1.getUser()).isEqualTo(user);
        assertThat(savedComment2.getUser()).isEqualTo(savedComment1.getUser());
        assertThat(savedComment1.getBoard()).isEqualTo(board1);
        assertThat(savedComment2.getBoard()).isEqualTo(savedComment1.getBoard());
        assertThat(savedComment1.getContent()).isEqualTo("댓글이용");
        assertThat(savedComment2.getContent()).isEqualTo("대댓글이용");
        assertThat(savedComment2.getParent()).isEqualTo(savedComment1);

    }
}