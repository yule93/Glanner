package com.glanner.core.domain.glanner;

import com.glanner.core.domain.base.BaseTimeEntity;
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
public class GroupBoard extends BaseTimeEntity {

    @Builder
    public GroupBoard(User user, String title, String content, List<Comment> comments, String interests, List<String> fileUrls, int count) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.comments = comments;
        this.interests = interests;
        this.fileUrls = fileUrls;
        this.count = count;
    }

    @Id @GeneratedValue
    @Column(name = "group_board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String title;

    @Column
    private String content;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private List<Comment> comments = new ArrayList<>();

    private String interests;

    private List<String> fileUrls = new ArrayList<>();

    private int count;
}
