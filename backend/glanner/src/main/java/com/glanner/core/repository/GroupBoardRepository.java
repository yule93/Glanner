package com.glanner.core.repository;

import com.glanner.core.domain.glanner.GroupBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupBoardRepository extends JpaRepository<GroupBoard, Long> {
    public Optional<GroupBoard> findByTitle(String title);
    public Optional<GroupBoard> findById(Long id);
}
