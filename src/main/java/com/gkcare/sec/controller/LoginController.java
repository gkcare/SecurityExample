package com.gkcare.sec.controller;

import com.gkcare.sec.dto.ApiResponseDto;
import com.gkcare.sec.dto.LoginDto;
import com.gkcare.sec.exception.InvalidCredentialsException;
import com.gkcare.sec.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/login")
public class LoginController {


    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public LoginController(JwtService jwtService,AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager=authenticationManager;
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto> login(@RequestBody LoginDto loginDto, HttpServletRequest request){
     System.out.println("Step First");
        System.out.println(authenticationManager);
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.username(),loginDto.password()));

        if(authentication.isAuthenticated()){
            return new ResponseEntity<>(new ApiResponseDto(
                    LocalDateTime.now(),
                    HttpStatus.OK.value(),
                    "Token generated Successfully",
                    jwtService.generateToken(loginDto.username()),
                    null,
                    request.getRequestURI()
            ), HttpStatusCode.valueOf(200));
        }
        else{
            throw new InvalidCredentialsException();
        }


    }

}
