package com.ecommerce.app.security;

import com.ecommerce.app.service.UserDetailsServiceImpl;
import com.ecommerce.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    private UserDetailsServiceImpl userDetailsService(){
        return new UserDetailsServiceImpl(userService);
    };

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());

        return daoAuthenticationProvider;

    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {

        security.authorizeHttpRequests(request ->
                        request
                                .requestMatchers(HttpMethod.POST, "/categories").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/products").permitAll()
                                .requestMatchers(HttpMethod.GET, "/categories").permitAll()
                                .requestMatchers(HttpMethod.GET,"/users").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST,"/users").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/shipment/**").hasAnyRole("ADMIN" , "SELLER")
                                .anyRequest().authenticated()


                );




        security.httpBasic(Customizer.withDefaults());
        security.csrf(AbstractHttpConfigurer::disable);

        return security.build();
    }




}
