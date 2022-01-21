package com.glanner.core.domain.user;

import com.glanner.api.queryrepository.UserQueryRepository;
import com.glanner.core.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

/**
 * cherish8513@naver.com 회원은 이미 등록되어있는 상태
 */
@SpringBootTest
@Transactional(readOnly = true)
public class UserFindTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserQueryRepository userQueryRepository;

    @Test
    public void testFindUser() throws Exception{
        //given

        //when
        User user = userRepository.findByEmail("cherish8513@naver.com").orElseThrow(() -> new IllegalStateException("없는 회원 입니다."));

        //then
        assertThat(user.getName()).isEqualTo("JeongJooHeon");
    }

    @Test
    public void testFindUserSchedule() throws Exception{
        //given

        //when
        User user = userQueryRepository.findByEmail("cherish8513@naver.com").orElseThrow(() -> new IllegalStateException("없는 회원 입니다."));

        //then
        assertThat(user.getSchedule().getUser()).isEqualTo(user);
    }

    /**
     * cherish8513@naver.com 유저는 3시간 분량의 일을 6개 가지고 있는 상황
     */
    @Test
    public void testFindUserDailyWork() throws Exception{
        //given

        //when
        User user = userQueryRepository.findByEmail("cherish8513@naver.com").orElseThrow(() -> new IllegalStateException("없는 회원 입니다."));

        //then
        assertThat(user.getSchedule().getWorks().get(0).getContent()).isEqualTo("something1");
        assertThat(user.getSchedule().getWorks().get(1).getContent()).isEqualTo("something2");
        assertThat(user.getSchedule().getWorks().get(2).getContent()).isEqualTo("something3");
        assertThat(user.getSchedule().getWorks().get(3).getContent()).isEqualTo("something4");
        assertThat(user.getSchedule().getWorks().get(4).getContent()).isEqualTo("something5");
        assertThat(user.getSchedule().getWorks().get(5).getContent()).isEqualTo("something6");
    }
}
