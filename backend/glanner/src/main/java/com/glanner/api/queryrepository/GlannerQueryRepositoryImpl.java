package com.glanner.api.queryrepository;

import com.glanner.core.domain.glanner.*;
import com.glanner.core.domain.glanner.QDailyWorkGlanner;
import com.glanner.core.domain.glanner.QGlanner;
import com.glanner.core.domain.glanner.QGlannerBoard;
import com.glanner.core.domain.glanner.QUserGlanner;
import com.glanner.core.domain.user.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GlannerQueryRepositoryImpl implements GlannerQueryRepository{

    private final JPAQueryFactory query;
    QUser user = new QUser("user1");
    QGlanner glanner = new QGlanner("glanner1");
    QUserGlanner userGlanner = new QUserGlanner("userGlanner1");
    QGlannerBoard glannerBoard = new QGlannerBoard("glannerBoard1");
    QDailyWorkGlanner dailyWorkGlanner = new QDailyWorkGlanner("dailyWorkGlanner1");

    @Override
    public Optional<Glanner> findById(Long id) {
        return Optional.ofNullable(query
                .select(glanner)
                .from(glanner)
                .leftJoin(glanner.host, user).fetchJoin()
                .leftJoin(glanner.userGlanners, userGlanner)
                .leftJoin(glanner.glannerBoards, glannerBoard)
                .leftJoin(glanner.works, dailyWorkGlanner)
                .where(glanner.id.eq(id))
                .fetchFirst());
    }

    @Override
    public Optional<Glanner> findByHostId(Long hostId) {
        return Optional.ofNullable(query
                .select(glanner)
                .from(glanner)
                .leftJoin(glanner.host, user).fetchJoin()
                .leftJoin(glanner.userGlanners, userGlanner)
                .leftJoin(glanner.glannerBoards, glannerBoard)
                .leftJoin(glanner.works, dailyWorkGlanner)
                .where(glanner.host.id.eq(hostId))
                .fetchFirst());
    }
}
