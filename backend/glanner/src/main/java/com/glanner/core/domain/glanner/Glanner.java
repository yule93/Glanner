package com.glanner.core.domain.glanner;

import com.glanner.core.domain.base.BaseTimeEntity;
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
public class Glanner extends BaseTimeEntity {

    @Builder
    public Glanner(GroupBoard board) {
        this.board = board;
    }

    @Id @GeneratedValue
    @Column(name = "glanner_id")
    private Long id;

    @OneToMany(mappedBy = "glanner", fetch = FetchType.LAZY)
    private List<UserGlanner> userGlanners = new ArrayList<>();

    @OneToMany(mappedBy = "glanner", fetch = FetchType.LAZY)
    private List<DailyWorkGlanner> dailyworks = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_board_id")
    private GroupBoard board;

    @OneToMany(mappedBy = "glanner", fetch = FetchType.LAZY)
    private List<GlannerBoard> glannerBoards = new ArrayList<>();

    public void addUserGlanner(UserGlanner userGlanner){
        userGlanners.add(userGlanner);
        userGlanner.changeUserGlanner(this);
    }

    public void addDailyWorkGlanner(DailyWorkGlanner work){
        dailyworks.add(work);
        work.changeGlanner(this);
    }

    public void addGlannerBoard(GlannerBoard glannerBoard){
        glannerBoards.add(glannerBoard);
    }

}

