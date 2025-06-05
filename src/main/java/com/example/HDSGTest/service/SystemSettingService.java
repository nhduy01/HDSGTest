package com.example.HDSGTest.service;

import com.example.HDSGTest.Exception.AppException;
import com.example.HDSGTest.Exception.ErrorCode;
import com.example.HDSGTest.IService.ISystemSettingService;
import com.example.HDSGTest.entity.SystemSetting;
import com.example.HDSGTest.repository.SystemSettingRepository;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;

@Slf4j
@Service
public class SystemSettingService implements ISystemSettingService {
    private SystemSettingRepository systemSettingRepository;

    public SystemSettingService(SystemSettingRepository systemSettingRepository) {
        this.systemSettingRepository = systemSettingRepository;
    }

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
