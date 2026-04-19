package com.example.menunfl.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    @Autowired
    SecurityFilter secutiryFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html",
                                "/swagger-resources/**", "/webjars/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/product/add-product").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/product/list-all-products").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/product/update-product/{id}").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/product/delete-product/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/login-customer").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/register-customer").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/customer/get-all-customers").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/customer/get-customer-by-id/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/customer/update-customer/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/address/add-address").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/customer/customers/{customerId}/addresses").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/order/place-order").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(secutiryFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
