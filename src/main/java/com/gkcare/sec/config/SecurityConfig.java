package com.gkcare.sec.config;

import com.gkcare.sec.repository.UserInfoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class SecurityConfig{

    private final UserInfoRepository userInfoRepository;

    public SecurityConfig(UserInfoRepository userInfoRepository){
        this.userInfoRepository=userInfoRepository;
    }

    @Bean
    public UserDetailsService userDetailsService(){
        /*UserDetails admin= User.withUsername("Gaurav")
                .password(encoder.encode("pwd")).roles("ADMIN")
                .build();
        UserDetails user=User.withUsername("Kumar")
                .password(encoder.encode("pwd2")).roles("GUEST")
                .build();
        return new InMemoryUserDetailsManager(admin,user);*/

        return new UserInfoUserDetailsService(userInfoRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/","/home","/user/add").permitAll()
                       // .requestMatchers("/patient/all**").hasRole("ADMIN")
                     //  .requestMatchers("/patient/get**").hasRole("GUEST")
                        .anyRequest().authenticated()
                ).formLogin(AbstractAuthenticationFilterConfigurer::permitAll);

        return http.build();

    }

  /*  @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }*/

}
