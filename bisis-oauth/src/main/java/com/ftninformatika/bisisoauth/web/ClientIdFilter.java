package com.ftninformatika.bisisoauth.web;

import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(PriorityOrdered.HIGHEST_PRECEDENCE)
public class ClientIdFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String client_id = request.getParameter("client_id");
        if (client_id != null) {
            (request).getSession().setAttribute("client_id", client_id);
        }
        filterChain.doFilter(request, response);
    }
}
