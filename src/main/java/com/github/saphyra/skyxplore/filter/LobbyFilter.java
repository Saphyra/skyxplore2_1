package com.github.saphyra.skyxplore.filter;

import com.github.saphyra.skyxplore.common.PageController;
import com.github.saphyra.skyxplore.lobby.lobby.LobbyQueryService;
import com.github.saphyra.skyxplore.lobby.lobby.domain.Lobby;
import com.github.saphyra.util.CookieUtil;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
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

@Component
@RequiredArgsConstructor
@Slf4j
@Builder
class LobbyFilter extends OncePerRequestFilter {
    private static final List<String> ALLOWED_URIS = Arrays.asList(
        "/**/favicon.ico",
        "/css/**",
        "/images/**",
        "/js/**",
        "/i18n/**",
        "/"
    );

    private final AntPathMatcher antPathMatcher;
    private final CookieUtil cookieUtil;
    private final CustomFilterHelper customFilterHelper;
    private final LobbyQueryService lobbyQueryService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("lobbyFilter");
        String requestURI = request.getRequestURI();
        if (customFilterHelper.isRestCall(request)
            || isAllowedPath(requestURI)
            || !request.getMethod().equalsIgnoreCase(HttpMethod.GET.name())
        ) {
            log.debug("{} is allowed.", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        Optional<Lobby> lobbyOptional = cookieUtil.getCookie(request, COOKIE_CHARACTER_ID)
            .flatMap(lobbyQueryService::findByCharacterId);
        boolean isLobbyPageRequest = antPathMatcher.match(PageController.LOBBY_PAGE_MAPPING, requestURI);
        boolean isQueuePageRequest = antPathMatcher.match(PageController.LOBBY_QUEUE_MAPPING, requestURI);
        boolean isInQueue = lobbyOptional.map(Lobby::isInQueue).orElse(false);

        if (isInQueue && !isQueuePageRequest) {
            log.debug("Character is in queueLobby, and request is not for queuePage. Redirecting...");
            response.sendRedirect(PageController.LOBBY_QUEUE_MAPPING);
            return;
        }

        if (lobbyOptional.isPresent() && !isLobbyPageRequest && !isInQueue) {
            log.debug("Character is in lobby, and request is not for lobbyPage. Redirecting...");
            response.sendRedirect(PageController.LOBBY_PAGE_MAPPING);
            return;
        }

        if (!lobbyOptional.isPresent() && (isLobbyPageRequest || isQueuePageRequest)) {
            log.debug("Character is not in lobby, and request target is for lobby-related page. Redirrecting...");
            response.sendRedirect(PageController.OVERVIEW_MAPPING);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isAllowedPath(String requestURI) {
        return ALLOWED_URIS.stream()
            .anyMatch(uri -> antPathMatcher.match(uri, requestURI));
    }
}
