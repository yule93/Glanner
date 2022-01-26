package com.glanner.api.queryrepository;

import com.glanner.core.domain.glanner.Glanner;

import java.util.Optional;

public interface GlannerQueryRepository {
    public Optional<Glanner> findById(Long id);
    public Optional<Glanner> findByHostId(Long hostId);
    public void deleteAllWorksById(Long id);
    public void deleteAllUserGlannerById(Long id);
}
