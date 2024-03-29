package com.ftninformatika.bisis.acquisition.config;

import com.ftninformatika.bisisauthentication.filters.AuthenticationTokenFilter;
import com.ftninformatika.bisisauthentication.security.BisisUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    BisisUserDetailsService bisisUserDetailService;

    @Autowired
    AuthenticationTokenFilter authFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(bisisUserDetailService);
    }
    @Override
    protected void configure(final HttpSecurity http) throws Exception {


        http.cors().and().authorizeRequests()
                .antMatchers(
                        "/authenticate").permitAll()
                .antMatchers("/distributors/get/**","/distributors/getOffersByPib/**", "/distributors/addOffer").permitAll()
                .antMatchers("/location/**","/reports/**","/distributors/**","/acquisition/**","/desideratum/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable();
    }
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

}
