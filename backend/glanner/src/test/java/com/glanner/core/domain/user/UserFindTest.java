package com.glanner.core.domain.user;

import com.glanner.api.queryrepository.UserQueryRepository;
import com.glanner.core.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

/**
 * 특정 유저 찾기 단위 테스트
 */
@SpringBootTest
@Transactional
public class UserFindTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserQueryRepository userQueryRepository;

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

        DailyWorkSchedule workSchedule = DailyWorkSchedule.builder()
                .content("hard")
                .title("work")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusHours(3))
                .build();
        schedule.addDailyWork(workSchedule);
        user.changeSchedule(schedule);
        userRepository.save(user);
    }

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
        assertThat(user.getSchedule().getWorks().get(0).getContent()).isEqualTo("hard");
    }
}
