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
//TODO unit test
public class AlreadyInLobbyFilter extends OncePerRequestFilter {
    private static final List<String> ALLOWED_URIS = Arrays.asList(
        PageController.LOBBY_MAPPING,
        "/**/favicon.ico",
        "/css/**",
        "/images/**",
        "/js/**",
        "/i18n/**"
    );

    private final AntPathMatcher antPathMatcher;
    private final CookieUtil cookieUtil;
    private final CustomFilterHelper customFilterHelper;
    private final LobbyQueryService lobbyQueryService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("AlreadyInLobbyFilter");
        Optional<String> characterId = cookieUtil.getCookie(request, COOKIE_CHARACTER_ID);
        if (!customFilterHelper.isRestCall(request) && !isAllowedPath(request.getRequestURI()) && characterId.isPresent() && lobbyQueryService.findByCharacterId(characterId.get()).isPresent()) {
            log.info("{} is already in lobby. Redirecting...", characterId);
            response.sendRedirect(PageController.LOBBY_MAPPING);
        }

        filterChain.doFilter(request, response);
    }

    private boolean isAllowedPath(String requestURI) {
        return ALLOWED_URIS.stream()
            .anyMatch(uri -> antPathMatcher.match(uri, requestURI));
    }
}
