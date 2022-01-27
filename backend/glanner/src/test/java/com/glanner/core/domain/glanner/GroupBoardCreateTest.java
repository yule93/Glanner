package com.glanner.core.domain.glanner;

import com.glanner.api.queryrepository.UserQueryRepository;
import com.glanner.core.domain.user.Schedule;
import com.glanner.core.domain.user.User;
import com.glanner.core.domain.user.UserRoleStatus;
import com.glanner.core.repository.GroupBoardRepository;
import com.glanner.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor
@Transactional
class GroupBoardCreateTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserQueryRepository userQueryRepository;

    @Autowired
    private GroupBoardRepository groupBoardRepository;

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
    public void testCreateGroupBoard() throws Exception{
        //given
        User findUser = userQueryRepository.findByEmail("cherish8513@naver.com").orElseThrow(() -> new IllegalStateException("없는 회원 입니다."));

        GroupBoard groupBoard = GroupBoard.builder()
                .content("group board test")
                .title("king")
                .count(0)
                .user(findUser)
                .build();

        //when
        GroupBoard savedGroupBoard = groupBoardRepository.save(groupBoard);

        //then
        assertThat(savedGroupBoard.getUser().getId()).isEqualTo(findUser.getId());
    }

}