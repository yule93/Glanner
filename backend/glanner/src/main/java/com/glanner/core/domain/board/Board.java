package com.glanner.core.domain.board;

import com.glanner.core.domain.base.BaseTimeEntity;
import com.glanner.core.domain.user.User;
import lombok.AccessLevel;
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
    public Board(String title, String content, int count, User user) {
        this.title = title;
        this.content = content;
        this.count = count;
        this.user = user;
    }

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private  Long id;
    private String title;
    private String content;
    private int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board", cascade = CascadeType.ALL)
    private List<FileInfo> fileInfos = new ArrayList<>();

    public void changeBoard(String title, String content, String fileUrls) {
        this.title = title;
        this.content = content;
    }

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public void updateCount(){
        this.count++;
    }

    public void addFile(FileInfo fileInfo) {
       this.fileInfos.add(fileInfo);
    }
}

