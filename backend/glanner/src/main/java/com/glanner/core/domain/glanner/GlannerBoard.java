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
public class GlannerBoard extends BaseTimeEntity {

    @Builder
    public GlannerBoard(User user, Glanner glanner, String title, String content, String fileUrls, int count) {
        this.user = user;
        this.glanner = glanner;
        this.title = title;
        this.content = content;
        this.fileUrls = fileUrls;
        this.count = count;
    }

    @Id @GeneratedValue
    @Column(name = "glanner_board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "glanner_id")
    private Glanner glanner;

    private String title;
    private String content;
    private String fileUrls;
    private int count;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "glannerBoard", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment){
        comments.add(comment);
    }

}
