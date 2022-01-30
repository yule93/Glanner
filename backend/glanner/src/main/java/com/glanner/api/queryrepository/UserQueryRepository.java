package com.glanner.api.queryrepository;

import com.glanner.core.domain.user.User;

import java.util.Optional;

public interface UserQueryRepository {
    public void deleteAllWorksByScheduleId(Long scheduleId);
}
