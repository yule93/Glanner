package com.glanner.core.domain.user;

import com.glanner.core.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Commit
class UserCreateTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testCreateUser() throws Exception{
        //given
        User user = User.builder()
                .phoneNumber("010-6575-2938")
                .email("cherish8513@naver.com")
                .name("JeongJooHeon")
                .interests("#난그게재밌더라강식당다시보기#")
                .password("1234")
                .role(UserRoleStatus.ROLE_USER)
                .schedule(null)
                .build();
        //when
        User savedUser = userRepository.save(user);

        //then
        assertThat(savedUser.getPhoneNumber()).isEqualTo("010-6575-2938");
        assertThat(savedUser.getEmail()).isEqualTo("cherish8513@naver.com");
        assertThat(savedUser.getName()).isEqualTo("JeongJooHeon");
        assertThat(savedUser.getInterests()).isEqualTo("#난그게재밌더라강식당다시보기#");
        assertThat(savedUser.getPassword()).isEqualTo("1234");
        assertThat(savedUser.getRole()).isEqualTo(UserRoleStatus.ROLE_USER);
    }
    
    @Test
    public void testAddScheduleTest() throws Exception{
        //given
        User user = User.builder()
                .phoneNumber("010-6575-2938")
                .email("cherish8513@naver.com")
                .name("JeongJooHeon")
                .interests("#난그게재밌더라강식당다시보기#")
                .password("1234")
                .role(UserRoleStatus.ROLE_USER)
                .schedule(null)
                .build();

        Schedule schedule = Schedule.builder()
                .user(null)
                .build();

        //when
        user.changeSchedule(schedule);
        User savedUser = userRepository.save(user);

        //then
        assertThat(savedUser.getSchedule()).isEqualTo(schedule);
    }

    @Test
    @Commit
    public void testAddWork() throws Exception{
        //given
        User user = User.builder()
                .phoneNumber("010-6575-2938")
                .email("cherish8513@naver.com")
                .name("JeongJooHeon")
                .interests("#난그게재밌더라강식당다시보기#")
                .password(passwordEncoder.encode("1234"))
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

        //when
        User savedUser = userRepository.save(user);

        //then
        assertThat(savedUser.getSchedule().getWorks().get(0)).isEqualTo(work);
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