package com.gkcare.sec.exception;

import com.gkcare.sec.dto.ApiResponseDto;
import com.gkcare.sec.enums.ErrorCodes;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.WeakKeyException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;
import java.time.LocalDateTime;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponseDto> handleBaseException(BaseException ex, HttpServletRequest request) {
        ex.printStackTrace();
        return buildErrorResponse(ex.getErrorCode(), request.getRequestURI());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiResponseDto> handleExpiredJwtException(ExpiredJwtException ex, HttpServletRequest request) {
        return buildErrorResponse(ErrorCodes.TOKEN_EXPIRED, request.getRequestURI());
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ApiResponseDto> handleMalformedJwtException(MalformedJwtException ex, HttpServletRequest request) {
        return buildErrorResponse(ErrorCodes.TOKEN_INVALID, request.getRequestURI());
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ApiResponseDto> handleSignatureException(SignatureException ex, HttpServletRequest request) {
        return buildErrorResponse(ErrorCodes.TOKEN_SIGNATURE_INVALID, request.getRequestURI());
    }

    @ExceptionHandler(WeakKeyException.class)
    public ResponseEntity<ApiResponseDto> handleWeakKeyException(WeakKeyException ex, HttpServletRequest request) {
        return buildErrorResponse(ErrorCodes.TOKEN_SIGNATURE_INVALID, request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto> handleGeneralException(Exception ex, HttpServletRequest request) {

        return buildErrorResponse(ErrorCodes.INTERNAL_SERVER_ERROR, request.getRequestURI());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponseDto> handleAccessDeniedException(Exception ex, HttpServletRequest request) {

        return buildErrorResponse(ErrorCodes.ACCESS_DENIED, request.getRequestURI());
    }

    private ResponseEntity<ApiResponseDto> buildErrorResponse(ErrorCodes errorCode, String path) {
        String localizedMessage = messageSource.getMessage(errorCode.getMessageKey(), null, LocaleContextHolder.getLocale());
        ApiResponseDto response=new ApiResponseDto(
                LocalDateTime.now(),
                errorCode.getHttpStatus().value(),
                errorCode.getHttpStatus().getReasonPhrase(),
                null,
                localizedMessage,
                path
        );
        return new ResponseEntity<>(response, errorCode.getHttpStatus());
    }



}
