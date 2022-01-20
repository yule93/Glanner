package com.glanner.core.domain.board;

import com.glanner.core.domain.user.User;
import com.glanner.core.repository.CommentRepository;
import com.glanner.core.repository.FreeBoardRepository;
import com.glanner.core.repository.NoticeBoardRepository;
import com.glanner.core.repository.UserRepository;
import com.mysql.cj.protocol.x.Notice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class BoardDeleteTest {
    @Autowired
    private NoticeBoardRepository noticeBoardRepository;
    @Autowired
    private FreeBoardRepository freeBoardRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void testDeleteNoticeBoard() throws Exception{
        //given
        NoticeBoard noticeBoard = noticeBoardRepository.findById((long)1).orElseThrow(() -> new IllegalStateException("없는 게시물 입니다."));

        //when
        noticeBoardRepository.delete(noticeBoard);

        //then
        assertThatThrownBy(() -> {
            noticeBoardRepository.findById((long)1).orElseThrow(() -> new IllegalStateException("없는 게시물 입니다."));
        }).isInstanceOf(IllegalStateException.class).hasMessage("없는 게시물 입니다.");
    }

    @Test
    public void testDeleteFreeBoard() throws Exception{
        //given
        FreeBoard freeBoard = freeBoardRepository.findById((long)1).orElseThrow(() -> new IllegalStateException("없는 게시물 입니다."));

        int commentCount = freeBoard.getComments().size();
        int preCommentSize = (int)commentRepository.count();

        //when
        freeBoardRepository.delete(freeBoard);
        int curCommentSize = (int)commentRepository.count();

        //then
        assertThatThrownBy(() -> {
            freeBoardRepository.findById((long)1).orElseThrow(() -> new IllegalStateException("없는 게시물 입니다."));
        }).isInstanceOf(IllegalStateException.class).hasMessage("없는 게시물 입니다.");

        assertThat(commentCount).isEqualTo(preCommentSize-curCommentSize);
    }

    @Test
    public void testDeleteComment() throws Exception{
        //given
        Comment comment = commentRepository.findById((long)1).orElseThrow(
                () -> new IllegalStateException("없는 댓글 입니다.")
        );

        //when
        commentRepository.delete(comment);

        //then
        assertThat(commentRepository.count()).isEqualTo(0);
    }
}
