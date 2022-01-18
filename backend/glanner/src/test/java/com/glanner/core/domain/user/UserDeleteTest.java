package com.glanner.core.domain.user;

import com.glanner.core.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
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
