package com.glanner.core.domain.glanner;

import com.querydsl.core.annotations.QueryEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@QueryEntity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Glanner {
    @Id @GeneratedValue
    @Column(name = "glanner_id")
    private Long id;
}
