package com.glanner.api.queryrepository;

import com.glanner.api.dto.response.FindFreeBoardResDto;
import com.glanner.api.dto.response.FindNoticeBoardResDto;
import com.glanner.core.domain.board.QNoticeBoard;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.glanner.core.domain.board.QFreeBoard.freeBoard;
import static com.glanner.core.domain.board.QNoticeBoard.*;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeBoardQueryRepositoryImpl implements NoticeBoardQueryRepository{

    private final JPAQueryFactory query;

    @Override
    public Optional<FindNoticeBoardResDto> findById(Long id) {
        return Optional.ofNullable(query
                .select(Projections.constructor(FindNoticeBoardResDto.class,
                        noticeBoard.title,
                        noticeBoard.content,
                        noticeBoard.count))
                .from(noticeBoard)
                .where(noticeBoard.id.eq(id))
                .fetchOne());
    }

    @Override
    public List<FindNoticeBoardResDto> findPage(int offset, int limit) {
        return query
                .select(Projections.constructor(FindNoticeBoardResDto.class,
                        noticeBoard.title,
                        noticeBoard.content,
                        noticeBoard.count))
                .from(noticeBoard)
                .orderBy(noticeBoard.createdDate.desc())
                .offset(offset)
                .limit(limit)
                .fetch();
    }
}
