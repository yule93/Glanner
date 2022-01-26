package com.glanner.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.glanner.api.dto.request.*;
import com.glanner.api.service.BoardService;
import com.glanner.core.domain.board.FreeBoard;
import com.glanner.core.domain.board.NoticeBoard;
import com.glanner.core.repository.FreeBoardRepository;
import com.glanner.core.repository.NoticeBoardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class BoardControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BoardService boardService;
    @MockBean
    private FreeBoardRepository freeBoardRepository;
    @MockBean
    private NoticeBoardRepository noticeBoardRepository;

    @Test
    @WithUserDetails("cherish8513@naver.com")
    public void testGetFreeBoards() throws Exception{
        //given
        List<FreeBoard> response = new ArrayList<>();
        response.add(new FreeBoard("title", "content", 0, 0, 0, null));
        response.add(new FreeBoard("title1", "content2", 1, 2, 0, null));

        when(freeBoardRepository.findAll()).thenReturn(response);

        //when
        mockMvc.perform(get("/api/board/getFreeBoard"))

        //then
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithUserDetails("cherish8513@naver.com")
    public void testGetFreeBoard() throws Exception{
        //given
        FreeBoard response = new FreeBoard("title1", "content2", 1, 2, 0, null);

        when(freeBoardRepository.findById(1L)).thenReturn(Optional.of(response));

        //when
        mockMvc.perform(get("/api/board/getFreeBoard/{boardId}",1))

                //then
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithUserDetails("cherish8513@naver.com")
    public void testGetNoticeBoards() throws Exception{
        //given
        List<NoticeBoard> response = new ArrayList<>();
        response.add(new NoticeBoard("title", "content", 0, null));
        response.add(new NoticeBoard("title1", "content2", 2, null));

        when(noticeBoardRepository.findAll()).thenReturn(response);

        //when
        mockMvc.perform(get("/api/board/getNoticeBoard"))

                //then
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithUserDetails("cherish8513@naver.com")
    public void testGetNoticeBoard() throws Exception{
        //given
        NoticeBoard response = new NoticeBoard("title1", "content2", 2, null);

        when(noticeBoardRepository.findById(1L)).thenReturn(Optional.of(response));

        //when
        mockMvc.perform(get("/api/board/getNoticeBoard/{boardId}",1))

                //then
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithUserDetails("cherish8513@naver.com")
    public void testSaveFreeBoard() throws Exception{
        //given
        BoardSaveReqDto reqDto = new BoardSaveReqDto("제목","내용");

        //when
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/board/saveFreeBoard")
                        .content(asJsonString(reqDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

        //then
                        .andExpect(status().isOk())
                        .andDo(print());

        verify(boardService, times(1)).saveFreeBoard("cherish8513@naver.com", reqDto, null);
    }

    @Test
    @WithUserDetails("cherish8513@naver.com")
    public void testSaveNoticeBoard() throws Exception{
        //given
        BoardSaveReqDto reqDto = new BoardSaveReqDto("제목","내용");
        MockMultipartFile multipartFile1 = new MockMultipartFile("files", "imagefile.jpeg", "image/jpeg", "<<jpeg data>>".getBytes());
        MockMultipartFile multipartFile2 = new MockMultipartFile("files", "imagefile.jpeg", "image/jpeg", "<<jpeg data>>".getBytes());
        List<MultipartFile> files = new ArrayList<>();
        files.add(multipartFile1);
        files.add(multipartFile2);

        //when
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/board/saveNoticeBoard")
                        .file(multipartFile1)
                        .file(multipartFile2)
                        .content(asJsonString(reqDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

        //then
                .andExpect(status().isOk())
                .andDo(print());


        verify(boardService, times(1)).saveNoticeBoard("cherish8513@naver.com", reqDto, files);
    }

    @Test
    @WithUserDetails("cherish8513@naver.com")
    public void testEditBoard() throws Exception{
        //given
        BoardUpdateReqDto reqDto = new BoardUpdateReqDto("제목수정","내용수정",null);

        //when
        mockMvc.perform(put("/api/board/editBoard/{boardId}","1")
                        .content(asJsonString(reqDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

        //then
                .andExpect(status().isOk())
                .andDo(print());

        verify(boardService, times(1)).editBoard(1L, reqDto);
    }

    @Test
    @WithUserDetails("cherish8513@naver.com")
    public void testDeleteBoard() throws Exception{
        //given
        //when
        mockMvc.perform(delete("/api/board/deleteBoard/{boardId}","1"))

        //then
                .andExpect(status().isOk())
                .andDo(print());

        verify(boardService, times(1)).deleteBoard(1L);
    }

    @Test
    @WithUserDetails("cherish8513@naver.com")
    public void testAddComment() throws Exception{
        //given
        BoardAddCommentReqDto reqDto = new BoardAddCommentReqDto(1L, null, "제목");

        //when
        mockMvc.perform(post("/api/board/addComment")
                        .content(asJsonString(reqDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                //then
                .andExpect(status().isOk())
                .andDo(print());

        verify(boardService, times(1)).addComment("cherish8513@naver.com", reqDto);
    }

    @Test
    @WithUserDetails("cherish8513@naver.com")
    public void testEditComment() throws Exception{
        //given
        BoardUpdateCommentReqDto reqDto = new BoardUpdateCommentReqDto("댓글 수정");

        //when
        mockMvc.perform(put("/api/board/editComment/{commentId}","1")
                        .content(asJsonString(reqDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                //then
                .andExpect(status().isOk())
                .andDo(print());

        verify(boardService, times(1)).editComment(1L, reqDto);
    }

    @Test
    @WithUserDetails("cherish8513@naver.com")
    public void testDeleteComment() throws Exception{
        //given
        //when
        mockMvc.perform(delete("/api/board/deleteComment/{commentId}","1"))

                //then
                .andExpect(status().isOk())
                .andDo(print());

        verify(boardService, times(1)).deleteComment(1L);
    }

    @Test
    @WithUserDetails("cherish8513@naver.com")
    public void testUpdateCount() throws Exception{
        //given
        BoardCountReqDto reqDto = new BoardCountReqDto("LIKE");

        //when
        mockMvc.perform(put("/api/board/updateCount/{boardId}","1")
                        .content(asJsonString(reqDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                //then
                .andExpect(status().isOk())
                .andDo(print());

        verify(boardService, times(1)).updateCount(1L, reqDto);
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