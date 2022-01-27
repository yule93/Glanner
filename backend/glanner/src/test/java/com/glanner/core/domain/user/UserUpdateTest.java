package com.glanner.core.domain.user;

import com.glanner.core.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

/**
 * 특정 유저 속성 변경 단위 테스트
 */
@SpringBootTest
@Transactional
public class UserUpdateTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

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
