package com.example.HDSGTest.dto.request;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class RefreshTokenRequest {
    @NotBlank(message = "Refresh token là bắt buộc")
    private String refreshToken;
} 