package com.gkcare.sec.service;

import java.util.Date;

public interface JwtService {

    public String generateToken(String username);
    public String extractUsername(String token);
    public Date extractExpiraion(String token);
    public boolean isTokenValid(String token,String username);

}
