package com.glanner.api.service;

import com.glanner.api.dto.request.BoardSaveReqDto;
import com.glanner.api.dto.request.BoardUpdateReqDto;
import com.glanner.api.queryrepository.UserQueryRepository;
import com.glanner.core.domain.board.Board;
import com.glanner.core.domain.board.FreeBoard;
import com.glanner.core.domain.board.NoticeBoard;
import com.glanner.core.domain.user.Schedule;
import com.glanner.core.domain.user.User;
import com.glanner.core.domain.user.UserRoleStatus;
import com.glanner.core.repository.BoardRepository;
import com.glanner.core.repository.FreeBoardRepository;
import com.glanner.core.repository.NoticeBoardRepository;
import com.glanner.core.repository.UserRepository;
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
}