package com.glanner.api.controller;

import com.glanner.api.service.BoardService;
import com.glanner.core.domain.user.Schedule;
import com.glanner.core.domain.user.User;
import com.glanner.core.domain.user.UserRoleStatus;
import com.glanner.core.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest(BoardController.class)
class BoardControllerTest {
    private MockMvc mvc;

    @MockBean
    private BoardService boardService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp(){
        // MockMVC setting
        mvc = MockMvcBuilders.standaloneSetup(new BoardController(boardService))
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .build();

        // User setting
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
    public void testSaveFreeBoard() throws Exception{
        //given

        //when
        ResultActions actions =
                mvc.perform(
                        post("/api/board/saveFreeBoard")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content(
                                    "{"
                                        +" \"title\" : \"제목이에요\", "
                                        +" \"content\" : \"내용이에요\", "
                                        +"}"
                                )
                );

        //then
    }
}