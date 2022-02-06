package com.glanner.api.queryrepository;

import com.glanner.api.dto.response.FindPlannerWorkResDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserQueryRepository {
    public void deleteAllWorksByScheduleId(Long scheduleId);
    public List<FindPlannerWorkResDto> findDailyWorks(Long scheduleId, LocalDateTime month);
    public Optional<FindPlannerWorkResDto> findDailyWork(Long workId);
}
