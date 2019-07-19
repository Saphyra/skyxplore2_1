package com.github.saphyra.skyxplore.filter;

import static com.github.saphyra.skyxplore.character.CharacterController.RENAME_CHARACTER_MAPPING;
import static com.github.saphyra.skyxplore.filter.CustomFilterHelper.COOKIE_CHARACTER_ID;
import static com.github.saphyra.skyxplore.filter.CustomFilterHelper.COOKIE_USER_ID;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.github.saphyra.skyxplore.character.CharacterQueryService;
import com.github.saphyra.skyxplore.common.PageController;
import com.github.saphyra.skyxplore.common.exception.CharacterNotFoundException;
import com.github.saphyra.skyxplore.common.exception.InvalidAccessException;
import com.github.saphyra.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Component
class CharacterAuthFilter extends OncePerRequestFilter {
    private static final List<String> allowedUris = Arrays.asList(
        "/",
        "/**/favicon.ico",
        "/login",
        "/logout",
        "/css/**",
        "/images/**",
        "/js/**",
        "/characterselect",
        "/character/characters",
        "/account",
        "/user/**",
        "/character",
        "/character/*",
        "/character/name",
        "/" + RENAME_CHARACTER_MAPPING,
        "/character/delete/*",
        "/i18n/**"
    );

    private final AntPathMatcher pathMatcher;
    private final CharacterQueryService characterQueryService;
    private final CookieUtil cookieUtil;
    private final CustomFilterHelper customFilterHelper;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        log.debug("CharacterAuthFilter");
        String path = request.getRequestURI();
        log.debug("processing path {}", path);
        if (isAllowedPath(path)) {
            log.debug("Path is allowed.");
            filterChain.doFilter(request, response);
        } else if (isAuthenticated(request)) {
            log.debug("Authentication successful");
            filterChain.doFilter(request, response);
        } else {
            customFilterHelper.handleUnauthorized(request, response, PageController.CHARACTER_SELECT_MAPPING);
        }
    }

    private boolean isAllowedPath(String path) {
        return allowedUris.stream().anyMatch(allowedPath -> pathMatcher.match(allowedPath, path));
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        Optional<String> userId = cookieUtil.getCookie(request, COOKIE_USER_ID);
        Optional<String> characterId = cookieUtil.getCookie(request, COOKIE_CHARACTER_ID);

        if (!userId.isPresent() || !characterId.isPresent()) {
            log.warn("Cookies not found.");
            return false;
        }

        try {
            characterQueryService.findCharacterByIdAuthorized(characterId.get(), userId.get());
        } catch (CharacterNotFoundException | InvalidAccessException e) {
            log.warn("Character authentication failed.", e);
            return false;
        }

        return true;
    }
}
