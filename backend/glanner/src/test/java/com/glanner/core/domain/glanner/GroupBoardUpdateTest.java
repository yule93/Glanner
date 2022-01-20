package com.glanner.core.domain.glanner;

import com.glanner.core.domain.user.User;
import com.glanner.core.repository.GroupBoardRepository;
import com.glanner.core.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class GroupBoardUpdateTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupBoardRepository groupBoardRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void testChangeGroupBoard() throws Exception{
        //given
//        User findUser = userRepository.findByEmail("cherish8513@naver.com").orElseThrow(() -> new IllegalStateException("없는 유저 입니다."));
        GroupBoard findBoard = groupBoardRepository.findByTitle("Title3").orElseThrow(() -> new IllegalStateException("없는 게시글 입니다."));
//        System.out.println("findBoard: "+ findBoard);

        //when
//        GroupBoard updateGroupBoard = findBoard;
//        updateGroupBoard.changeGroupBoard("제목", "내용", "#관심사#", null);
        findBoard.changeGroupBoard("제목", "내용", "#관심사#", null);
        em.flush();
        GroupBoard updateGroupBoard = groupBoardRepository.findByTitle("제목").orElseThrow(() -> new IllegalStateException("없는 게시글 입니다."));

        //then
        assertThat(updateGroupBoard.getTitle()).isEqualTo("제목");

    }
}
