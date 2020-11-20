package com.ftninformatika.bisis.config;

import com.ftninformatika.bisisauthentication.filters.AuthenticationTokenFilter;
import com.ftninformatika.bisisauthentication.security.BisisUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
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
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    BisisUserDetailsService userDetailsService;

    @Autowired
    AuthenticationTokenFilter authenticationTokenFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
               // .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(
                        "/book",
                        "/inventory/**",
                        "/inventory_unit/**",
                        "/book/multiple",
                        "/book/collection",
                        "/opac/**",
                        "/unikat/search",
                        "/unikat/search**",
                        "/external_hit/**",
                        "/auth",
                        "/authenticate",
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
                        "/coders/accession_register**",
                        "/coders/sublocation/**",
                        "/coders/item_status**",
                        "/coders/sublocation/get_by_location**",
                        "/librarians/**",
                        "/reservations/**").permitAll()
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
                .addFilterBefore(authenticationTokenFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
        //return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
