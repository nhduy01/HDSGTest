package com.example.HDSGTest.repository;

import com.example.HDSGTest.entity.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserHistoryRepository  extends JpaRepository<UserHistory, UUID> {
    List<UserHistory> findAllByUserId(UUID userId);
}
