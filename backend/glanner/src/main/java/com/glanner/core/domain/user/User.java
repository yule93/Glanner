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
public class User extends BaseTimeEntity {

    @Builder
    public User(String name, String email, String password, String phoneNumber, UserRoleStatus role, Schedule schedule, String interests) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.schedule = schedule;
        this.interests = interests;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private UserRoleStatus role;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
    private String interests;

    public void changeSchedule(Schedule schedule){
        this.schedule = schedule;
        schedule.changeUser(this);
    }

    public void changePassword(String password){
        this.password = password;
    }

}
