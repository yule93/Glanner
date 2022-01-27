package com.glanner.api.queryrepository;

import com.glanner.api.dto.response.FindGlannerWorkResDto;
import com.glanner.core.domain.glanner.DailyWorkGlanner;
import com.glanner.core.domain.glanner.QDailyWorkGlanner;
import com.glanner.core.domain.glanner.QGlanner;
import com.glanner.core.domain.user.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DailyWorkGlannerQueryRepositoryImpl implements DailyWorkGlannerQueryRepository{

    private final JPAQueryFactory query;
    QUser user = new QUser("user1");
    QGlanner glanner = new QGlanner("glanner1");
    QDailyWorkGlanner dailyWorkGlanner = new QDailyWorkGlanner("dailyWorkGlanner1");

    @Override
    public List<FindGlannerWorkResDto> findByGlannerIdWithDate(Long glannerId, LocalDateTime startDate, LocalDateTime endDate) {
        return query
                .select(Projections.constructor(FindGlannerWorkResDto.class,
                        dailyWorkGlanner.title,
                        dailyWorkGlanner.content,
                        dailyWorkGlanner.startDate,
                        dailyWorkGlanner.endDate))

                .from(dailyWorkGlanner)
                .where(dailyWorkGlanner.glanner.id.eq(glannerId),
                        dailyWorkGlanner.startDate.after(startDate),
                        dailyWorkGlanner.endDate.before(endDate))
                .orderBy(dailyWorkGlanner.startDate.desc())
                .fetch();
    }


}
