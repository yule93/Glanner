package com.glanner.core.domain.user;

import com.glanner.api.exception.UserNotFoundException;
import com.glanner.api.queryrepository.UserQueryRepository;
import com.glanner.core.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserDeleteTest {

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
                .role(UserRoleStatus.ROLE_USER)
                .schedule(null)
                .build();

        DailyWorkSchedule work = createWork("something1");
        DailyWorkSchedule work2 = createWork("something2");
        DailyWorkSchedule work3 = createWork("something3");
        DailyWorkSchedule work4 = createWork("something4");
        DailyWorkSchedule work5 = createWork("something5");
        DailyWorkSchedule work6 = createWork("something6");

        Schedule schedule = Schedule.builder()
                .user(null)
                .build();

        schedule.addDailyWork(work);
        schedule.addDailyWork(work2);
        schedule.addDailyWork(work3);
        schedule.addDailyWork(work4);
        schedule.addDailyWork(work5);
        schedule.addDailyWork(work6);
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
    public void testDeleteUserAllInfo() throws Exception{
        //given
        User findUser = userRepository.findByEmail("cherish8513@naver.com").orElseThrow(UserNotFoundException::new);

        //when
        userRepository.delete(findUser);

        //then
        assertThatThrownBy(() -> {
            userRepository.findByEmail("cherish8513@naver.com").orElseThrow(() -> new IllegalStateException("없는 유저 입니다."));
        }).isInstanceOf(IllegalStateException.class).hasMessage("없는 유저 입니다.");
    }

    @Test
    public void testQueryDeleteUserAllInfo() throws Exception{
        //given
        User findUser = userQueryRepository.findByEmail("cherish8513@naver.com").orElseThrow(UserNotFoundException::new);
        Long scheduleId = findUser.getSchedule().getId();

        //when
        userQueryRepository.deleteAllWorksByScheduleId(scheduleId);
        userRepository.delete(findUser);
        //then
        assertThatThrownBy(() -> {
            userRepository.findByEmail("cherish8513@naver.com").orElseThrow(() -> new IllegalStateException("없는 유저 입니다."));
        }).isInstanceOf(IllegalStateException.class).hasMessage("없는 유저 입니다.");
    }


    public DailyWorkSchedule createWork(String content){
        return DailyWorkSchedule.builder()
                .content(content)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusHours(3))
                .title("테스트하기")
                .build();
    }
}
