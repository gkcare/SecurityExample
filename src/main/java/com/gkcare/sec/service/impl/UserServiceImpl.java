package com.gkcare.sec.service.impl;

import com.gkcare.sec.dto.UserInfoDto;
import com.gkcare.sec.entity.UserInfo;
import com.gkcare.sec.repository.UserInfoRepository;
import com.gkcare.sec.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserInfoRepository userInfoRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserInfoRepository userInfoRepository,PasswordEncoder passwordEncoder){
        this.userInfoRepository=userInfoRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public String addUser(UserInfoDto userInfoDto) {
        userInfoRepository.save(new UserInfo(userInfoDto.getUsername(),passwordEncoder.encode(userInfoDto.getPassword()),userInfoDto.getEmail(),userInfoDto.getRoles()));
        return "Success";
    }
}
