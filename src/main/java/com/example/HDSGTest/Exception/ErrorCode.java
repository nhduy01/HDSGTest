package com.example.HDSGTest.Exception;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNAUTHORIZED(10001, "Bạn không có quyền truy cập chức năng này", HttpStatus.UNAUTHORIZED),
    ACCOUNT_NOT_FOUND(10002, "Không tìm thấy tài khoản", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(10003, "Chưa xác thực hoặc sai thông tin đăng nhập", HttpStatus.BAD_REQUEST),
    IMAGE_NOT_MATCH(10004, "Ảnh không đủ khớp", HttpStatus.BAD_REQUEST),
    SETTING_NOT_FOUND(10005,"Không tìm thấy thiết lập", HttpStatus.BAD_REQUEST),
    USERNAME_EXISTS(10006, "Tên đăng nhập đã tồn tại", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTS(10007, "Email đã tồn tại", HttpStatus.BAD_REQUEST)
    ;

    private int code;
    private String message;
    private HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
