package com.glanner.core.domain.board;

import com.glanner.core.domain.user.User;
import com.querydsl.core.annotations.QueryEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@QueryEntity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeBoard extends Board {
    @Builder
    public NoticeBoard(String title, String content, int count, User user) {
        super(title, content, count, user);
    }


}

