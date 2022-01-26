package com.glanner.core.domain.board;

import com.glanner.core.domain.base.BaseTimeEntity;
import com.glanner.core.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public class Board extends BaseTimeEntity {
    public Board(String title, String content, String fileUrls, int count, User user) {
        this.title = title;
        this.content = content;
        this.fileUrls = fileUrls;
        this.count = count;
        this.user = user;
    }

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private  Long id;
    private String title;
    private String content;
    private String fileUrls;
    private int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public void changeContent(String content) {
        this.content = content;
    }

    public void addComment(Comment comment){
        comments.add(comment);
    }
}

