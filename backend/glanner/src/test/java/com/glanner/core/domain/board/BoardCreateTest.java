package com.glanner.core.domain.board;

import com.glanner.core.domain.user.Schedule;
import com.glanner.core.domain.user.User;
import com.glanner.core.domain.user.UserRoleStatus;
import com.glanner.core.repository.FreeBoardRepository;
import com.glanner.core.repository.NoticeBoardRepository;
import com.glanner.core.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Commit
public class BoardCreateTest {
    @Autowired
    private NoticeBoardRepository noticeBoardRepository;
    @Autowired
    private FreeBoardRepository freeBoardRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void createUser(){
        User user = User.builder()
                .phoneNumber("010-6575-2938")
                .email("cherish8513@naver.com")
                .name("JeongJooHeon")
                .password("1234")
                .role(UserRoleStatus.ROLE_USER)
                .build();

        Schedule schedule = Schedule.builder()
                .build();
        user.changeSchedule(schedule);
        userRepository.save(user);
    }

    @Test
    public void testCreateNoticeBoard() throws Exception{
        User findUser = userRepository.findByEmail("cherish8513@naver.com").orElseThrow(() -> new IllegalStateException("없는 회원 입니다."));
        // given
        NoticeBoard noticeBoard = NoticeBoard.boardBuilder()
            .title("공지사항이요")
            .content("이거 꼭 읽어야합니다. 공지사항이니까요.")
            .user(findUser)
            .build();

        // when
        NoticeBoard savedNoticeBoard = noticeBoardRepository.save(noticeBoard);

        // then
        assertThat(savedNoticeBoard.getTitle()).isEqualTo("공지사항이요");
        assertThat(savedNoticeBoard.getContent()).isEqualTo("이거 꼭 읽어야합니다. 공지사항이니까요.");
        assertThat(savedNoticeBoard.getCount()).isEqualTo(0);
    }

    @Test
    public void testCreateFreeBoard() throws Exception{
        // given
        FreeBoard freeBoard = FreeBoard.boardBuilder()
                .title("제목이에요")
                .content("내용입니다")
                .user(null)
                .build();

        // when
        FreeBoard savedFreeBoard = freeBoardRepository.save(freeBoard);

        // then
        assertThat(savedFreeBoard.getTitle()).isEqualTo("제목이에요");
        assertThat(savedFreeBoard.getContent()).isEqualTo("내용입니다");
        assertThat(savedFreeBoard.getLikeCount()).isEqualTo(0);
        assertThat(savedFreeBoard.getDislikeCount()).isEqualTo(0);
        assertThat(savedFreeBoard.getCount()).isEqualTo(0);
    }

    @Test
    public void testCreateComment() throws Exception{
        //given
        FreeBoard freeBoard = FreeBoard.boardBuilder()
                .title("제목이에요")
                .content("내용입니다")
                .user(null)
                .build();

        Comment comment1 = Comment.builder()
                .user(null)
                .content("안녕?")
                .parent(null)
                .board(freeBoard)
                .build();

        Comment comment2 = Comment.builder()
                .user(null)
                .content("대댓")
                .parent(comment1)
                .board(freeBoard)
                .build();

        freeBoard.addComment(comment1);
        freeBoard.addComment(comment2);

//        when
        FreeBoard savedFreeBoard = freeBoardRepository.save(freeBoard);

//        then
        assertThat(savedFreeBoard.getComments().get(0)).isEqualTo(comment1);
        assertThat(savedFreeBoard.getComments().get(1)).isEqualTo(comment2);
    }
}
