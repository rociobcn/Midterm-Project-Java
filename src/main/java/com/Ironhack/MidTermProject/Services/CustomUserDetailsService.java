package com.Ironhack.MidTermProject.Services;

import com.Ironhack.MidTermProject.Models.User.User;
import com.Ironhack.MidTermProject.Repositories.UserRepository;
import com.Ironhack.MidTermProject.Security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            System.out.println("User not present!");
            throw new UsernameNotFoundException("User does not exist");
        }
        CustomUserDetails customUserDetails = new CustomUserDetails(user.get());
        System.out.println("User found");
        System.out.println(customUserDetails.getAuthorities());
        System.out.println(customUserDetails.getUsername());


        return customUserDetails;
    }
}
