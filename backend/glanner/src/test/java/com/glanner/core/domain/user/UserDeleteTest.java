package com.glanner.core.domain.user;

import com.glanner.core.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserDeleteTest {

    @Autowired
    private UserRepository userRepository;

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
    public void testDeleteUser() throws Exception{
        //given
        User findUser = userRepository.findByEmail("cherish8513@naver.com").orElseThrow(() -> new IllegalStateException("없는 유저 입니다."));

        //when
        userRepository.delete(findUser);

        //then
        assertThatThrownBy(() -> {
            userRepository.findByEmail("cherish8513@naver.com").orElseThrow(() -> new IllegalStateException("없는 유저 입니다."));
        }).isInstanceOf(IllegalStateException.class).hasMessage("없는 유저 입니다.");
    }

    @Test
    public void testDeleteUserDailyWork() throws Exception{
        //given
        User findUser = userRepository.findByEmail("cherish8513@naver.com").orElseThrow(() -> new IllegalStateException("없는 유저 입니다."));

        //when
        int originalSize = findUser.getSchedule().getWorks().size();
        DailyWorkSchedule deleteWork = findUser.getSchedule().getWorks().get(0);
        deleteWork.cancel();
        int currentSize = findUser.getSchedule().getWorks().size();

        //then
        assertThat(currentSize).isEqualTo(originalSize - 1);
    }
}
