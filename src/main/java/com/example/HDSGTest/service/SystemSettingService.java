package com.example.HDSGTest.service;

import com.example.HDSGTest.Exception.AppException;
import com.example.HDSGTest.Exception.ErrorCode;
import com.example.HDSGTest.IService.ISystemSettingService;
import com.example.HDSGTest.entity.SystemSetting;
import com.example.HDSGTest.repository.SystemSettingRepository;
import com.example.HDSGTest.repository.UserHistoryRepository;
import com.example.HDSGTest.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SystemSettingService implements ISystemSettingService {
    private SystemSettingRepository systemSettingRepository;

    public SystemSettingService(SystemSettingRepository systemSettingRepository) {
        this.systemSettingRepository = systemSettingRepository;
    }

    public void updateFaceMatchSetting(String newValue) {
        SystemSetting setting = systemSettingRepository.findByKey("face_match").orElseThrow(() -> new AppException(ErrorCode.SETTING_NOT_FOUND));
        setting.setValue(newValue);
        systemSettingRepository.save(setting);
    }
}
