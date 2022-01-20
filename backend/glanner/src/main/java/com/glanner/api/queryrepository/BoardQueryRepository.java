package com.glanner.api.queryrepository;

import com.glanner.core.domain.board.FreeBoard;
import com.glanner.core.domain.board.NoticeBoard;
import com.glanner.core.domain.user.User;

import java.util.Optional;

public interface BoardQueryRepository {
    public Optional<FreeBoard> findById(long Id);
}
