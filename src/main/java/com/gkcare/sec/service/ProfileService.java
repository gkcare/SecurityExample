package com.gkcare.sec.service;

import com.gkcare.sec.dto.UserProfileDto;

public interface ProfileService {

    public UserProfileDto getLoggedInUserProfile(String token);

}
