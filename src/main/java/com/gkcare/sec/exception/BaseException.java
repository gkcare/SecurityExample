package com.gkcare.sec.exception;

import com.gkcare.sec.enums.ErrorCodes;
import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {

    private final ErrorCodes errorCode;

    public BaseException(ErrorCodes errorCode){
        super(errorCode.name());
        this.errorCode=errorCode;
    }

    public BaseException(ErrorCodes errorCode,String customMessage){
        super(customMessage);
        this.errorCode=errorCode;
    }
}
