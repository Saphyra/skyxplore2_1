package com.github.saphyra.skyxplore.filter;

import com.github.saphyra.skyxplore.common.PageController;
import com.github.saphyra.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
class CookieCleanupFilter extends OncePerRequestFilter {
    private static final AntPathMatcher pathMatcher = new AntPathMatcher();

    private final CookieUtil cookieUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("CookieCleanupFilter");
        String path = request.getRequestURI();

        if (isCleanupNeeded(path)) {
            log.info("Cleaning up characterCookie...");
            cookieUtil.setCookie(response, CustomFilterHelper.COOKIE_CHARACTER_ID, "");
        }

        filterChain.doFilter(request, response);
    }

    private boolean isCleanupNeeded(String path) {
        return pathMatcher.match(PageController.CHARACTER_SELECT_MAPPING, path) || pathMatcher.match(PageController.INDEX_MAPPING, path);
    }
}
