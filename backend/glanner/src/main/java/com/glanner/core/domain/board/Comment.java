package com.glanner.core.domain.board;

import com.glanner.core.domain.base.BaseTimeEntity;
import com.glanner.core.domain.glanner.GlannerBoard;
import com.glanner.core.domain.glanner.GroupBoard;
import com.glanner.core.domain.user.User;
import com.querydsl.core.annotations.QueryEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@QueryEntity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Builder
    public Comment(String content, User user, Comment parent, FreeBoard freeBoard) {
        this.content = content;
        this.user = user;
        this.parent = parent;
        this.freeBoard = freeBoard;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "free_board_id")
    private FreeBoard freeBoard;

    public void changeContent(String content) {
        this.content = content;
    }

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "group_board_id")
//    private GroupBoard groupBoard;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "glanner_board_id")
//    private GlannerBoard glannerBoard;

}