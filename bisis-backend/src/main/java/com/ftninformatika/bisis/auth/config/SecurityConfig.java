package com.ftninformatika.bisis.auth.config;

import com.ftninformatika.bisis.auth.security.filter.AuthenticationTokenFilter;
import com.ftninformatika.bisis.auth.security.service.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    protected SecurityConfig(final TokenAuthenticationService tokenAuthenticationService) {
        super();
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {


        http.authorizeRequests()
                .antMatchers("/auth").permitAll()
                .antMatchers("/memauth").permitAll()
                .antMatchers("/coders/language").permitAll()
                .antMatchers("/coders/lib_configurations").permitAll()
                .antMatchers("/records/wrapperrec/**").permitAll()
                .antMatchers("/records/wrapperrec/universal").permitAll()
                .antMatchers("/records/unimarc").permitAll()
                .antMatchers("/records/query/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new AuthenticationTokenFilter(tokenAuthenticationService),
                        UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }
}
