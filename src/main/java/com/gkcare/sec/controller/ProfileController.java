package com.gkcare.sec.controller;

import com.gkcare.sec.dto.ApiResponseDto;
import com.gkcare.sec.dto.UserProfileDto;
import com.gkcare.sec.service.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'DIRECTOR', 'LIBRARIAN')")
    public ResponseEntity<ApiResponseDto> getProfile(@RequestHeader("Authorization") String authHeader, HttpServletRequest request){
        return new ResponseEntity<>(new ApiResponseDto(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Token generated Successfully",
                profileService.getLoggedInUserProfile(authHeader),
                null,
                request.getRequestURI()
        ), HttpStatusCode.valueOf(200));
    }

}
