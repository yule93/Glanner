package com.glanner.core.domain.glanner;

import com.glanner.api.dto.response.FindGlannerWorkResDto;
import com.glanner.api.exception.UserNotFoundException;
import com.glanner.api.queryrepository.DailyWorkGlannerQueryRepository;
import com.glanner.api.queryrepository.GlannerQueryRepository;
import com.glanner.api.queryrepository.UserQueryRepository;
import com.glanner.core.domain.user.DailyWorkSchedule;
import com.glanner.core.domain.user.Schedule;
import com.glanner.core.domain.user.User;
import com.glanner.core.domain.user.UserRoleStatus;
import com.glanner.core.repository.GlannerRepository;
import com.glanner.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@RequiredArgsConstructor
@Transactional
class GlannerDeleteTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserQueryRepository userQueryRepository;
    @Autowired
    private GlannerRepository glannerRepository;
    @Autowired
    private GlannerQueryRepository glannerQueryRepository;
    @Autowired
    private DailyWorkGlannerQueryRepository dailyWorkGlannerQueryRepository;

    @Autowired
    private EntityManager em;
    private User host;

    @BeforeEach
    public void init(){
        createUser();
        createGlanner();
    }

    @Test
    public void glannerDeleteTest() throws Exception{
        //given
        User findUser = userQueryRepository.findByEmail("cherish8513@naver.com").orElseThrow(UserNotFoundException::new);
        Glanner glanner = findUser.getUserGlanners().get(0).getGlanner();
        Long glannerId = glanner.getId();

        //when
        glannerQueryRepository.deleteAllWorksById(glannerId);
        glannerQueryRepository.deleteAllUserGlannerById(glannerId);
        glannerRepository.delete(glanner);

        //then
        assertThatThrownBy(() -> {
            glannerRepository.findById(glannerId).orElseThrow(IllegalArgumentException::new);
        }).isInstanceOf(IllegalArgumentException.class);
        User deleteAfterUser = userQueryRepository.findByEmail("cherish8513@naver.com").orElseThrow(UserNotFoundException::new);
        assertThat(deleteAfterUser.getUserGlanners().size()).isEqualTo(0);
    }

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
        em.flush();
        em.clear();
    }

    public void createGlanner(){
        User user = userRepository.findByEmail("cherish8513@naver.com").orElseThrow(() -> new IllegalStateException("없는 회원 입니다."));

        Glanner glanner = Glanner.builder()
                .host(user)
                .build();

        UserGlanner userGlanner = UserGlanner.builder()
                .user(user)
                .build();

        DailyWorkGlanner dailyWorkGlanner = createDailyWork(1);
        DailyWorkGlanner dailyWorkGlanner2 = createDailyWork(2);
        DailyWorkGlanner dailyWorkGlanner3 = createDailyWork(3);
        DailyWorkGlanner dailyWorkGlanner4 = createDailyWork(4);
        DailyWorkGlanner dailyWorkGlanner5 = createDailyWork(5);

        glanner.addUserGlanner(userGlanner);

        Glanner savedGlanner = glannerRepository.save(glanner);
        glanner.addDailyWork(dailyWorkGlanner);
        glanner.addDailyWork(dailyWorkGlanner2);
        glanner.addDailyWork(dailyWorkGlanner3);
        glanner.addDailyWork(dailyWorkGlanner4);
        glanner.addDailyWork(dailyWorkGlanner5);
        em.flush();
        em.clear();
    }

    public DailyWorkGlanner createDailyWork(int hour){
        return DailyWorkGlanner
                .builder()
                .startDate(LocalDateTime.now().plusHours(hour))
                .endDate(LocalDateTime.now().plusHours(3 + hour))
                .build();
    }
}