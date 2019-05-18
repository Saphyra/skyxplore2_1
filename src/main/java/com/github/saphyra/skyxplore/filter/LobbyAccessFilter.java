package com.github.saphyra.skyxplore.filter;

import com.github.saphyra.skyxplore.common.PageController;
import com.github.saphyra.skyxplore.lobby.lobby.LobbyQueryService;
import com.github.saphyra.util.CookieUtil;
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
import java.util.Optional;

import static com.github.saphyra.skyxplore.filter.CustomFilterHelper.COOKIE_CHARACTER_ID;

@Component
@RequiredArgsConstructor
@Slf4j
public class LobbyAccessFilter extends OncePerRequestFilter {
    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private final CookieUtil cookieUtil;
    private final LobbyQueryService lobbyQueryService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("LobbyAccessFilter");
        if (isLobbyPageRequest(request)) {
            log.debug("User wants to access lobby page");
            if (!isInLobby(request)) {
                log.info("Character is not in lobby. Redirecting...");
                response.sendRedirect(PageController.OVERVIEW_MAPPING);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isInLobby(HttpServletRequest request) {
        Optional<String> characterId = cookieUtil.getCookie(request, COOKIE_CHARACTER_ID);
        return characterId.isPresent() && lobbyQueryService.findByCharacterId(characterId.get()).isPresent();
    }

    private boolean isLobbyPageRequest(HttpServletRequest request) {
        return antPathMatcher.match(PageController.LOBBY_MAPPING, request.getRequestURI())
            && request.getMethod().equalsIgnoreCase(HttpMethod.GET.name());
    }
}
