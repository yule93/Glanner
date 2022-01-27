package com.glanner.core.domain.glanner;

import com.glanner.core.domain.board.Board;
import com.glanner.core.domain.user.User;
import com.querydsl.core.annotations.QueryEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@QueryEntity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupBoard extends Board {

    @Builder
    public GroupBoard(User user, String title, String content, String interests, int count) {
        super(title, content, count, user);
        this.interests = interests;
    }
    private String interests;

    public void changeGroupBoard(String title, String content, String interests, String fileUrls){
        this.interests = interests;
    }
}
