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
    private GlannerQueryRepository glannerQueryRepository;
    @MockBean
    private DailyWorkGlannerQueryRepository dailyWorkQueryRepository;

    @Test
    @WithUserDetails("cherish8513@naver.com")
    public void testCreateGlanner() throws Exception{
        //given

        //when
        mockMvc.perform(post("/api/glanner/save"))

        //then
                .andExpect(status().isOk());
        verify(glannerService, times(1)).saveGlanner(any(String.class));
    }

    @Test
    @WithUserDetails("cherish8513@naver.com")
    public void testDeleteGlanner() throws Exception{
        //given
        DeleteGlannerReqDto reqDto = new DeleteGlannerReqDto(1L);

        //when
        mockMvc.perform(delete("/api/glanner/delete")
                .content(asJsonString(reqDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))

        //then
                .andExpect(status().isOk());
        verify(glannerService, times(1)).deleteGlanner(any(DeleteGlannerReqDto.class));
    }

    @Test
    @WithUserDetails("cherish8513@naver.com")
    public void testFindHost() throws Exception{
        //given
        FindGlannerHostReqDto reqDto = new FindGlannerHostReqDto(1L);
        User user = User.builder()
                .build();
        Glanner glanner = Glanner.builder()
                .host(user)
                .build();
        when(glannerQueryRepository.findById(reqDto.getGlannerId())).thenReturn(Optional.of(glanner));

        //when
        mockMvc.perform(get("/api/glanner/get-host-id")
                .content(asJsonString(reqDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))

        //then
                .andExpect(status().isOk())
                .andDo(print());

        verify(glannerQueryRepository, times(1)).findById(reqDto.getGlannerId());
    }

    @Test
    @WithUserDetails("cherish8513@naver.com")
    public void testAddUser() throws Exception{
        //given
        AddUserToGlannerReqDto reqDto = new AddUserToGlannerReqDto("cherish8514@naver.com");

        //when
        mockMvc.perform(post("/api/glanner/add-user")
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
        DeleteUserFromGlannerReqDto reqDto = new DeleteUserFromGlannerReqDto("cherish8514@naver.com", 1L);

        //when
        mockMvc.perform(delete("/api/glanner/delete-user")
                        .content(asJsonString(reqDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

        //then
                        .andExpect(status().isOk())
                        .andDo(print());
        verify(glannerService, times(1)).deleteUser(reqDto);
    }

    @Test
    @WithUserDetails("cherish8513@naver.com")
    public void testAddDailyWork() throws Exception{
        //given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime ldt = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), now.getHour(), now.getMinute());
        AddGlannerWorkReqDto reqDto = new AddGlannerWorkReqDto(1L, "title", "content", ldt, ldt);

        //when
        mockMvc.perform(post("/api/glanner/add-work")
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
        FindGlannerWorkReqDto reqDto = new FindGlannerWorkReqDto(1L, ldt, ldt);

        List<FindGlannerWorkResDto> response = new ArrayList<>();
        response.add(new FindGlannerWorkResDto("title", "content", ldt, ldt));
        response.add(new FindGlannerWorkResDto("title", "content", ldt, ldt));


        //when
        when(dailyWorkQueryRepository.findByGlannerIdWithDate(1L, ldt, ldt)).thenReturn(response);
        mockMvc.perform(get("/api/glanner/find-work")
                    .content(asJsonString(reqDto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))

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
