package com.ftninformatika.bisisoauth.config;

import com.ftninformatika.bisisauthentication.security.BisisUserDetailsService;
import com.ftninformatika.bisisoauth.web.ClientIdFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.PriorityOrdered;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
public class DefaultSecurityConfig {

    @Autowired
    BisisUserDetailsService userDetailsService;

    @Bean
    public DaoAuthenticationProvider authProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        userDetailsService.setLibraryFilter("bgb");
        userDetailsService.setExtractUserData(true);
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authProvider;
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers(  "/css/**", "/oauth2/userinfo").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin().loginPage("/login").permitAll();
        return http.build();
    }

    @Bean
    UserDetailsService users() {
        return userDetailsService;
    }


    @Bean
    public FilterRegistrationBean<ClientIdFilter> clientIdFilterFilterRegistrationBean(){
        FilterRegistrationBean<ClientIdFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new ClientIdFilter());
        registrationBean.addUrlPatterns("/oauth2/authorize");
        registrationBean.setOrder(PriorityOrdered.HIGHEST_PRECEDENCE);
        return registrationBean;
    }


}
