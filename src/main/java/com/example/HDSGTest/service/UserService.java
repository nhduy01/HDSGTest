package com.example.HDSGTest.service;

import com.example.HDSGTest.Exception.AppException;
import com.example.HDSGTest.Exception.ErrorCode;
import com.example.HDSGTest.IService.IUserService;
import com.example.HDSGTest.dto.request.UserCreateRequest;
import com.example.HDSGTest.dto.request.UserUpdateRequest;
import com.example.HDSGTest.dto.response.UserResponseDTO;
import com.example.HDSGTest.dto.response.UserUpdateResponse;
import com.example.HDSGTest.entity.SystemSetting;
import com.example.HDSGTest.entity.User;
import com.example.HDSGTest.entity.UserHistory;
import com.example.HDSGTest.repository.SystemSettingRepository;
import com.example.HDSGTest.repository.UserHistoryRepository;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Slf4j
@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private UserHistoryRepository userHistoryRepository;
    private SystemSettingRepository systemSettingRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,UserHistoryRepository userHistoryRepository, SystemSettingRepository systemSettingRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userHistoryRepository = userHistoryRepository;
        this.systemSettingRepository = systemSettingRepository;
    }

    public List<UserResponseDTO> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            return users.stream().map(user -> {
                UserResponseDTO dto = new UserResponseDTO();
                dto.setId(user.getId());
                dto.setUsername(user.getUsername());
                dto.setEmail(user.getEmail());
                dto.setFullName(user.getFullName());
                if (user.getAvatar() != null) {
                    String base64 = Base64.getEncoder().encodeToString(user.getAvatar());
                    dto.setAvatarUrl("data:image/jpeg;base64," + base64);
                } else {
                    dto.setAvatarUrl(null);
                }
                return dto;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Lỗi khi lấy tất cả thông tin người dùng", e);
            throw e;
        }
    }

    public UserResponseDTO getCurrentUserInfo() {
        try {
            User user = getCurrentUser();
            UserResponseDTO dto = new UserResponseDTO();
            dto.setId(user.getId());
            dto.setUsername(user.getUsername());
            dto.setEmail(user.getEmail());
            dto.setFullName(user.getFullName());
            if (user.getAvatar() != null) {
                String base64 = Base64.getEncoder().encodeToString(user.getAvatar());
                dto.setAvatarUrl("data:image/jpeg;base64," + base64);
            } else {
                dto.setAvatarUrl(null);
            }
            return dto;
        } catch (Exception e) {
            log.error("Lỗi khi lấy thông tin người dùng hiện tại", e);
            throw e;
        }
    }


    @Transactional
    public User createUser(UserCreateRequest request, MultipartFile avatarFile) throws IOException {
        try {
            if (userRepository.existsByUsername(request.getUsername())) {
                throw new AppException(ErrorCode.USERNAME_EXISTS);
            }
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new AppException(ErrorCode.EMAIL_EXISTS);
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
        } catch (Exception e) {
            log.error("Lỗi khi tạo người dùng", e);
            throw e;
        }
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
    }

    public UserUpdateResponse updateProfile(MultipartFile uploadedImage,UserUpdateRequest request) {
        try {
            User user = getCurrentUser();
            UserHistory history = new UserHistory();
            history.setId(UUID.randomUUID());
            history.setUserId(user.getId());
            history.setUsername(user.getUsername());
            history.setEmail(user.getEmail());
            history.setPassword(user.getPassword());
            history.setFullName(user.getFullName());
            history.setAvatar(user.getAvatar());
            history.setRole(user.getRole());
            history.setOriginalCreatedAt(user.getCreatedAt());
            history.setOriginalUpdatedAt(user.getUpdatedAt());
            history.setHistoryCreatedAt(LocalDateTime.now());
            history.setActionType("UPDATE_PROFILE");
            history.setUpdatedBy(user.getUsername());

            userHistoryRepository.save(history);

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
                    throw new RuntimeException("Không thể tải ảnh lên", e);
                }
            }

            userRepository.save(user);

            return UserUpdateResponse.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .fullName(user.getFullName())
                    .email(user.getEmail())
                    .avatarUrl(user.getAvatar() != null
                            ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(user.getAvatar())
                            : null)
                    .build();
        } catch (Exception e) {
            log.error("Lỗi khi cập nhật thông tin người dùng", e);
            throw e;
        }
    }

    public double compareImages(byte[] img1Bytes, byte[] img2Bytes) throws IOException {
        try {
            Mat img1 = Imgcodecs.imdecode(new MatOfByte(img1Bytes), Imgcodecs.IMREAD_COLOR);
            Mat img2 = Imgcodecs.imdecode(new MatOfByte(img2Bytes), Imgcodecs.IMREAD_COLOR);

            if (img1.empty() || img2.empty()) {
                throw new IOException("Một trong hai ảnh bị rỗng hoặc không hợp lệ!");
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
        } catch (Exception e) {
            log.error("Lỗi khi so sánh ảnh", e);
            throw e;
        }
    }

    public void changePassword(String newPassword,MultipartFile uploadedImage)throws IOException {
        try {
            User user = getCurrentUser();

            SystemSetting faceMatchSetting = systemSettingRepository.findByKey("face_match").orElseThrow(() -> new AppException(ErrorCode.SETTING_NOT_FOUND));

            //chuyển thành %
            String valueStr = faceMatchSetting.getValue().replace("%", "").trim();
            double threshold = Double.parseDouble(valueStr) / 100.0;

            double match = compareImages(uploadedImage.getBytes(),user.getAvatar());
            if(match>= threshold) {
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
                log.info("Người dùng '{}' vừa đổi mật khẩu với mức độ khớp: {}%", user.getUsername(), String.format("%.2f", match * 100));
            }
            else {
                throw new AppException(ErrorCode.IMAGE_NOT_MATCH);
            }
        } catch (Exception e) {
            log.error("Lỗi khi thay đổi mật khẩu", e);
            throw e;
        }
    }

}
