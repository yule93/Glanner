package com.glanner.api.dto.request;

import com.glanner.core.domain.board.FreeBoard;
import com.glanner.core.domain.board.NoticeBoard;
import com.glanner.core.domain.glanner.Glanner;
import com.glanner.core.domain.glanner.GlannerBoard;
import com.glanner.core.domain.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardSaveReqDto {
    private String title;
    private String content;
    private String fileUrls;

    @Builder
    public BoardSaveReqDto(String title, String content, String fileUrls) {
        this.title = title;
        this.content = content;
        this.fileUrls = fileUrls;
    }

    public FreeBoard toFreeBoardEntity(User user){
        return FreeBoard.builder()
                .title(title)
                .content(content)
                .fileUrls(fileUrls)
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
                .fileUrls(fileUrls)
                .user(user)
                .count(0)
                .build();
    }

    public GlannerBoard toGlannerBoardEntity(User user, Glanner glanner){
        return GlannerBoard.builder()
                .title(title)
                .content(content)
                .fileUrls(fileUrls)
                .user(user)
                .count(0)
                .glanner(glanner)
                .build();
    }
}
