package com.smart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // this method is used for custom user...

    // @Bean
    // public UserDetailsService userDetailsService(){

    // UserDetails user
    // =User.withUsername("user").password(passwordEncoder().encode("1234")).roles("USER").build();
    // UserDetails admin
    // =User.withUsername("admin").password(passwordEncoder().encode("1234")).roles("ADMIN").build();

    // return new InMemoryUserDetailsManager(user,admin);

    // }

    @Bean // this bean created error cant invoke user repository
    public UserDetailsService getDetailsService() {
        return new UserDetailsServiceImple();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(getDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;

    }

    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/user/**").hasRole("USER")
                .requestMatchers("/**").permitAll()
                .anyRequest().authenticated().and()
                .formLogin()
                .loginPage("/signin")
                .loginProcessingUrl("/dologin")
                .defaultSuccessUrl("/user/index");

        return http.build();
    }

    // this method used for Custom security method already set username and password

    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    // {

    // http.csrf().disable()
    // .authorizeHttpRequests().anyRequest()
    // .authenticated().and().formLogin();

    // return http.build();
    // }
}
