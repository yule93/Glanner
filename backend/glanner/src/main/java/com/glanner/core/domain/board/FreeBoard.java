package com.glanner.core.domain.board;

import com.glanner.core.domain.base.BaseTimeEntity;
import com.glanner.core.domain.user.User;
import com.querydsl.core.annotations.QueryEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@QueryEntity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FreeBoard extends BaseTimeEntity {
    @Builder
    public FreeBoard(String title, String content, int likeCount, int disLikeCount, int count, String fileUrls, User user) {
        this.title = title;
        this.content = content;
        this.likeCount = likeCount;
        this.disLikeCount = disLikeCount;
        this.count = count;
        this.fileUrls = fileUrls;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "free_board_id")
    private Long id;
    private String title;
    private String content;
    private int likeCount;
    private int disLikeCount;
    private int count;
    private String fileUrls;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "freeBoard", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public void changeContent(String content) {
        this.content = content;
    }
    public void addComment(Comment comment) {
        comments.add(comment);
    }
}