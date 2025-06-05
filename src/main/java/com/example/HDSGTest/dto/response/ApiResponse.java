package com.example.HDSGTest.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
public class ApiResponse<T> {
    int code = 0;
    String message = "Lỗi không xác định";
    T entity;
}
