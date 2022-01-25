package com.glanner.api.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardUpdateReqDto {
    private Long boardId;
    private String title;
    private String content;
    private String fileUrls;

    @Builder
    public BoardUpdateReqDto(Long boardId, String title, String content, String fileUrls) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.fileUrls = fileUrls;
    }
}
