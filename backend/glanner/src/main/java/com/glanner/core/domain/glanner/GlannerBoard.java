package com.glanner.core.domain.glanner;

import com.glanner.core.domain.board.Board;
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
public class GlannerBoard extends Board {

    @Builder
    public GlannerBoard(User user, Glanner glanner, String title, String content, int count) {
        super(title, content, count, user);
        this.glanner = glanner;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "glanner_id")
    private Glanner glanner;

}
