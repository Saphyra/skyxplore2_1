package com.github.saphyra.skyxplore.filter;

import com.github.saphyra.skyxplore.common.PageController;
import com.github.saphyra.skyxplore.lobby.LobbyQueryService;
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
//TODO unit test
public class LobbyAccessFilter extends OncePerRequestFilter {
    private final AntPathMatcher antPathMatcher;
    private final CookieUtil cookieUtil;
    private final LobbyQueryService lobbyQueryService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("LobbyAccessFilter");
        if (antPathMatcher.match(PageController.LOBBY_MAPPING, request.getRequestURI()) && request.getMethod().equalsIgnoreCase(HttpMethod.GET.name())) {
            log.debug("User wants to access lobby page");
            Optional<String> characterId = cookieUtil.getCookie(request, COOKIE_CHARACTER_ID);
            if (!characterId.isPresent() || !lobbyQueryService.findByCharacterId(characterId.get()).isPresent()) {
                log.info("{} is not in lobby. Redirecting...", characterId);
                response.sendRedirect(PageController.OVERVIEW_MAPPING);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}