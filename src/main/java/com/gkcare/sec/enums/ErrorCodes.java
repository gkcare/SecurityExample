package com.gkcare.sec.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCodes {

    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "error.token.expired"),
    TOKEN_INVALID(HttpStatus.BAD_REQUEST, "error.token.invalid"),
    TOKEN_SIGNATURE_INVALID(HttpStatus.UNAUTHORIZED, "error.token.signature_invalid"),
    TOKEN_BLACKLISTED(HttpStatus.UNAUTHORIZED, "error.token.blacklisted"),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "error.auth.invalid_credentials"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "error.server.internal"),
    USER_LOCKED(HttpStatus.LOCKED,"error.auth.user_locked"),
    ACCESS_DENIED(HttpStatus.UNAUTHORIZED,"error.auth.access_denied");

    private final HttpStatus httpStatus;
    private final String messageKey;

    ErrorCodes(HttpStatus httpStatus,String messageKey){
        this.httpStatus=httpStatus;
        this.messageKey=messageKey;
    }

}