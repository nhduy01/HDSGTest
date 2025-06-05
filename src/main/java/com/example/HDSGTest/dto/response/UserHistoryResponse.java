package com.example.HDSGTest.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserHistoryResponse {
    UUID id;
    UUID userId;
    String username;
    String email;
    String fullName;
    String role;
    LocalDateTime originalCreatedAt;
    LocalDateTime originalUpdatedAt;
    LocalDateTime historyCreatedAt;
    String actionType;
    String updatedBy;
}
