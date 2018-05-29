package skyxplore.global.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import skyxplore.auth.domain.AccessToken;
import skyxplore.auth.domain.exception.BadCredentialsException;
import skyxplore.auth.domain.exception.BadRequestAuthException;
import skyxplore.auth.service.AccessTokenService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
        } else {
            log.info("Authenticating...");
            authenticate(request);
            filterChain.doFilter(request, response);
        }
    }

    private void authenticate(HttpServletRequest request) {
        String accessTokenId = request.getHeader(COOKIE_ACCESS_TOKEN);
        String userIdValue = getCookie(request, COOKIE_USER_ID);
        if (userIdValue == null) {
            throw new BadRequestAuthException("Required cookies not found:" + COOKIE_USER_ID);
        }
        if(accessTokenId == null){
            throw new BadRequestAuthException("Required cookie not found:" + COOKIE_ACCESS_TOKEN);
        }

        Long userId;
        try{
            userId = Long.valueOf(userIdValue);
        }catch(NumberFormatException e){
            throw new BadRequestAuthException("Invalid userId type.");
        }


        AccessToken accessToken = accessTokenService.getAccessTokenByUserId(userId);
        if (accessToken == null) {
            throw new BadCredentialsException("No valid accessToken for user " + userId);
        } else if (!accessToken.getAccessTokenId().equals(accessTokenId)) {
            throw new BadCredentialsException("Invalid accessToken for user " + userId);
        }
    }

    private String getCookie(HttpServletRequest request, String name) {
        for(Cookie cookie : request.getCookies()){
            log.info(cookie.getName());
            if(cookie.getName().equals(name)){
                return cookie.getValue();
            }
        }
        return null;
        /*Optional<Cookie> cookie = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(name))
                .findAny();
        if (cookie.isPresent()) {
            return cookie.get().getValue();
        } else {
            return null;
        }*/
    }
}
