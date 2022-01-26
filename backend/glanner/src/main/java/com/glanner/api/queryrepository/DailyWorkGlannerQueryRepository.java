package com.glanner.api.queryrepository;

import com.glanner.api.dto.response.FindGlannerWorkResDto;
import com.glanner.core.domain.glanner.DailyWorkGlanner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DailyWorkGlannerQueryRepository {
    public List<FindGlannerWorkResDto> findByGlannerIdWithDate(Long glannerId, LocalDateTime startDate, LocalDateTime endDate);
}
