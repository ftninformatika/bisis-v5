package com.ftninformatika.bisis.auth.config;

import com.ftninformatika.bisis.auth.model.Authority;
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

//TODO: update security config before deploy!
        http.authorizeRequests()
                .antMatchers(
                        "/book",
                        "/book/multiple",
                        "/book/collection",
                        "/hello",
                        "/opac/**",
                        "/external_hit/**",
                        "/auth",
                        "/book_cover/**",
                        "/book_common/**",
                        "/memauth",
                        "/members/lending_history/**",
                        "/coders/language",
                        "/coders/lib_configurations",
                        "/records/wrapperrec/**",
                        "/records/wrapperrec/universal",
                        "/records/unimarc",
                        "/records/query/**",
                        "/book_cover/**",
                        "/v2/api-docs",
                        "/configuration/**",
                        "/swagger*/**",
                        "/webjars/**",
                        "/records/opac_wrapperrec/**",
                        "/library_members/**",
                        "/coders/location**",
                        "/records/rate_record/**",
                        "/coders/item_status**",
                        "/coders/sublocation/get_by_location**").permitAll()
                .antMatchers(
                        "/members_repository/**",
                        "/circ_report/**")
                .hasAnyRole("USER","ADMIN")
                .antMatchers("/**").hasAuthority("ROLE_ADMIN")
//                .anyRequest().authenticated()
                //.anyRequest().permitAll()
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
