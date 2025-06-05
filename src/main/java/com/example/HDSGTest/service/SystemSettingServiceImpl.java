package com.example.HDSGTest.service;

import com.example.HDSGTest.exception.AppException;
import com.example.HDSGTest.exception.ErrorCode;
import com.example.HDSGTest.entity.SystemSetting;
import com.example.HDSGTest.repository.SystemSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class SystemSettingServiceImpl implements SystemSettingService {
    private final SystemSettingRepository systemSettingRepository;

    public void updateFaceMatchSetting(String newValue) {
        try {
            SystemSetting setting = systemSettingRepository.findByKey("face_match").orElseThrow(() -> new AppException(ErrorCode.SETTING_NOT_FOUND));
            setting.setValue(newValue);
            systemSettingRepository.save(setting);
        } catch (Exception e) {
            log.error("Lỗi khi cập nhật cài đặt khớp khuôn mặt", e);
            throw e;
        }
    }
}
