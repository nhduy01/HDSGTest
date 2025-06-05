package com.example.HDSGTest.config;

import com.example.HDSGTest.entity.User;
import com.example.HDSGTest.entity.SystemSetting;
import com.example.HDSGTest.entity.UserHistory;
import com.example.HDSGTest.repository.UserRepository;
import com.example.HDSGTest.repository.SystemSettingRepository;
import com.example.HDSGTest.repository.UserHistoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Arrays;

import java.time.LocalDateTime;
import java.util.UUID;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(UserRepository userRepository, 
                               PasswordEncoder passwordEncoder,
                               SystemSettingRepository systemSettingRepository,
                               UserHistoryRepository userHistoryRepository) {
        return args -> {
            // Tạo data
            User user = null;
            if (!userRepository.existsByUsername("admin")) {
                user = new User();
                user.setUsername("admin");
                user.setPassword(passwordEncoder.encode("admin123"));
                user.setEmail("admin@example.com");
                user.setFullName("Administrator");
                user.setRole("ROLE_ADMIN");
                user = userRepository.save(user);
                
            } else {
                user = userRepository.findByUsername("admin").orElse(null);
            }
            User user1 = null;
            if (!userRepository.existsByUsername("user")) {
                user1 = new User();
                user1.setUsername("user");
                user1.setPassword(passwordEncoder.encode("user123"));
                user1.setEmail("user@example.com");
                user1.setFullName("User");
                user1.setRole("ROLE_USER");
                user1 = userRepository.save(user1);
            } else {
                user1 = userRepository.findByUsername("user").orElse(null);
            }

            if (userHistoryRepository.count() == 0) {
                UserHistory history = new UserHistory();
                history.setId(UUID.randomUUID());
                history.setUserId(user.getId());
                history.setUsername(user.getUsername());
                history.setEmail(user.getEmail());
                history.setPassword(user.getPassword());
                history.setFullName(user.getFullName());
                history.setAvatar(user.getAvatar());
                history.setRole(user.getRole());
                history.setOriginalCreatedAt(LocalDateTime.now());
                history.setOriginalUpdatedAt(LocalDateTime.now());
                history.setActionType("INIT");
                history.setUpdatedBy("system");
                UserHistory history1 = new UserHistory();
                history1.setId(UUID.randomUUID());
                history1.setUserId(user1.getId());
                history1.setUsername(user1.getUsername());
                history1.setEmail(user1.getEmail());
                history1.setPassword(user1.getPassword());
                history1.setFullName(user1.getFullName());
                history1.setAvatar(user1.getAvatar());
                history1.setRole(user1.getRole());
                history1.setOriginalCreatedAt(LocalDateTime.now());
                history1.setOriginalUpdatedAt(LocalDateTime.now());
                history1.setActionType("INIT");
                history1.setUpdatedBy("system");
                userHistoryRepository.saveAll(Arrays.asList(history, history1));
            }
            

            if (userHistoryRepository.count() == 0) {
                
            }
            if (systemSettingRepository.findByKey("face_match").isEmpty()) {
                SystemSetting setting = new SystemSetting();
                setting.setKey("face_match");
                setting.setValue("85%");
                setting.setDescription("Ngưỡng nhận diện khuôn mặt");
                systemSettingRepository.save(setting);
            }
            
        };
    }
} 