package com.gkcare.sec.config;

import com.gkcare.sec.entity.UserInfo;
import com.gkcare.sec.repository.UserInfoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;


public class UserInfoUserDetailsService implements UserDetailsService {

    private final UserInfoRepository userInfoRepository;

    public UserInfoUserDetailsService(UserInfoRepository userInfoRepository){
        this.userInfoRepository=userInfoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo=userInfoRepository.findByUsername(username);
        return userInfo.map(CustomUserDetails::new).orElseThrow(
                () -> new UsernameNotFoundException("User Not found - "+username)
        );
    }
}
