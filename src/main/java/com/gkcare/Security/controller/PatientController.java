package com.gkcare.Security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @GetMapping("/all")
   @PreAuthorize("hasAuthority('ROLE_ADMIN')")
   // @PreAuthorize("hasRole('ADMIN')")
    public String getAllPatient(){
        return "getAllPatient details Fetch successfully";
    }

    @GetMapping("/get")
    @PreAuthorize("hasAuthority('ROLE_GUEST')")
  //  @PreAuthorize("hasRole('GUEST')")
    public String getPatientById(){
        return "get Patient By Id details Fetch successfully";
    }
}
