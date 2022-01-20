package com.glanner.core.domain.board;

import com.glanner.core.domain.user.User;
import com.glanner.core.domain.user.UserRoleStatus;
import com.glanner.core.repository.CommentRepository;
import com.glanner.core.repository.FreeBoardRepository;
import com.glanner.core.repository.NoticeBoardRepository;
import com.glanner.core.repository.UserRepository;
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
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateNoticeBoard() throws Exception{
        // given
        NoticeBoard noticeBoard = NoticeBoard.builder()
            .title("공지사항이요")
            .content("이거 꼭 읽어야합니다. 공지사항이니까요.")
            .fileUrls(null)
            .count(0)
            .user(null)
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
        User user = userRepository.findByEmail("cherish8513@naver.com").orElseThrow(() -> new IllegalStateException("없는 유저 입니다."));

        FreeBoard freeBoard = FreeBoard.builder()
                .title("제목이에요")
                .content("내용입니다")
                .likeCount(0)
                .disLikeCount(0)
                .count(0)
                .fileUrls(null)
                .user(user)
                .build();

        // when
        FreeBoard savedFreeBoard = freeBoardRepository.save(freeBoard);

        // then
        assertThat(savedFreeBoard.getTitle()).isEqualTo("제목이에요");
        assertThat(savedFreeBoard.getContent()).isEqualTo("내용입니다");
        assertThat(savedFreeBoard.getLikeCount()).isEqualTo(0);
        assertThat(savedFreeBoard.getDisLikeCount()).isEqualTo(0);
        assertThat(savedFreeBoard.getCount()).isEqualTo(0);
        assertThat(savedFreeBoard.getUser()).isEqualTo(user);

    }

    @Test
    public void testCreateComment() throws Exception{
        //given
        FreeBoard freeBoard = FreeBoard.builder()
                .title("제목이에요")
                .content("내용입니다")
                .likeCount(0)
                .disLikeCount(0)
                .count(0)
                .fileUrls(null)
                .user(null)
                .build();

        Comment comment1 = Comment.builder()
                .user(null)
                .content("안녕?")
                .parent(null)
                .freeBoard(freeBoard)
                .build();

        Comment comment2 = Comment.builder()
                .user(null)
                .content("대댓")
                .parent(comment1)
                .freeBoard(freeBoard)
                .build();

        freeBoard.addComment(comment1);
        freeBoard.addComment(comment2);

//        when
        Comment savedComment1 = commentRepository.save(comment1);
        Comment savedComment2 = commentRepository.save(comment2);
        FreeBoard savedFreeBoard = freeBoardRepository.save(freeBoard);

//        then
        assertThat(savedFreeBoard.getComments().get(0)).isEqualTo(savedComment1);
        assertThat(savedFreeBoard.getComments().get(1)).isEqualTo(savedComment2);
    }
}
