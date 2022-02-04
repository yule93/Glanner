package com.glanner.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.glanner.api.dto.request.SaveFreeBoardReqDto;
import com.glanner.api.dto.response.FindFreeBoardResDto;
import com.glanner.api.dto.response.FindNoticeBoardResDto;
import com.glanner.api.queryrepository.FreeBoardQueryRepository;
import com.glanner.api.queryrepository.NoticeBoardQueryRepository;
import com.glanner.api.service.BoardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * DB에 cherish8513@naver.com 유저가 있는 상황에서
 * NoticeBoard 컨트롤러 접근 테스트
 */
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class NoticeBoardControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private NoticeBoardQueryRepository queryRepository;

    @Test
    @WithUserDetails("cherish8513@naver.com")
    public void testFindBoardOne() throws Exception{
        //given
        Long boardId = 1L;
        FindNoticeBoardResDto noticeBoardResDto = new FindNoticeBoardResDto("title", "content", 0);

        //when
        when(queryRepository.findById(boardId)).thenReturn(Optional.of(noticeBoardResDto));
        mockMvc.perform(get("/api/notice/{id}", boardId))

                //then
                .andExpect(status().isOk());
        verify(queryRepository, times(1)).findById(boardId);
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
