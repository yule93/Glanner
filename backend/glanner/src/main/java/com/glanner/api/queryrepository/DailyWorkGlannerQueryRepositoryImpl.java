package com.glanner.api.queryrepository;

import com.glanner.api.dto.response.FindGlannerWorkResDto;
import com.glanner.core.domain.glanner.DailyWorkGlanner;
import com.glanner.core.domain.glanner.QDailyWorkGlanner;
import com.glanner.core.domain.glanner.QGlanner;
import com.glanner.core.domain.user.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
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
