package com.gkcare.sec.config;

import com.gkcare.sec.entity.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    private List<GrantedAuthority> authorities;

    public CustomUserDetails(UserInfo userInfo){
        this.username=userInfo.getUsername();
        this.password=userInfo.getPassword();
       // authorities= Arrays.stream(userInfo.getRoles().split(",")).map(SimpleGrantedAuthority::new).toList();

       /* authorities= Arrays.stream(userInfo.getRoles().split(","))
                .map(Sim0pleGrantedAuthority::new)
                .map(authority -> (GrantedAuthority) authority)
                .toList();*/
        // belo line use cas method reference
        authorities= Arrays.stream(userInfo.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .map(GrantedAuthority.class::cast)
                .toList();
        /*authorities = Arrays.stream(userInfo.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());*//*authorities = Arrays.stream(userInfo.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());*/
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
