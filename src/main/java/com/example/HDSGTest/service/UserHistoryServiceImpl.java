package com.example.HDSGTest.service;

import com.example.HDSGTest.entity.UserHistory;
import com.example.HDSGTest.dto.response.UserHistoryResponse;
import com.example.HDSGTest.repository.UserHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserHistoryServiceImpl implements UserHistoryService {
    private final UserHistoryRepository userHistoryRepository;

    @Override
    public List<UserHistoryResponse> findAllByUserId(UUID userId) {
        try {
            List<UserHistory> histories = userHistoryRepository.findAllByUserId(userId);
            return histories.stream().map(history -> UserHistoryResponse.builder()
                    .id(history.getId())
                    .userId(history.getUserId())
                    .username(history.getUsername())
                    .email(history.getEmail())
                    .fullName(history.getFullName())
                    .role(history.getRole())
                    .originalCreatedAt(history.getOriginalCreatedAt())
                    .originalUpdatedAt(history.getOriginalUpdatedAt())
                    .historyCreatedAt(history.getHistoryCreatedAt())
                    .actionType(history.getActionType())
                    .updatedBy(history.getUpdatedBy())
                    .build())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Lỗi khi lấy lịch sử user với userId {}", userId, e);
            throw e;
        }
    }
} 