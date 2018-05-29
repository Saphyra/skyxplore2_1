package skyxplore.global.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import skyxplore.auth.service.AccessTokenService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {
    public static final String COOKIE_USER_ID = "userid";
    public static final String COOKIE_ACCESS_TOKEN = "accesstoken";

    private static final AntPathMatcher pathMatcher = new AntPathMatcher();
    private static final List<String> allowedUris = Arrays.asList(
            "/",
            "/login",
            "/registration",
            "/isusernameexists",
            "/isemailexists",
            "/css/**",
            "/images/**",
            "/js/**"
    );

    private final AccessTokenService accessTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("AuthFilter");
        String path = request.getRequestURI();
        log.debug("Request arrived: {}", path);
        if (allowedUris.stream().anyMatch(allowedPath -> pathMatcher.match(allowedPath, path))) {
            log.debug("Path allowed.");
            filterChain.doFilter(request, response);
        } else if (isAuthenticated(request, response)) {
            filterChain.doFilter(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed.");
        }
    }

    private boolean isAuthenticated(HttpServletRequest request, HttpServletResponse response) {
        log.info("Authenticating...");
        String accessTokenId = getCookie(request, COOKIE_ACCESS_TOKEN);
        String userIdValue = getCookie(request, COOKIE_USER_ID);

        return accessTokenService.isAuthenticated(userIdValue, accessTokenId, response);
    }

    private String getCookie(HttpServletRequest request, String name) {
        Optional<Cookie> cookie = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(name))
                .findAny();
        if (cookie.isPresent()) {
            return cookie.get().getValue();
        } else {
            return null;
        }
    }
}
