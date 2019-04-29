package org.github.saphyra.skyxplore.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.github.saphyra.skyxplore.common.CookieUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.github.saphyra.skyxplore.common.PageController.CHARACTER_SELECT_MAPPING;
import static org.github.saphyra.skyxplore.common.PageController.INDEX_MAPPING;
import static org.github.saphyra.skyxplore.filter.CustomFilterHelper.COOKIE_CHARACTER_ID;

@Component
@Slf4j
@RequiredArgsConstructor
public class CookieCleanupFilter extends OncePerRequestFilter {
    private static final AntPathMatcher pathMatcher = new AntPathMatcher();

    private final CookieUtil cookieUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("CookieCleanupFilter");
        String path = request.getRequestURI();

        if (isCleanupNeeded(path)) {
            log.info("Cleaning up characterCookie...");
            cookieUtil.setCookie(response, COOKIE_CHARACTER_ID, "");
        }

        filterChain.doFilter(request, response);
    }

    private boolean isCleanupNeeded(String path) {
        return pathMatcher.match(CHARACTER_SELECT_MAPPING, path) || pathMatcher.match(INDEX_MAPPING, path);
    }
}
