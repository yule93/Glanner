package com.glanner.core.domain.user;

import com.glanner.core.domain.base.BaseTimeEntity;
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
public class Log extends BaseTimeEntity {

    @Builder
    public Log(Long id, User user, String content, String url) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.url = url;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    @OneToOne(mappedBy = "schedule", fetch = FetchType.LAZY)
    private User user;

    @Column
    private String content;

    @Column
    private String url;
}
