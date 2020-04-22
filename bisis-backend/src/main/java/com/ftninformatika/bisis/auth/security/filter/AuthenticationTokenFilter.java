package com.ftninformatika.bisis.auth.security.filter;

import com.ftninformatika.bisis.auth.security.service.TokenAuthenticationService;
import com.ftninformatika.bisis.rest_service.LibraryPrefixProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class AuthenticationTokenFilter extends GenericFilterBean {

    private final TokenAuthenticationService authenticationService;

    @Autowired LibraryPrefixProvider prefixProvider;

    public AuthenticationTokenFilter(final TokenAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
                         final FilterChain filterChain)
            throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final Authentication authentication = authenticationService.authenticate(httpRequest);


        String lib = ((HttpServletRequest) request).getHeader("Library");

        if (lib != null && !lib.equals("")) {
            prefixProvider.setPrefix(lib); //Usmeravanje na odredjenu kolekciju u zavisnosti od hedera (Library)
        }
        else {
            prefixProvider.setPrefix("exile");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
