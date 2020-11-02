package com.codergeezer.auth.enums;

import com.codergeezer.common.exception.BaseError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author haidv
 * @version 1.0
 */
@Getter
public enum ErrorNum implements BaseError {

    USER_LOCKED(4102, "user.locked", HttpStatus.UNAUTHORIZED),
    USER_NOT_ACCEPT(4102, "user.not.accept", HttpStatus.UNAUTHORIZED),
    USER_NOT_FOUND(4401, "user.not.found", HttpStatus.UNAUTHORIZED);

    private final int code;

    private final String messageCode;

    private final HttpStatus httpStatus;

    ErrorNum(int code, String messageCode, HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.messageCode = messageCode;
    }
}
