package com.example.HDSGTest.controller;

import com.example.HDSGTest.dto.request.UserCreateRequest;
import com.example.HDSGTest.dto.request.UserUpdateRequest;
import com.example.HDSGTest.dto.response.UserResponseDTO;
import com.example.HDSGTest.dto.response.UserUpdateResponse;
import com.example.HDSGTest.entity.User;
import com.example.HDSGTest.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
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

    @PutMapping
    public ResponseEntity<UserUpdateResponse> updateProfile(@RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(userService.updateProfile(request));
    }

    @PostMapping(value = "/compare",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> compareImages(
            @RequestParam("uploadedImage") MultipartFile uploadedImage,
            @RequestParam("newPassword") String newPassword)
        throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Nếu dùng JwtAuthenticationToken
        if (!(authentication instanceof JwtAuthenticationToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        Jwt jwt = ((JwtAuthenticationToken) authentication).getToken();
        String userIdStr = jwt.getClaim("userId"); // hoặc "sub" tùy token của bạn lưu userId ở đâu
        UUID userId = UUID.fromString(userIdStr);

        byte[] avatarImage = userService.getUserAvatarById(userId);
        double similarity = userService.compareImages(uploadedImage.getBytes(), avatarImage);

        if (similarity >= 0.85) {
            userService.changePasswordById(userId, newPassword);
            return ResponseEntity.ok("Password changed successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Face does not match.");
        }

    }
}
