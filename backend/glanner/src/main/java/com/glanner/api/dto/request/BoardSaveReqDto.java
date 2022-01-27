package com.glanner.api.dto.request;

import com.glanner.core.domain.board.FreeBoard;
import com.glanner.core.domain.board.NoticeBoard;
import com.glanner.core.domain.glanner.Glanner;
import com.glanner.core.domain.glanner.GlannerBoard;
import com.glanner.core.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardSaveReqDto {
    @NotNull
    private String title;
    @NotNull
    private String content;

    @Builder
    public BoardSaveReqDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public FreeBoard toFreeBoardEntity(User user){
        return FreeBoard.builder()
                .title(title)
                .content(content)
                .user(user)
                .count(0)
                .disLikeCount(0)
                .likeCount(0)
                .build();
    }

    public NoticeBoard toNoticeBoardEntity(User user){
        return NoticeBoard.builder()
                .title(title)
                .content(content)
                .user(user)
                .count(0)
                .build();
    }

    public GlannerBoard toGlannerBoardEntity(User user, Glanner glanner){
        return GlannerBoard.builder()
                .title(title)
                .content(content)
                .user(user)
                .count(0)
                .glanner(glanner)
                .build();
    }


}
