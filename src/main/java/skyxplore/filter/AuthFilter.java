package skyxplore.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import skyxplore.exception.BadRequestAuthException;
import skyxplore.service.AccessTokenService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("NullableProblems")
@Slf4j
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {
    public static final String COOKIE_USER_ID = "userid";
    public static final String COOKIE_ACCESS_TOKEN = "accesstoken";

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
            "/js/**"
    );

    private final AccessTokenService accessTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("AuthFilter");
        String path = request.getRequestURI();
        log.debug("Request arrived: {}", path);
        if (pathMatcher.match("/logout", path)) {
            logout(request, response);
        } else if (isAllowedPath(path)) {
            log.debug("Allowed path: {}", path);
            filterChain.doFilter(request, response);
        } else if (isAuthenticated(request)) {
            log.debug("Needs authentication: {}", path);
            filterChain.doFilter(request, response);
        } else {
            if ("restcontroller".equals(request.getHeader("Request-Type"))) {
                log.info("Sending error. Cause: Unauthorized access.");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed.");
            } else {
                log.info("Redirect to login page. Cause: Unauthorized access.");
                response.sendRedirect("/");
            }
        }
    }

    private boolean isAllowedPath(String path){
        return allowedUris.stream().anyMatch(allowedPath -> pathMatcher.match(allowedPath, path));
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Logging out...");
        String accessTokenId = getCookie(request, COOKIE_ACCESS_TOKEN);
        String userIdValue = getCookie(request, COOKIE_USER_ID);
        try {
            accessTokenService.logout(userIdValue, accessTokenId);
        } catch (BadRequestAuthException e) {
            log.info("Error during logging out: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            return;
        }
        log.info("Successfully logged out.");
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        log.debug("Authenticating...");
        String accessTokenId = getCookie(request, COOKIE_ACCESS_TOKEN);
        String userIdValue = getCookie(request, COOKIE_USER_ID);

        return accessTokenService.isAuthenticated(userIdValue, accessTokenId);
    }

    private String getCookie(HttpServletRequest request, String name) {
        Optional<Cookie> cookie = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(name))
                .findAny();
        return cookie.map(Cookie::getValue).orElse(null);
    }
}
