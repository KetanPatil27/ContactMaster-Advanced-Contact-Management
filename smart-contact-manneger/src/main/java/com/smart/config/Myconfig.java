package com.smart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

// Depricated methode or old method new method of spring security use in Security Config class

// @Configuration
// @EnableWebSecurity
public class Myconfig {

  
    
    // @Bean
    // public UserDetailsService getUserDetailsService() {
    //     return new UserDetailsServiceImple();
    // }

    // @Bean
    // public BCryptPasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }

    // // @Bean
    // // public PasswordEncoder passwordEncoder()
    // // {
    // //     return new BCryptPasswordEncoder();
    // // }

    // @Bean
    // public DaoAuthenticationProvider authenticationProvider() {
    //     DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    //     daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService());
    //     daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    //     return daoAuthenticationProvider;
    // }

    // /// configuration method

    // // @Bean
    // // @Override
    // // protected void configure(AuthenticationManagerBuilder auth) throws Exception{
    // // auth.authenticationProvider(authenticationProvider());
    // // return super.authenticationManagerBean();
    // // }




    // @Bean
    // public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
    //     return configuration.getAuthenticationManager();

    // }

    // // }
    // // @Override
    // // protected void configure(HttpSecurity http) throws Exception{
    // // http.authorizeRequests().antMatchers("/admin/**").hasRole("Admin");
    // // .antMatchers("/user/**").hasRole("USER")
    // // .antMatchers("/**").permitAll().and().formLogin().and().csrf().disable()

    // // }

    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    //     httpSecurity.csrf().disable()
    //             .authorizeHttpRequests()
    //             .requestMatchers("/admin/**").hasRole("ADMIN")
    //             .requestMatchers("/user/**").hasRole("USER")
    //             .requestMatchers("/**").permitAll()
    //             .and().formLogin();

    //     return httpSecurity.build();
    // }

}
