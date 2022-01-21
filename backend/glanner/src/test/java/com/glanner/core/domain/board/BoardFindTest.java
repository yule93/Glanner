package com.glanner.core.domain.board;

import com.glanner.core.repository.FreeBoardRepository;
import com.glanner.core.repository.NoticeBoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional(readOnly = true)
public class BoardFindTest {
    @Autowired
    private FreeBoardRepository freeBoardRepository;
    @Autowired
    private NoticeBoardRepository noticeBoardRepository;

    @Test
    public void testFindNoticeBoard() throws Exception{
        //given

        //when
        NoticeBoard noticeBoard = noticeBoardRepository.findByTitleLike("%공지%")
                                    .orElseThrow(() -> new IllegalStateException("없는 게시물 입니다."));

        //then
        assertThat(noticeBoard.getTitle()).isEqualTo("공지사항이요");
    }

    @Test
    public void testFindFreeBoard() throws Exception{
        //given

        //when
        FreeBoard freeBoard = freeBoardRepository.findByTitleLike("%제목%")
                .orElseThrow(() -> new IllegalStateException("없는 게시물 입니다."));

        //then
        assertThat(freeBoard.getTitle()).isEqualTo("제목이에요");
        assertThat(freeBoard.getComments().get(0).getContent()).isEqualTo("안녕?");
        assertThat(freeBoard.getComments().get(1).getParent()).isEqualTo(freeBoard.getComments().get(0));
    }

}
