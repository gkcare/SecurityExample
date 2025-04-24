package com.gkcare.sec.controller;

import com.gkcare.sec.dto.ApiResponseDto;
import com.gkcare.sec.dto.UserInfoDto;
import com.gkcare.sec.service.UserService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponseDto> addUserInfo(@RequestBody UserInfoDto userInfoDto){
        return new ResponseEntity<>(new ApiResponseDto(200,"User has been created successfully",userService.addUser(userInfoDto)), HttpStatusCode.valueOf(200));
    }
}
