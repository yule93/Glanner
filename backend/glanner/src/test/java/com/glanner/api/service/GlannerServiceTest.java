package com.glanner.api.service;

import com.glanner.api.dto.request.AddUserToGlannerReqDto;
import com.glanner.api.dto.request.DeleteGlannerWorkReqDto;
import com.glanner.api.dto.request.DeleteUserFromGlannerReqDto;
import com.glanner.api.dto.request.UpdateGlannerWorkReqDto;
import com.glanner.api.exception.UserNotFoundException;
import com.glanner.api.queryrepository.GlannerQueryRepository;
import com.glanner.api.queryrepository.UserQueryRepository;
import com.glanner.core.domain.glanner.DailyWorkGlanner;
import com.glanner.core.domain.glanner.Glanner;
import com.glanner.core.domain.glanner.UserGlanner;
import com.glanner.core.domain.user.DailyWorkSchedule;
import com.glanner.core.domain.user.Schedule;
import com.glanner.core.domain.user.User;
import com.glanner.core.domain.user.UserRoleStatus;
import com.glanner.core.repository.DailyWorkGlannerRepository;
import com.glanner.core.repository.GlannerRepository;
import com.glanner.core.repository.UserGlannerRepository;
import com.glanner.core.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class GlannerServiceTest {

    @Autowired
    private GlannerRepository glannerRepository;
    @Autowired
    private UserQueryRepository userQueryRepository;
    @Autowired
    private GlannerQueryRepository glannerQueryRepository;
    @Autowired
    private DailyWorkGlannerRepository dailyWorkGlannerRepository;

    // save user
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager em;

    @BeforeEach
    public void init(){
        createUser();
    }

    @Test
    public void testCreateGlanner() throws Exception{
        //given
       User findUser = getUser(userQueryRepository.findByEmail("cherish8513@naver.com"));


        //when
        Glanner glanner = Glanner.builder()
                .host(findUser)
                .build();

        UserGlanner userGlanner = UserGlanner.builder()
                .user(findUser)
                .build();

        glanner.addUserGlanner(userGlanner);

        Glanner savedGlanner = glannerRepository.save(glanner);

        //then
        assertThat(savedGlanner.getHost()).isEqualTo(findUser);
        assertThat(savedGlanner.getUserGlanners().get(0).getUser()).isEqualTo(findUser);
    }

    @Test
    public void testDeleteGlanner() throws Exception{
        //given
        User findUser = getUser(userQueryRepository.findByEmail("cherish8513@naver.com"));
        Glanner glanner = Glanner.builder()
                .host(findUser)
                .build();

        UserGlanner userGlanner = UserGlanner.builder()
                .user(findUser)
                .build();

        glanner.addUserGlanner(userGlanner);

        Glanner savedGlanner = glannerRepository.save(glanner);

        //when
        Glanner findGlanner = getGlanner(glannerQueryRepository.findById(savedGlanner.getId()));

        glannerRepository.delete(findGlanner);

        //then
        User afterDeleteFindUser = getUser(userQueryRepository.findByEmail("cherish8513@naver.com"));
        assertThat(afterDeleteFindUser.getUserGlanners().size()).isEqualTo(0);
        assertThatThrownBy(() -> {
            getGlanner(glannerQueryRepository.findById(savedGlanner.getId()));
        }).isInstanceOf(IllegalArgumentException.class).hasMessage("글래너가 존재하지 않습니다.");
    }

    @Test
    public void testAddUser() throws Exception{
        //given
        User findUser = getUser(userQueryRepository.findByEmail("cherish8513@naver.com"));
        Glanner glanner = Glanner.builder()
                .host(findUser)
                .build();

        UserGlanner userGlanner = UserGlanner.builder()
                .user(findUser)
                .build();

        glanner.addUserGlanner(userGlanner);

        Glanner savedGlanner = glannerRepository.save(glanner);

        User anotherUser = User.builder()
                .email("cherish8514@naver.com")
                .role(UserRoleStatus.ROLE_USER)
                .build();

        Schedule schedule = Schedule.builder()
                .build();
        anotherUser.changeSchedule(schedule);
        User savedAnotherUser = userRepository.save(anotherUser);

        int size = 5;
        AddUserToGlannerReqDto reqDto = new AddUserToGlannerReqDto("cherish8514@naver.com");

        //when
        findUser = getUser(userQueryRepository.findByEmail("cherish8513@naver.com"));
        User findAnotherUser = getUser(userQueryRepository.findByEmail(reqDto.getEmail()));
        Glanner findGlanner = getGlanner(glannerQueryRepository.findByHostId(findUser.getId()));
        if (findGlanner.getUserGlanners().size() >= size){
            throw new IllegalStateException("회원 수가 가득 찼습니다.");
        }
        UserGlanner userGlanner1 = UserGlanner.builder()
                .user(findAnotherUser)
                .build();
        findGlanner.addUserGlanner(userGlanner1);

        //then
        assertThat(findAnotherUser.getUserGlanners().get(0).getGlanner()).isEqualTo(findGlanner);
        assertThat(findGlanner.getUserGlanners().size()).isEqualTo(2);
    }

    @Test
    public void testDeleteUser() throws Exception{
        //given
        //== glanner 생성 ==//
        User findUser = getUser(userQueryRepository.findByEmail("cherish8513@naver.com"));
        Glanner glanner = Glanner.builder()
                .host(findUser)
                .build();

        UserGlanner userGlanner = UserGlanner.builder()
                .user(findUser)
                .build();

        glanner.addUserGlanner(userGlanner);

        //== 참여 유저 생성 ==//
        User anotherUser = User.builder()
                .email("cherish8514@naver.com")
                .role(UserRoleStatus.ROLE_USER)
                .build();

        Schedule schedule = Schedule.builder()
                .build();

        anotherUser.changeSchedule(schedule);

        User savedAnotherUser = userRepository.save(anotherUser);

        //== 글래너에 참여 유저 추가 ==//
        UserGlanner userGlanner1 = UserGlanner.builder()
                .user(savedAnotherUser)
                .build();

        glanner.addUserGlanner(userGlanner1);
        Glanner savedGlanner = glannerRepository.save(glanner);

        DeleteUserFromGlannerReqDto reqDto = new DeleteUserFromGlannerReqDto("cherish8514@naver.com", savedGlanner.getId());

        //when
        //== 글래너에 호스트와 유저 1명이 있는 상황 ==//
        //== 유저 1명이 가지고 있는 글래너는 1개 ==//
        User attendingUser = getUser(userQueryRepository.findByEmail(reqDto.getEmail()));
        Glanner findGlanner = getGlanner(glannerQueryRepository.findById(reqDto.getGlannerId()));
        int size = findGlanner.getUserGlanners().size();
        for (int i = 0; i < size; i++) {
            if(findGlanner.getUserGlanners().get(i).getUser().getId().equals(attendingUser.getId())){
                findGlanner.getUserGlanners().get(i).delete();
                break;
            }
        }

        //then
        User deleteAfterUser = getUser(userQueryRepository.findByEmail(reqDto.getEmail()));
        assertThat(deleteAfterUser.getUserGlanners().size()).isEqualTo(0);
    }

    @Test
    public void deleteDailyWork() throws Exception{
        //given
        User findUser = getUser(userQueryRepository.findByEmail("cherish8513@naver.com"));
        Glanner glanner = Glanner.builder()
                .host(findUser)
                .build();

        UserGlanner userGlanner = UserGlanner.builder()
                .user(findUser)
                .build();

        glanner.addUserGlanner(userGlanner);
        glanner.addDailyWork(DailyWorkGlanner.builder().build());

        Glanner savedGlanner = glannerRepository.save(glanner);
        Long workId = savedGlanner.getWorks().get(0).getId();
        DeleteGlannerWorkReqDto reqDto = new DeleteGlannerWorkReqDto(savedGlanner.getId(), workId);

        //when
        Glanner findGlanner = getGlanner(glannerQueryRepository.findById(reqDto.getGlannerId()));
        DailyWorkGlanner deleteWork = dailyWorkGlannerRepository.findById(reqDto.getWorkId()).orElseThrow(IllegalArgumentException::new);
        findGlanner.getWorks().remove(deleteWork);

        //then
        assertThat(findGlanner.getWorks().size()).isEqualTo(0);
    }

    @Test
    public void updateDailyWork() throws Exception{
        //given
        User findUser = getUser(userQueryRepository.findByEmail("cherish8513@naver.com"));
        Glanner glanner = Glanner.builder()
                .host(findUser)
                .build();

        UserGlanner userGlanner = UserGlanner.builder()
                .user(findUser)
                .build();

        glanner.addUserGlanner(userGlanner);
        glanner.addDailyWork(DailyWorkGlanner.builder().build());

        Glanner savedGlanner = glannerRepository.save(glanner);
        Long workId = savedGlanner.getWorks().get(0).getId();
        UpdateGlannerWorkReqDto reqDto = new UpdateGlannerWorkReqDto(workId, "title", null, LocalDateTime.now(), LocalDateTime.now());

        //when
        DailyWorkGlanner updateWork = dailyWorkGlannerRepository.findById(reqDto.getWorkId()).orElseThrow(IllegalArgumentException::new);
        updateWork.changeDailyWork(reqDto.getStartTime(), reqDto.getEndTime(), reqDto.getTitle(), reqDto.getContent());

        //then
        assertThat(updateWork.getTitle()).isEqualTo("title");
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

    public User getUser(Optional<User> user){
        return user.orElseThrow(UserNotFoundException::new);
    }

    public Glanner getGlanner(Optional<Glanner> glanner){
        return glanner.orElseThrow(() -> new IllegalArgumentException("글래너가 존재하지 않습니다."));
    }
}
