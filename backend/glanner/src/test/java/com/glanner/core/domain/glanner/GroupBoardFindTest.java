package com.glanner.core.domain.glanner;

import com.glanner.api.queryrepository.UserQueryRepository;
import com.glanner.core.domain.user.User;
import com.glanner.core.repository.GroupBoardRepository;
import com.glanner.core.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional(readOnly = true)
public class GroupBoardFindTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserQueryRepository userQueryRepository;

    @Autowired
    private GroupBoardRepository groupBoardRepository;

    @Test
    public void testFindGroupBoard() throws Exception{
        //given
        
        //when
//        User user = userRepository.findByEmail("cherish8513@naver.com").orElseThrow(() -> new IllegalStateException("없는 회원 입니다."));
        GroupBoard groupBoard = groupBoardRepository.findByTitle("그룹 보드 제목").orElseThrow(() -> new IllegalStateException("없는 게시글 입니다."));

        //then
        assertThat(groupBoard.getTitle()).isEqualTo(groupBoard);
    }

}
