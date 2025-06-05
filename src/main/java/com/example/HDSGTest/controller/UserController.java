package com.example.HDSGTest.controller;

import com.example.HDSGTest.IService.IUserService;
import com.example.HDSGTest.dto.request.UserCreateRequest;
import com.example.HDSGTest.dto.request.UserUpdateRequest;
import com.example.HDSGTest.dto.response.ApiResponse;
import com.example.HDSGTest.dto.response.UserResponseDTO;
import com.example.HDSGTest.dto.response.UserUpdateResponse;
import com.example.HDSGTest.entity.User;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.Operation;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Lấy tất cả người dùng")
    @GetMapping("/getall")
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.<List<UserResponseDTO>>builder().code(0).message("Lấy danh sách người dùng thành công").entity(users).build());
    }
    @Operation(summary = "Lấy thông tin người dùng hiện tại")
    @GetMapping()
    public ResponseEntity<ApiResponse<UserResponseDTO>> getCurrentUserInfo() {
        UserResponseDTO users = userService.getCurrentUserInfo();
        return ResponseEntity.ok(ApiResponse.<UserResponseDTO>builder().code(0).message("Lấy thông tin người dùng hiện tại thành công").entity(users).build());
    }

    @Operation(summary = "Tạo người dùng mới")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<User>> createUser(
            @RequestPart("user") UserCreateRequest userCreateRequest,
            @RequestPart(value = "avatar", required = false) MultipartFile avatarFile
    ) throws IOException {
        User createdUser = userService.createUser(userCreateRequest, avatarFile);
        return ResponseEntity.ok(ApiResponse.<User>builder().code(0).message("Tạo người dùng thành công").entity(createdUser).build());
    }

    @Operation(summary = "Cập nhật thông tin người dùng")
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<UserUpdateResponse>> updateProfile(
            @RequestPart("user") UserUpdateRequest request
            ,@RequestPart("avatar") MultipartFile avatarFile
            ) {
        UserUpdateResponse updated = userService.updateProfile(avatarFile,request);
        return ResponseEntity.ok(ApiResponse.<UserUpdateResponse>builder().code(0).message("Cập nhật thông tin người dùng thành công").entity(updated).build());
    }

    @Operation(summary = "Đổi mật khẩu bằng xác thực khuôn mặt")
    @PutMapping(value = "/compare",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<String>> changePassword(
            @RequestPart("avatar") MultipartFile avatarFile,
            @RequestPart("newPassword") String newPassword)
        throws IOException {
        userService.changePassword(newPassword,avatarFile);
        return ResponseEntity.ok(ApiResponse.<String>builder().code(0).message("Đổi mật khẩu thành công.").entity("Đổi mật khẩu thành công.").build());
    }
}
