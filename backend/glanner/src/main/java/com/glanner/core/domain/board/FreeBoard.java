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
    public FreeBoard(String title, String content, int likeCount, int disLikeCount, int count, User user) {
        super(title, content, count, user);
        this.likeCount = likeCount;
        this.disLikeCount = disLikeCount;
    }

    private int likeCount;
    private int disLikeCount;

    public void updateCount(String type){
        if(type.equals("LIKE")) this.likeCount++;
        else if(type.equals("DISLIKE")) this.disLikeCount++;
    }
}
