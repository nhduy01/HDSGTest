package com.example.HDSGTest.Exception;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNAUTHORIZED(10001, "You Don't Have Permissions For This Function", HttpStatus.UNAUTHORIZED),
    ACCOUNT_NOT_FOUND(10002, "Account Not Found", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(10003, "Unauthenticated", HttpStatus.BAD_REQUEST),
    IMAGE_NOT_MATCH(10004, "Image not match up to 85%", HttpStatus.BAD_REQUEST),
    SETTING_NOT_FOUND(10005,"Setting not found", HttpStatus.BAD_REQUEST)
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
