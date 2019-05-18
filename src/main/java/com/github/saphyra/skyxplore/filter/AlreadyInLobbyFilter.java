package com.github.saphyra.skyxplore.filter;

import com.github.saphyra.skyxplore.common.PageController;
import com.github.saphyra.skyxplore.lobby.lobby.LobbyQueryService;
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
import java.util.Optional;

import static com.github.saphyra.skyxplore.filter.CustomFilterHelper.COOKIE_CHARACTER_ID;

@RequiredArgsConstructor
@Slf4j
@Component
public class AlreadyInLobbyFilter extends OncePerRequestFilter {
    private static final List<String> ALLOWED_URIS = Arrays.asList(
        PageController.LOBBY_MAPPING,
        "/**/favicon.ico",
        "/css/**",
        "/images/**",
        "/js/**",
        "/i18n/**",
        "/"
    );
    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private final CookieUtil cookieUtil;
    private final CustomFilterHelper customFilterHelper;
    private final LobbyQueryService lobbyQueryService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("AlreadyInLobbyFilter");

        if (isRedirectNeeded(request)) {
            log.info("Character is already in lobby. Redirecting...");
            response.sendRedirect(PageController.LOBBY_MAPPING);
            return;
        }

        filterChain.doFilter(request, response);
    }

    /**
     * @return true, if the request is a GET query for a page what is not allowed, and the character is already in lobby.
     */
    private boolean isRedirectNeeded(HttpServletRequest request) {
        Optional<String> characterId = cookieUtil.getCookie(request, COOKIE_CHARACTER_ID);
        return !customFilterHelper.isRestCall(request) && !isAllowedPath(request.getRequestURI()) && characterId.isPresent() && lobbyQueryService.findByCharacterId(characterId.get()).isPresent();
    }

    private boolean isAllowedPath(String requestURI) {
        return ALLOWED_URIS.stream()
            .anyMatch(uri -> antPathMatcher.match(uri, requestURI));
    }
}
