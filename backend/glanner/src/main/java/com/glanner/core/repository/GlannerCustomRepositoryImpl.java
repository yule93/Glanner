package com.glanner.core.repository;

import com.glanner.core.domain.glanner.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.glanner.core.domain.glanner.QDailyWorkGlanner.*;
import static com.glanner.core.domain.glanner.QGlanner.glanner;
import static com.glanner.core.domain.glanner.QGlannerBoard.*;
import static com.glanner.core.domain.glanner.QUserGlanner.*;
import static com.glanner.core.domain.user.QUser.*;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GlannerCustomRepositoryImpl implements GlannerCustomRepository{

    private final JPAQueryFactory query;

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
