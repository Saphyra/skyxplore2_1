package com.github.saphyra.skyxplore.platform.filter;

import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class UserIdCleanupFilter extends OncePerRequestFilter {
    private final CookieUtil cookieUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.debug("Cleaning up userId cookie...");

        cookieUtil.setCookie(response, RequestConstants.COOKIE_USER_ID, "", 0);

        filterChain.doFilter(request, response);
    }
}
