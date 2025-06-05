package com.example.HDSGTest.dto.response;

import java.util.UUID;
import java.util.List;
import com.example.HDSGTest.entity.UserHistory;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    UUID id;
    String username;
    String email;
    String fullName;
    String avatarUrl;
    List<UserHistoryResponse> histories;
} 