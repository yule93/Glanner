package com.glanner.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.glanner.api.dto.request.SaveFreeBoardReqDto;
import com.glanner.api.dto.request.SaveGlannerBoardReqDto;
import com.glanner.api.service.BoardService;
import com.glanner.api.service.GlannerBoardService;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * DB에 cherish8513@naver.com 유저가 있는 상황에서
 * FreeBoard 컨트롤러 접근 테스트
 */
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class GlannerBoardControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GlannerBoardService glannerBoardService;
    @MockBean
    private BoardService boardService;

    @Test
    @WithUserDetails("cherish8513@naver.com")
    public void testSaveGlannerBoard() throws Exception{
        //given
        SaveGlannerBoardReqDto reqDto = new SaveGlannerBoardReqDto("title", "content", null, 1L);

        //when
        mockMvc.perform(post("/api/glanner-board/save")
                        .content(asJsonString(reqDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                //then
                .andExpect(status().isOk());
        verify(glannerBoardService, times(1))
                .saveGlannerBoard(eq("cherish8513@naver.com"), any(SaveGlannerBoardReqDto.class));
        verify(boardService, times(0)).saveBoard(anyString(), any(SaveGlannerBoardReqDto.class));
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
