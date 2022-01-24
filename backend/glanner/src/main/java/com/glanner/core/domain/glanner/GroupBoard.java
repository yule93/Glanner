package com.glanner.core.domain.glanner;

import com.glanner.core.domain.base.BaseTimeEntity;
import com.glanner.core.domain.board.Board;
import com.glanner.core.domain.board.Comment;
import com.glanner.core.domain.user.User;
import com.querydsl.core.annotations.QueryEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@QueryEntity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupBoard extends Board {

    @Builder
    public GroupBoard(User user, String title, String content, String interests, String fileUrls, int count) {
        super(title, content, fileUrls, count, user);
        this.interests = interests;
    }
    private String interests;

    public void changeGroupBoard(String title, String content, String interests, String fileUrls){
//        this.title = title;
//        this.content = content;
        this.interests = interests;
//        this.fileUrls = fileUrls;
    }
}
