package com.glanner.api.queryrepository;

import com.glanner.core.domain.user.User;

import java.util.Optional;

public interface UserQueryRepository {
    public Optional<User> findByEmail(String email);
}
