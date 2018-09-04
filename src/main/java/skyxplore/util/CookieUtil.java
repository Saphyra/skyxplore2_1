package skyxplore.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
//TODO unit test
public class CookieUtil {
    public String getCookie(HttpServletRequest request, String name) {
        Optional<Cookie> cookie = Arrays.stream(request.getCookies())
            .filter(c -> c.getName().equals(name))
            .findAny();
        return cookie.map(Cookie::getValue).orElse(null);
    }

    public void setCookie(HttpServletResponse response, String name, String value){
        response.addCookie(createCookie(name, value));
    }

    private Cookie createCookie(String name, String value){
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(-1);
        return cookie;
    }
}
