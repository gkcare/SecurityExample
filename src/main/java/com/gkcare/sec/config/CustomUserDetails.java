package com.gkcare.sec.config;

import com.gkcare.sec.entity.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    private Set<SimpleGrantedAuthority> authorities;

    public CustomUserDetails(UserInfo userInfo){
        this.username=userInfo.getUsername();
        this.password=userInfo.getPassword();
        authorities= userInfo.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role.getName().name().replace("ROLE_",""))).collect(Collectors.toSet());

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
