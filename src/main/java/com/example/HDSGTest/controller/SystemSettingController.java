package com.example.HDSGTest.controller;

import com.example.HDSGTest.IService.ISystemSettingService;
import com.example.HDSGTest.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/system-setting")
@RequiredArgsConstructor
public class SystemSettingController {
    private final ISystemSettingService systemSettingService;

    @Operation(summary = "Cập nhật ngưỡng so khớp khuôn mặt")
    @PutMapping("/face-match")
    public ResponseEntity<ApiResponse<Void>> updateFaceMatch(@RequestParam("value") String newValue) {
        systemSettingService.updateFaceMatchSetting(newValue);
        return ResponseEntity.ok(ApiResponse.<Void>builder().code(0).message("Cập nhật ngưỡng so khớp khuôn mặt thành công").build());
    }
}
