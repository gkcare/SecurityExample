package com.gkcare.sec.controller;

import com.gkcare.sec.dto.ApiResponseDto;
import com.gkcare.sec.dto.UserInfoDto;
import com.gkcare.sec.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponseDto> addUserInfo(@RequestBody UserInfoDto userInfoDto, HttpServletRequest request){
        return new ResponseEntity<>(new ApiResponseDto(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "User has been added successfully",
                userService.addUser(userInfoDto),
                null,
                request.getRequestURI()
        ), HttpStatusCode.valueOf(200));
    }
}
