package com.glanner.api.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardCountReqDto {
    private Long boardId;
    private String countType;

    @Builder
    public BoardCountReqDto(Long boardId, String countType) {
        this.boardId = boardId;
        this.countType = countType;
    }
}
