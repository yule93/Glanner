package com.glanner.api.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardUpdateReqDto {
    private String title;
    private String content;
    private String fileUrls;

    @Builder
    public BoardUpdateReqDto( String title, String content, String fileUrls) {
        this.title = title;
        this.content = content;
        this.fileUrls = fileUrls;
    }
}
