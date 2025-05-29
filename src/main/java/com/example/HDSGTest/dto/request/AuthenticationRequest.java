package com.example.HDSGTest.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
//@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequest {
    @Size(min = 1, max = 20, message = "Username must be 8-20 characters long")
    private String username;
    @Size(min = 1, max = 20, message = "Password must be 8-20 characters long")
    private String password;
}
