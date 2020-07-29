package com.ftninformatika.bisis.auth.config;

import com.ftninformatika.bisis.auth.security.filter.AuthenticationTokenFilter;
import com.ftninformatika.bisis.auth.security.service.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
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
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(
                        "/book",
                        "/book/multiple",
                        "/book/collection",
                        "/opac/**",
                        "/unikat/search",
                        "/unikat/search**",
                        "/external_hit/**",
                        "/auth",
                        "/book_cover/retrieve/**",
                        "/book_common/**",
                        "/memauth",
                        "/library_configuration/allConfigsBrief",
                        "/coders/language",
                        "/coders/lib_configurations",
                        "/records/wrapperrec/**",
                        "/records/wrapperrec/universal",
                        "/records/unimarc",
                        "/records/query/**",
                        "/v2/api-docs",
                        "/configuration/**",
                        "/swagger*/**",
                        "/webjars/**",
                        "/records/opac_wrapperrec/**",
                        "/library_members/get_member_by_activation_token/**",
                        "/library_members/activate_account/**",
                        "/library_members/forgot_password/**",
                        "/library_members/activate_account/**",
                        "/coders/location**",
                        "/coders/sublocation**",
                        "/coders/sublocation/**",
                        "/coders/item_status**",
                        "/coders/sublocation/get_by_location**").permitAll()
                .antMatchers(
                        "/members_repository/**",
                        "/circ_report/**",
                        "/library_members/**",
                        "/members/active_lendings/**",
                        "/members/lending_history/**",
                        "/records/rate_record/**"
                        )
                .hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
                .antMatchers(

                        "/book_cover/upload/**",
                        "/**").hasAuthority("ROLE_ADMIN")
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
