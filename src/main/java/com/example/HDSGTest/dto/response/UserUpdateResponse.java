package com.example.HDSGTest.dto.response;


import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserUpdateResponse {
    private UUID id;
    private String username;
    private String fullName;
    private String email;
    private String avatarUrl;
}
