package com.example.HDSGTest.dto.request;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Tên đăng nhập là bắt buộc")
    @Size(min = 1, max = 20, message = "Tên đăng nhập từ 1 đến 20 ký tự")
    private String username;
    @NotBlank(message = "Mật khẩu là bắt buộc")
    @Size(min = 1, max = 20, message = "Mật khẩu từ 1 đến 20 ký tự")
    private String password;
}
