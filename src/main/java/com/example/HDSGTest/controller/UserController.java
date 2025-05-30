package com.example.HDSGTest.controller;

import com.example.HDSGTest.IService.IUserService;
import com.example.HDSGTest.dto.request.UserCreateRequest;
import com.example.HDSGTest.dto.request.UserUpdateRequest;
import com.example.HDSGTest.dto.response.UserResponseDTO;
import com.example.HDSGTest.dto.response.UserUpdateResponse;
import com.example.HDSGTest.entity.User;
import com.example.HDSGTest.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final IUserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getall")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @GetMapping()
    public ResponseEntity<UserResponseDTO> getCurrentUserInfo() {
        UserResponseDTO users = userService.getCurrentUserInfo();
        return ResponseEntity.ok(users);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<User> createUser(
            @RequestPart("user") UserCreateRequest userCreateRequest,
            @RequestPart(value = "avatar", required = false) MultipartFile avatarFile
    ) throws IOException {
        User createdUser = userService.createUser(userCreateRequest, avatarFile);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserUpdateResponse> updateProfile(
            @RequestPart("userinfo") UserUpdateRequest request
            ,@RequestPart("uploadedImage") MultipartFile uploadedImage
            ) {
        return ResponseEntity.ok(userService.updateProfile(uploadedImage,request));
    }

    @PutMapping(value = "/compare",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> changePassword(
            @RequestPart("uploadedImage") MultipartFile uploadedImage,
            @RequestPart("newPassword") String newPassword)
        throws IOException {
        userService.changePassword(newPassword,uploadedImage);
        return ResponseEntity.ok("Password changed successfully.");
    }
}
