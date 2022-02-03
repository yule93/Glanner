package com.glanner.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.glanner.api.dto.request.*;
import com.glanner.api.dto.response.FindGlannerWorkResDto;
import com.glanner.api.queryrepository.DailyWorkGlannerQueryRepository;
import com.glanner.api.queryrepository.GlannerQueryRepository;
import com.glanner.api.service.GlannerService;
import com.glanner.core.domain.glanner.Glanner;
import com.glanner.core.domain.user.User;
import com.glanner.core.repository.GlannerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * DB에 cherish8513@naver.com 유저가 있는 상황에서
 * Glanner 컨트롤러 접근 테스트
 */
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class GlannerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GlannerService glannerService;
    @MockBean
    private GlannerRepository glannerRepository;
    @MockBean
    private DailyWorkGlannerQueryRepository dailyWorkQueryRepository;

    @Test
    @WithUserDetails("cherish8513@naver.com")
    public void testCreateGlanner() throws Exception{
        //given

        //when
        mockMvc.perform(post("/api/glanner"))

        //then
                .andExpect(status().isOk());
        verify(glannerService, times(1)).saveGlanner(any(String.class));
    }

    @Test
    @WithUserDetails("cherish8513@naver.com")
    public void testDeleteGlanner() throws Exception{
        //given

        //when
        mockMvc.perform(delete("/api/glanner/{id}", 1))

        //then
                .andExpect(status().isOk());
        verify(glannerService, times(1)).deleteGlanner(any(Long.class));
    }

    @Test
    @WithUserDetails("cherish8513@naver.com")
    public void testFindHost() throws Exception{
        //given
        User user = User.builder()
                .build();
        Glanner glanner = Glanner.builder()
                .host(user)
                .build();
        when(glannerRepository.findRealById(1L)).thenReturn(Optional.of(glanner));

        //when
        mockMvc.perform(get("/api/glanner/{id}",1L))

        //then
                .andExpect(status().isOk())
                .andDo(print());

        verify(glannerRepository, times(1)).findRealById(anyLong());
    }

    @Test
    @WithUserDetails("cherish8513@naver.com")
    public void testAddUser() throws Exception{
        //given
        AddUserToGlannerReqDto reqDto = new AddUserToGlannerReqDto("cherish8514@naver.com");

        //when
        mockMvc.perform(post("/api/glanner/user")
                        .content(asJsonString(reqDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

        //then
                        .andExpect(status().isOk())
                        .andDo(print());
        verify(glannerService, times(1)).addUser(reqDto, "cherish8513@naver.com");
    }

    @Test
    @WithUserDetails("cherish8513@naver.com")
    public void testDeleteUser() throws Exception{
        //given

        //when
        mockMvc.perform(delete("/api/glanner/user/{glannerId}/{userId}", 1, 2))

        //then
                        .andExpect(status().isOk())
                        .andDo(print());
        verify(glannerService, times(1)).deleteUser(anyLong(), anyLong());
    }

    @Test
    @WithUserDetails("cherish8513@naver.com")
    public void testAddDailyWork() throws Exception{
        //given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime ldt = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), now.getHour(), now.getMinute());
        AddGlannerWorkReqDto reqDto = new AddGlannerWorkReqDto(1L, "title", "content", ldt, ldt);

        //when
        mockMvc.perform(post("/api/glanner/work")
                        .content(asJsonString(reqDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

        //then
                        .andExpect(status().isOk())
                        .andDo(print());
        verify(glannerService, times(1)).addDailyWork(reqDto);
    }

    @Test
    @WithUserDetails("cherish8513@naver.com")
    public void testFindDailyWork() throws Exception{
        //given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime ldt = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), now.getHour(), now.getMinute());

        List<FindGlannerWorkResDto> response = new ArrayList<>();
        response.add(new FindGlannerWorkResDto("title", "content", ldt, ldt));
        response.add(new FindGlannerWorkResDto("title", "content", ldt, ldt));


        //when
        when(dailyWorkQueryRepository.findByGlannerIdWithDate(1L, ldt, ldt)).thenReturn(response);
        mockMvc.perform(get("/api/glanner/work/{glannerId}/{startTime}/{endTime}", 1L, ldt, ldt))

        //then
                    .andExpect(status().isOk())
                    .andDo(print());
    }

    public static String asJsonString(final Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
