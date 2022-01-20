package com.glanner.api.queryrepository;

import com.glanner.core.domain.board.FreeBoard;
import com.glanner.core.domain.board.QComment;
import com.glanner.core.domain.board.QFreeBoard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardQueryRepositoryImpl implements BoardQueryRepository {
    private final JPAQueryFactory query;

    private QFreeBoard freeBoard= new QFreeBoard("freeboard");
    private QComment comment= new QComment("comment");
    @Override
    public Optional<FreeBoard> findById(long Id) {
        return Optional.ofNullable(query
                .select(freeBoard)
                .from(freeBoard)
                .join(freeBoard.comments, comment).fetchJoin()
                .where(freeBoard.id.eq(Id))
                .fetchOne()
        );
    }
}
