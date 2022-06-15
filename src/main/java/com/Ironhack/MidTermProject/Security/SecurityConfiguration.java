package com.Ironhack.MidTermProject.Security;

import com.Ironhack.MidTermProject.Services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic();

        http.authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/account_holder/**").hasRole("ACCOUNTHOLDER")
                .mvcMatchers(HttpMethod.POST, "/account_holder/**").hasRole("ACCOUNTHOLDER")
                .mvcMatchers(HttpMethod.GET, "/third_party/**").hasAnyRole("THIRDPARTY")
                .mvcMatchers(HttpMethod.POST, "/third_party/**").hasRole("THIRDPARTY")
     //           .mvcMatchers(HttpMethod.GET, "/admin/**").hasRole("ADMIN")
       //         .mvcMatchers(HttpMethod.POST, "/admin/**").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.GET, "/admin/add_account_holder/").permitAll()
                .mvcMatchers(HttpMethod.PATCH, "/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll();

        http.csrf().disable();
        return http.build();


    }
}
