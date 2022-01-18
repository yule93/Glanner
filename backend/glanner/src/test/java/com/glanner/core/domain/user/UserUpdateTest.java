package com.glanner.core.domain.user;

import com.glanner.core.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserUpdateTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void testChangeUserPassword() throws Exception{
        //given
        User findUser = userRepository.findByEmail("cherish8513@naver.com").orElseThrow(() -> new IllegalStateException("없는 유저 입니다."));

        //when
        findUser.changePassword("8513");
        em.flush();
        User updateUser = userRepository.findByEmail("cherish8513@naver.com").orElseThrow(() -> new IllegalStateException("없는 유저 입니다."));

        //then
        assertThat(updateUser.getPassword()).isEqualTo("8513");
    }

    @Test
    public void testChangeUserDailyWork() throws Exception{
        //given
        User findUser = userRepository.findByEmail("cherish8513@naver.com").orElseThrow(() -> new IllegalStateException("없는 유저 입니다."));

        //when
        DailyWorkSchedule work = findUser.getSchedule().getWorks().get(0);
        work.changeDailyWork(null, null, "today", "good");
        em.flush();
        User updateUser = userRepository.findByEmail("cherish8513@naver.com").orElseThrow(() -> new IllegalStateException("없는 유저 입니다."));

        //then
        assertThat(updateUser.getSchedule().getWorks().get(0).getContent()).isEqualTo("good");
    }
}
