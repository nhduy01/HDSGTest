package com.example.HDSGTest.controller;

import com.example.HDSGTest.IService.ISystemSettingService;
import com.example.HDSGTest.service.SystemSettingService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/system-setting")
public class SystemSettingController {
    private ISystemSettingService systemSettingService;


    @PutMapping("/face-match")
    public void updateFaceMatch(@RequestParam("value") String newValue) {
        systemSettingService.updateFaceMatchSetting(newValue);
    }
}
