package com.gkcare.sec.dto;

import com.gkcare.sec.enums.RoleType;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoDto {

    private int id;
    private String username;
    private String email;
    private String password;
    //private String roles;

    private Set<RoleType> roles;

    // student fields
    private String registrationNumber;
    private String course;
    private int semester;
    private String section;
    private LocalDate dateOfAdmission;

    // Teacher fields
    private String employeeId;
    private String department;
    private String designation;
    private Integer experienceYears;
    private String qualification;

    // Director fields
    private String officeNumber;
    private String authorityLevel;
    private String messageToCampus;

    // Librarian fields
    private String employeeCode;
    private String shift;
    private Boolean weekendAvailability;

}
