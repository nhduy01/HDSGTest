package com.example.HDSGTest.service;

import com.example.HDSGTest.dto.request.UserCreateRequest;
import com.example.HDSGTest.dto.request.UserUpdateRequest;
import com.example.HDSGTest.dto.response.UserResponse;
import com.example.HDSGTest.dto.response.UserUpdateResponse;
import com.example.HDSGTest.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();

    UserResponse getCurrentUserInfo();

    User createUser(UserCreateRequest request, MultipartFile avatarFile) throws IOException;

    UserUpdateResponse updateProfile(MultipartFile uploadedImage, UserUpdateRequest request);

    double compareImages(byte[] img1Bytes, byte[] img2Bytes) throws IOException;

    void changePassword(String newPassword, MultipartFile uploadedImage) throws IOException;
}
