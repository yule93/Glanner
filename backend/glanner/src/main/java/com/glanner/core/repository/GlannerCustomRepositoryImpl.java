package com.glanner.core.repository;

import com.glanner.core.domain.glanner.*;
import com.glanner.core.domain.user.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GlannerCustomRepositoryImpl implements GlannerCustomRepository{

    private final JPAQueryFactory query;
    QUser user = new QUser("user1");
    QGlanner glanner = new QGlanner("glanner1");
    QUserGlanner userGlanner = new QUserGlanner("userGlanner1");
    QGlannerBoard glannerBoard = new QGlannerBoard("glannerBoard1");
    QDailyWorkGlanner dailyWorkGlanner = new QDailyWorkGlanner("dailyWorkGlanner1");

    @Override
    public Optional<Glanner> findRealById(Long id) {
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
}
