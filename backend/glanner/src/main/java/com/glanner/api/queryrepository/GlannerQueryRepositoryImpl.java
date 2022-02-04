package com.glanner.api.queryrepository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import static com.glanner.core.domain.glanner.QDailyWorkGlanner.dailyWorkGlanner;
import static com.glanner.core.domain.glanner.QUserGlanner.userGlanner;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GlannerQueryRepositoryImpl implements GlannerQueryRepository{

    private final JPAQueryFactory query;

    @Override
    public void deleteAllWorksById(Long id) {
        query
                .delete(dailyWorkGlanner)
                .where(dailyWorkGlanner.in(
                        JPAExpressions
                                .select(dailyWorkGlanner)
                                .from(dailyWorkGlanner)
                                .where(dailyWorkGlanner.glanner.id.eq(id))
                ))
                .execute();
    }

    @Override
    public void deleteAllUserGlannerById(Long id) {
        query
                .delete(userGlanner)
                .where(userGlanner.in(
                        JPAExpressions
                                .select(userGlanner)
                                .from(userGlanner)
                                .where(userGlanner.glanner.id.eq(id))
                ))
                .execute();
    }


}
