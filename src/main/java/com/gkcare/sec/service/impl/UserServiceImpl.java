package com.gkcare.sec.service.impl;

import com.gkcare.sec.dto.UserInfoDto;
import com.gkcare.sec.entity.*;
import com.gkcare.sec.enums.RoleType;
import com.gkcare.sec.repository.RoleRepository;
import com.gkcare.sec.repository.UserInfoRepository;
import com.gkcare.sec.service.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserInfoRepository userInfoRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    public UserServiceImpl(UserInfoRepository userInfoRepository,PasswordEncoder passwordEncoder, RoleRepository roleRepository){
        this.userInfoRepository=userInfoRepository;
        this.passwordEncoder=passwordEncoder;
        this.roleRepository=roleRepository;
    }

    @Transactional
    @Override
    public String addUser(UserInfoDto userInfoDto) {
        //userInfoRepository.save(new UserInfo(userInfoDto.getUsername(),passwordEncoder.encode(userInfoDto.getPassword()),userInfoDto.getEmail(),userInfoDto.getRoles()));

        if(userInfoRepository.findByUsername(userInfoDto.getUsername()).isPresent()){
            return "username already exists";
        }

        Set<Role> roles=new HashSet<>();
        for(RoleType type: userInfoDto.getRoles()){
            Role role = roleRepository.findByName(type)
                    .orElseThrow(() -> new RuntimeException("Role not found - "+type));
            roles.add(role);
        }

        UserInfo user= UserInfo.builder()
                .username(userInfoDto.getUsername())
                .password(passwordEncoder.encode(userInfoDto.getPassword()))
                .email(userInfoDto.getEmail())
                .roles(roles)
                .build();

        // Add profile conditionally

        for(Role role : roles){
            switch (role.getName()){
                case ROLE_STUDENT -> {
                    StudentProfile sp = StudentProfile.builder()
                            .registrationNumber(userInfoDto.getRegistrationNumber())
                            .course(userInfoDto.getCourse())
                            .semester(userInfoDto.getSemester())
                            .section(userInfoDto.getSection())
                            .dateOfAdmission(userInfoDto.getDateOfAdmission())
                            .user(user)
                            .build();
                    user.setStudentProfile(sp);
                }
                case ROLE_DIRECTOR -> {
                    DirectorProfile dp = DirectorProfile.builder()
                            .officeNumber(userInfoDto.getOfficeNumber())
                            .authorityLevel(userInfoDto.getAuthorityLevel())
                            .messageToCampus(userInfoDto.getMessageToCampus())
                            .user(user)
                            .build();
                    user.setDirectorProfile(dp);
                }
                case ROLE_LIBRARIAN -> {
                    LibrarianProfile lp = LibrarianProfile.builder()
                            .employeeCode(userInfoDto.getEmployeeCode())
                            .shift(userInfoDto.getShift())
                            .weekendAvailability(userInfoDto.getWeekendAvailability())
                            .user(user)
                            .build();
                    user.setLibrarianProfile(lp);
                }
                case ROLE_TEACHER -> {
                    TeacherProfile tp =TeacherProfile.builder()
                            .employeeId(userInfoDto.getEmployeeId())
                            .department(userInfoDto.getDepartment())
                            .designation(userInfoDto.getDesignation())
                            .experienceYears(userInfoDto.getExperienceYears())
                            .qualification(userInfoDto.getQualification())
                            .user(user)
                            .build();
                    user.setTeacherProfile(tp);
                }
            }
        }

        userInfoRepository.save(user);



        return "Success";
    }
}
