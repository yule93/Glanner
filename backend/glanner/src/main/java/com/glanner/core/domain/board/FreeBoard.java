package com.glanner.core.domain.board;

import com.glanner.core.domain.user.User;
import com.querydsl.core.annotations.QueryEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@QueryEntity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FreeBoard extends Board {
    @Builder
    public FreeBoard(String title, String content, int likeCount, int disLikeCount, int count, String fileUrls, User user) {
        super(title, content, fileUrls, count, user);
        this.likeCount = likeCount;
        this.disLikeCount = disLikeCount;
    }

    private int likeCount;
    private int disLikeCount;
}
