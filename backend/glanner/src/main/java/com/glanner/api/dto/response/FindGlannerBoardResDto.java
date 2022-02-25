package com.glanner.api.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class FindGlannerBoardResDto extends FindBoardResDto {
    private int commentCount;

    public FindGlannerBoardResDto(Long boardId, String userName, String userEmail, String title, String content, int count, LocalDateTime createdDate, int commentCount) {
        super(boardId, userName, userEmail, title, content, count, createdDate);
        this.commentCount = commentCount;
    }
}
