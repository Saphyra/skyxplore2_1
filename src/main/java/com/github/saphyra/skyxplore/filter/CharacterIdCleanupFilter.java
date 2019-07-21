package com.github.saphyra.skyxplore.filter;

import com.github.saphyra.skyxplore.auth.repository.AccessTokenDao;
import com.github.saphyra.skyxplore.common.PageController;
import com.github.saphyra.skyxplore.common.RequestConstants;
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
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CharacterIdCleanupFilter extends OncePerRequestFilter {
    private static final List<String> PROTECTED_URIS = Arrays.asList(
        PageController.INDEX_MAPPING,
        PageController.CHARACTER_SELECT_MAPPING
    );

    private final AntPathMatcher antPathMatcher;
    private final AccessTokenDao accessTokenDao;
    private final CookieUtil cookieUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestUri = request.getRequestURI();
        log.debug("Filtering... requestUri: {}", requestUri);
        log.info("requestUri: {}", requestUri);
        if (isProtected(requestUri)) {
            cookieUtil.getCookie(request, RequestConstants.COOKIE_CHARACTER_ID).ifPresent(accessTokenDao::cleanupCharacterId);
        }

        filterChain.doFilter(request, response);
    }

    private boolean isProtected(String requestURI) {
        return PROTECTED_URIS.stream()
            .anyMatch(protectedUri -> antPathMatcher.match(protectedUri, requestURI));
    }
}
