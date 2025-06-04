package com.example.HDSGTest.controller;

import com.example.HDSGTest.IService.IAuthenticationService;
import com.example.HDSGTest.dto.response.AuthenticationResponse;
import com.example.HDSGTest.dto.request.AuthenticationRequest;
import com.example.HDSGTest.dto.request.RefreshTokenRequest;
import com.example.HDSGTest.dto.response.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping(path = "/authenticate")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
    IAuthenticationService authenticationService;

    @Operation(summary = "Đăng nhập", description = "Đăng nhập và nhận access token, refresh token")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Đăng nhập thành công"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Sai thông tin đăng nhập")
    })
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> login(@Validated @RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = authenticationService.login(request);
        return ResponseEntity.ok(ApiResponse.<AuthenticationResponse>builder().code(0).message("Login success").entity(response).build());
    }

    @Operation(summary = "Làm mới access token", description = "Nhận access token mới từ refresh token")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Làm mới token thành công"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Refresh token không hợp lệ")
    })
    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> refreshToken(@RequestBody RefreshTokenRequest request) {
        AuthenticationResponse response = authenticationService.refreshToken(request.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.<AuthenticationResponse>builder().code(0).message("Refresh token success").entity(response).build());
    }

    @Operation(summary = "Đăng xuất", description = "Xóa refresh token của user hiện tại")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Đăng xuất thành công"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Token không hợp lệ")
    })
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestHeader("Authorization") String authHeader) {
        authenticationService.logout(authHeader);
        return ResponseEntity.ok(ApiResponse.<Void>builder().code(0).message("Logout success").build());
    }
}
