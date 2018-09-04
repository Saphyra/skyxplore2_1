package skyxplore.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.PageController;
import skyxplore.exception.BadRequestAuthException;
import skyxplore.service.AccessTokenFacade;
import skyxplore.util.CookieUtil;

@SuppressWarnings("NullableProblems")
@Slf4j
@RequiredArgsConstructor
@Component
//TODO unit test
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

    private final AccessTokenFacade accessTokenFacade;
    private final CookieUtil cookieUtil;

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
            if ("rest".equals(request.getHeader("Request-Type"))) {
                log.info("Sending error. Cause: Unauthorized access.");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed.");
            } else {
                log.info("Redirect to login page. Cause: Unauthorized access.");
                response.sendRedirect(PageController.INDEX_MAPPING);
            }
        }
    }

    private boolean isAllowedPath(String path){
        return allowedUris.stream().anyMatch(allowedPath -> pathMatcher.match(allowedPath, path));
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Logging out...");
        String accessTokenId = cookieUtil.getCookie(request, COOKIE_ACCESS_TOKEN);
        String userIdValue = cookieUtil.getCookie(request, COOKIE_USER_ID);
        try {
            accessTokenFacade.logout(userIdValue, accessTokenId);
        } catch (BadRequestAuthException e) {
            log.info("Error during logging out: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            return;
        }
        log.info("Successfully logged out.");
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        log.debug("Authenticating...");
        String accessTokenId = cookieUtil.getCookie(request, COOKIE_ACCESS_TOKEN);
        String userIdValue = cookieUtil.getCookie(request, COOKIE_USER_ID);

        if(accessTokenId == null || userIdValue == null){
            log.warn("Cookies not found.");
            return false;
        }

        return accessTokenFacade.isAuthenticated(userIdValue, accessTokenId);
    }


}
