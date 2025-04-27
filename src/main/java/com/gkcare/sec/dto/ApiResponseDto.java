package com.gkcare.sec.dto;

import java.time.LocalDateTime;

public record ApiResponseDto(LocalDateTime timestamp,int Status,String message,Object data,String errorCode,String path){}

