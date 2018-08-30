package skyxplore.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import skyxplore.controller.PageController;
import skyxplore.exception.CharacterNotFoundException;
import skyxplore.exception.InvalidAccessException;
import skyxplore.service.character.CharacterQueryService;
import skyxplore.util.CookieUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
//TODO unit test
public class CharacterAuthFilter extends OncePerRequestFilter {
    public static final String COOKIE_CHARACTER_ID = "characterid";

    private static final AntPathMatcher pathMatcher = new AntPathMatcher();
    private static final List<String> allowedUris = Arrays.asList(
        "/",
        "/**/favicon.ico",
        "/login",
        "/registration",
        "/isusernameexists",
        "/isemailexists",
        "/css/**",
        "/images/**",
        "/js/**",
        "/characterselect",
        "/character/characters",
        "/account",
        "/user/*",
        "/character/select/*",
        "/character/ischarnameexists/*"
    );

    private final CharacterQueryService characterQueryService;
    private final CookieUtil cookieUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
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
            if ("rest".equals(request.getHeader("Request-Type"))) {
                log.info("Sending error. Cause: Unauthorized character access.");
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Authentication failed.");
            } else {
                log.info("Redirect to characterSelect page. Cause: Unauthorized character access.");
                response.sendRedirect(PageController.CHARACTER_SELECT_MAPPING);
            }
        }
    }

    private boolean isAllowedPath(String path) {
        return allowedUris.stream().anyMatch(allowedPath -> pathMatcher.match(allowedPath, path));
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        String userId = cookieUtil.getCookie(request, AuthFilter.COOKIE_USER_ID);
        String characterId = cookieUtil.getCookie(request, COOKIE_CHARACTER_ID);

        if (userId == null || characterId == null) {
            log.warn("Cookies not found.");
            return false;
        }

        try{
            characterQueryService.findCharacterByIdAuthorized(characterId, userId);
        }catch (CharacterNotFoundException | InvalidAccessException e){
            log.warn("Character authentication failed.", e);
            return false;
        }

        return true;
    }
}
