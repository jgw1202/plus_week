package com.example.demo.interceptor;

import com.example.demo.constants.GlobalConstants;
import com.example.demo.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.example.demo.dto.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.example.demo.entity.Role;

@Component
public class AdminRoleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws UnauthorizedException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED, "세션이 끊어졌습니다.");
        }

        Authentication authentication = (Authentication) session.getAttribute(GlobalConstants.USER_AUTH);
        Role role = authentication.getRole();

        if (role != Role.ADMIN) {
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED, "ADMIN 권한이 필요합니다.");
        }

        return true;
    }
}
