package com.glanner.core.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.time.LocalDateTime;

@Embeddable
@Getter
@NoArgsConstructor
@ToString
public class Work {
    private LocalDateTime date;
    private String title;
    private String content;

    @Builder
    public Work(LocalDateTime date, String title, String content) {
        this.date = date;
        this.title = title;
        this.content = content;
    }
}
