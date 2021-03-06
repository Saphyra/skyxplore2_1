package com.github.saphyra.skyxplore.platform.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.github.saphyra.skyxplore.platform.auth.repository.AccessTokenDao;
import com.github.saphyra.skyxplore.common.PageController;
import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.util.CookieUtil;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
@Builder
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
