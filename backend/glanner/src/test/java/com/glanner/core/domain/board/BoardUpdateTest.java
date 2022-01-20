package com.glanner.core.domain.board;

import com.glanner.core.domain.user.User;
import com.glanner.core.repository.CommentRepository;
import com.glanner.core.repository.FreeBoardRepository;
import com.glanner.core.repository.NoticeBoardRepository;
import com.glanner.core.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class BoardUpdateTest {
    @Autowired
    private NoticeBoardRepository noticeBoardRepository;
    @Autowired
    private FreeBoardRepository freeBoardRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private EntityManager em;

    @Test
    public void testUpdateNoticeBoard() throws Exception{
        // given
        NoticeBoard noticeBoard = noticeBoardRepository.findById((long)1).orElseThrow(
                () -> new IllegalStateException("없는 게시물 입니다.")
        );

        // when
        noticeBoard.changeContent("내용 업데이트");
        em.flush();
        NoticeBoard updatedNoticeBoard = noticeBoardRepository.findById((long)1).orElseThrow(
                () -> new IllegalStateException("없는 게시물 입니다.")
        );

        // then
        assertThat(updatedNoticeBoard.getContent()).isEqualTo("내용 업데이트");
    }

    @Test
    public void testUpdateFreeBoard() throws Exception{
        // given
        FreeBoard freeBoard = freeBoardRepository.findById((long)1).orElseThrow(
                () -> new IllegalStateException("없는 게시물 입니다.")
        );

        // when
        freeBoard.changeContent("내용 업데이트");
        em.flush();
        FreeBoard updatedFreeBoard = freeBoardRepository.findById((long)1).orElseThrow(
                () -> new IllegalStateException("없는 게시물 입니다.")
        );

        // then
        assertThat(updatedFreeBoard.getContent()).isEqualTo("내용 업데이트");
    }

    @Test
    public void testUpdateComment() throws Exception{
        // given
        Comment comment = commentRepository.findById((long)1).orElseThrow(
                () -> new IllegalStateException("없는 댓글 입니다.")
        );

        // when
        comment.changeContent("내용 업데이트");
        em.flush();
        Comment updatedComment = commentRepository.findById((long)1).orElseThrow(
                () -> new IllegalStateException("없는 댓글 입니다.")
        );

        // then
        assertThat(updatedComment.getContent()).isEqualTo("내용 업데이트");
    }
}
