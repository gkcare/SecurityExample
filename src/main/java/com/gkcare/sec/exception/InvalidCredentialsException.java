package com.gkcare.sec.exception;

import com.gkcare.sec.enums.ErrorCodes;

public class InvalidCredentialsException extends BaseException{

    public InvalidCredentialsException(){
        super(ErrorCodes.INVALID_CREDENTIALS);
    }

}
