package com.example.HDSGTest.service;

import com.example.HDSGTest.dto.response.UserHistoryResponse;

import java.util.List;
import java.util.UUID;

public interface UserHistoryService {

    List<UserHistoryResponse> findAllByUserId(UUID userId);
} 