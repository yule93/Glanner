package com.glanner.core.domain.board;

import com.glanner.core.domain.base.BaseTimeEntity;
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
public class NoticeBoard extends BaseTimeEntity {
    @Builder
    public NoticeBoard(String title, String content, String fileUrls, int count, User user) {
        this.title = title;
        this.content = content;
        this.fileUrls = fileUrls;
        this.count = count;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_board_id")
    private Long id;
    private String title;
    private String content;
    private String fileUrls;
    private int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void changeContent(String content) {
        this.content = content;
    }
}