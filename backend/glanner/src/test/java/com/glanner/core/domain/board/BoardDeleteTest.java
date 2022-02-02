package com.glanner.core.domain.board;

import com.glanner.core.repository.CommentRepository;
import com.glanner.core.repository.FreeBoardRepository;
import com.glanner.core.repository.NoticeBoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        NoticeBoard noticeBoard = noticeBoardRepository.findByTitleLike("%공지%").orElseThrow(() -> new IllegalStateException("없는 게시물 입니다."));

        //when
        noticeBoardRepository.delete(noticeBoard);

        //then
        assertThatThrownBy(() -> {
            noticeBoardRepository.findByTitleLike("%공지%").orElseThrow(() -> new IllegalStateException("없는 게시물 입니다."));
        }).isInstanceOf(IllegalStateException.class).hasMessage("없는 게시물 입니다.");
    }

    @Test
    public void testDeleteFreeBoard() throws Exception{
        //given
        FreeBoard freeBoard = freeBoardRepository.findByTitleLike("%제목%").orElseThrow(() -> new IllegalStateException("없는 게시물 입니다."));

        int commentCount = freeBoard.getComments().size();
        int preCommentSize = (int)commentRepository.count();

        //when
        freeBoardRepository.delete(freeBoard);
        int curCommentSize = (int)commentRepository.count();

        //then
        assertThatThrownBy(() -> {
            freeBoardRepository.findByTitleLike("%제목%").orElseThrow(() -> new IllegalStateException("없는 게시물 입니다."));
        }).isInstanceOf(IllegalStateException.class).hasMessage("없는 게시물 입니다.");

        assertThat(commentCount).isEqualTo(preCommentSize-curCommentSize);
    }

    @Test
    public void testDeleteComment() throws Exception{
        //given
        List<Comment> comments= commentRepository.findAll();

        //when
        for(Comment cmt:comments){ commentRepository.delete(cmt); }

        //then
        assertThat(commentRepository.count()).isEqualTo(0);
    }
}
