package com.glanner.api.queryrepository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.glanner.core.domain.user.QDailyWorkSchedule.dailyWorkSchedule;
import static com.glanner.core.domain.user.QUser.user;


@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryRepositoryImpl implements UserQueryRepository{
    private final JPAQueryFactory query;

    @Override
    public void deleteAllWorksByScheduleId(Long scheduleId){
        query
                .delete(dailyWorkSchedule)
                .where(dailyWorkSchedule.in(
                        JPAExpressions
                                .select(dailyWorkSchedule)
                                .from(dailyWorkSchedule)
                                .where(dailyWorkSchedule.schedule.id.eq(scheduleId))
                ))
                .execute();
    }

    private BooleanExpression userEmailEq(String userEmail) {
        return userEmail != null ? user.email.eq(userEmail) : null;
    }
}
