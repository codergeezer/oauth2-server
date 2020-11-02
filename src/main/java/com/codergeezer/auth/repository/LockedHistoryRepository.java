package com.codergeezer.auth.repository;

import com.codergeezer.auth.entity.LockedHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author haidv
 * @version 1.0
 */
@Repository
public interface LockedHistoryRepository extends JpaRepository<LockedHistory, Long> {

    @Query("select l from LockedHistory l where l.activated = true and l.isLocked = true and l.username = ?1")
    Optional<LockedHistory> findByUsername(String username);
}
