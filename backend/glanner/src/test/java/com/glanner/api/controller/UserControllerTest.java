package com.glanner.api.controller;

import com.glanner.api.dto.request.UserSaveReqDto;
import com.glanner.api.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testJoin() throws Exception{
        //given
        UserSaveReqDto reqDto = UserSaveReqDto.builder()
                .email("cherish8513@naver.com")
                .name("싸피")
                .password("1234")
                .phoneNumber("010-1234-5678")
                .build();


        //when
        mockMvc.perform(post("/user/join"))

        //then
                .andExpect(status().isOk());
        verify(userService, times(1)).saveUser(any());
    }
}