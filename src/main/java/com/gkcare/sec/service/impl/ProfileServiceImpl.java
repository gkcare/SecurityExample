package com.gkcare.sec.service.impl;

import com.gkcare.sec.dto.UserProfileDto;
import com.gkcare.sec.entity.UserInfo;
import com.gkcare.sec.repository.UserInfoRepository;
import com.gkcare.sec.service.JwtService;
import com.gkcare.sec.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserInfoRepository userInfoRepository;
    private final JwtService jwtService;

    @Override
    public UserProfileDto getLoggedInUserProfile(String token) {

        token = token.replace("Bearer ", "");
        String username= jwtService.extractUsername(token);
        UserInfo userinfo = userInfoRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return toDto(userinfo);
    }

    private UserProfileDto toDto(UserInfo userInfo) {
        Set<String> roles = userInfo.getRoles()
                .stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());

        Object profile = null;
        if (userInfo.getStudentProfile() != null)
            profile = userInfo.getStudentProfile();
        else if (userInfo.getTeacherProfile() != null)
            profile = userInfo.getTeacherProfile();
        else if (userInfo.getDirectorProfile() != null)
            profile = userInfo.getDirectorProfile();
        else if (userInfo.getLibrarianProfile() != null)
            profile = userInfo.getLibrarianProfile();

        return new UserProfileDto(
                userInfo.getUsername(),
                userInfo.getEmail(),
                roles,
                profile
        );
    }
}
