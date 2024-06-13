package com.smart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.smart.Dao.UserRepository;
import com.smart.entities.User;

@Component
public class UserDetailsServiceImple implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Could not found user...!");

        } else {

            return new CustomUserDetails(user);
        }

    }

    // @Override
    // public UserDetails loadUserByUsername(String username) throws
    // UsernameNotFoundException {
    // // fetiching user from database
    // User user = userRepository.getUserByUsername(username);
    // if (user == null) {
    // throw new UsernameNotFoundException("Could not found user...!");

    // }

    // CustomUserDetails customUserDetails = new CustomUserDetails(user);

    // return customUserDetails;
    // }

}
