package com.example.HDSGTest.service;

import com.example.HDSGTest.Exception.AppException;
import com.example.HDSGTest.Exception.ErrorCode;
import com.example.HDSGTest.IService.IUserService;
import com.example.HDSGTest.dto.request.UserCreateRequest;
import com.example.HDSGTest.dto.request.UserUpdateRequest;
import com.example.HDSGTest.dto.response.UserResponseDTO;
import com.example.HDSGTest.dto.response.UserUpdateResponse;
import com.example.HDSGTest.entity.User;
import com.example.HDSGTest.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(user -> {
            UserResponseDTO dto = new UserResponseDTO();
            dto.setId(user.getId());
            dto.setUsername(user.getUsername());
            dto.setEmail(user.getEmail());
            dto.setFullName(user.getFullName());
            dto.setAvatarUrl("/users/" + user.getId() + "/avatar");
            return dto;
        }).collect(Collectors.toList());
    }

    public UserResponseDTO getCurrentUserInfo() {
        User user = getCurrentUser();
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setAvatarUrl("/users/" + user.getId() + "/avatar");
        return dto;
    }


    @Transactional
    public User createUser(UserCreateRequest request, MultipartFile avatarFile) throws IOException {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setRole("ROLE_USER");

        if (avatarFile != null && !avatarFile.isEmpty()) {
            user.setAvatar(avatarFile.getBytes());
        }

        return userRepository.save(user);
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
    }

    public UserUpdateResponse updateProfile(MultipartFile uploadedImage,UserUpdateRequest request) {
        User user = getCurrentUser();

        if (request.getFullName() != null && !request.getFullName().isBlank()) {
            user.setFullName(request.getFullName());
        }
        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            user.setEmail(request.getEmail());
        }
        if (uploadedImage != null && !uploadedImage.isEmpty()) {
            try {
                user.setAvatar(uploadedImage.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Cannot load image", e);
            }
        }

        userRepository.save(user);

        return UserUpdateResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .build();
    }

    public double compareImages(byte[] img1Bytes, byte[] img2Bytes) throws IOException {

        Mat img1 = Imgcodecs.imdecode(new MatOfByte(img1Bytes), Imgcodecs.IMREAD_COLOR);
        Mat img2 = Imgcodecs.imdecode(new MatOfByte(img2Bytes), Imgcodecs.IMREAD_COLOR);

        if (img1.empty() || img2.empty()) {
            throw new IOException("One of the images is empty or invalid!");
        }

        // Resize về cùng kích thước để so sánh
        Size size = new Size(256, 256);
        Imgproc.resize(img1, img1, size);
        Imgproc.resize(img2, img2, size);

        // Chuyển sang grayscale
        Mat gray1 = new Mat();
        Mat gray2 = new Mat();
        Imgproc.cvtColor(img1, gray1, Imgproc.COLOR_BGR2GRAY);
        Imgproc.cvtColor(img2, gray2, Imgproc.COLOR_BGR2GRAY);

        // Tính histogram cho ảnh xám
        Mat hist1 = new Mat();
        Mat hist2 = new Mat();

        Imgproc.calcHist(Arrays.asList(gray1), new MatOfInt(0), new Mat(), hist1, new MatOfInt(256), new MatOfFloat(0,256));
        Imgproc.calcHist(Arrays.asList(gray2), new MatOfInt(0), new Mat(), hist2, new MatOfInt(256), new MatOfFloat(0,256));

        Core.normalize(hist1, hist1, 0, 1, Core.NORM_MINMAX);
        Core.normalize(hist2, hist2, 0, 1, Core.NORM_MINMAX);

        // So sánh histogram, trả về similarity
        return Imgproc.compareHist(hist1, hist2, Imgproc.CV_COMP_CORREL);
    }

    public void changePassword(String newPassword,MultipartFile uploadedImage)throws IOException {
        User user = getCurrentUser();
        double match = compareImages(uploadedImage.getBytes(),user.getAvatar());
        if(match>= 0.85) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        }
        else {
            new AppException(ErrorCode.IMAGE_NOT_MATCH);
        }

    }

}
