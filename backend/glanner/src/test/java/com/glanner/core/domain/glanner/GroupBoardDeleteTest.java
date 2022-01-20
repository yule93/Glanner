package com.glanner.core.domain.glanner;

import com.glanner.core.domain.user.User;
import com.glanner.core.repository.GroupBoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class GroupBoardDeleteTest {
    
    @Autowired
    private GroupBoardRepository groupBoardRepository;
    
    @Test
    public void testDeleteGroupBoard() throws Exception{
        //given
//        User findUser = userRepository.findByEmail("cherish8513@naver.com").orElseThrow(() -> new IllegalStateException("없는 유저 입니다."));
        GroupBoard findGroupBoard = groupBoardRepository.findByTitle("Title1").orElseThrow(() -> new IllegalStateException("없는 게시글 입니다. "));

        //when
//        int originalSize = findGroupBoard.getId();
//        GroupBoard deleteGroupBoard = findGroupBoard;
//        deleteGroupBoard.cancel();

        //then
    }
    
}
