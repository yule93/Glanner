package com.glanner.core.repository;


import com.glanner.core.domain.user.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.glanner.core.domain.glanner.QUserGlanner.*;
import static com.glanner.core.domain.user.QSchedule.*;
import static com.glanner.core.domain.user.QUser.*;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserCustomRepositoryImpl implements UserCustomRepository {
    private final JPAQueryFactory query;

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(query
                .select(user)
                .from(user)
                .leftJoin(user.schedule, schedule).fetchJoin()
                .leftJoin(user.userGlanners, userGlanner)
                .where(userEmailEq(email))
                .fetchFirst());
    }

    private BooleanExpression userEmailEq(String userEmail) {
        return userEmail != null ? user.email.eq(userEmail) : null;
    }
}