package com.example.HDSGTest.dto.response;

import java.util.UUID;
import lombok.Data;

@Data
public class UserResponseDTO {
    private UUID id;
    private String username;
    private String email;
    private String fullName;
    private String avatarUrl;
}
