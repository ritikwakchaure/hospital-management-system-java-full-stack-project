package com.hospital.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class AuthFilter extends HttpFilter implements Filter {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doFilter(HttpServletRequest request,
                            HttpServletResponse response,
                            FilterChain chain)
            throws IOException, ServletException {

        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = uri.substring(contextPath.length());

        boolean isLoginRequest = path.equals("/login");
        boolean isLogoutRequest = path.equals("/logout");
        boolean isRegisterRequest = path.equals("/register");
        boolean isStaticResource =
                path.startsWith("/css/") ||
                path.startsWith("/js/") ||
                path.startsWith("/images/") ||
                path.startsWith("/fonts/");

        if (isLoginRequest || isLogoutRequest || isRegisterRequest || isStaticResource) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession(false);
        Object loggedUser = (session != null) ? session.getAttribute("loggedUser") : null;

        if (loggedUser == null) {
            response.sendRedirect(contextPath + "/login");
            return;
        }

        chain.doFilter(request, response);
    }
}
