package skyxplore.auth;

import com.github.saphyra.authservice.PropertySource;
import org.springframework.stereotype.Component;
import skyxplore.filter.CustomFilterHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static skyxplore.controller.PageController.INDEX_MAPPING;
import static skyxplore.filter.CustomFilterHelper.COOKIE_ACCESS_TOKEN;
import static skyxplore.filter.CustomFilterHelper.COOKIE_USER_ID;
import static skyxplore.filter.CustomFilterHelper.REST_TYPE_REQUEST;

@Component
public class PropertySourceImpl implements PropertySource {
    @Override
    public String getRequestTypeHeader() {
        return CustomFilterHelper.REQUEST_TYPE_HEADER;
    }

    @Override
    public String getRestTypeValue() {
        return REST_TYPE_REQUEST;
    }

    @Override
    public String getUnauthorizedRedirection() {
        return INDEX_MAPPING;
    }

    @Override
    public String getForbiddenRedirection() {
        return INDEX_MAPPING;
    }

    @Override
    public String getAccessTokenIdCookie() {
        return COOKIE_ACCESS_TOKEN;
    }

    @Override
    public String getUserIdCookie() {
        return COOKIE_USER_ID;
    }

    @Override
    public List<String> getAllowedUris() {
        return Arrays.asList(
            "/",
            "/**/favicon.ico",
            "/login",
            "/user/register",
            "/user/name/exist",
            "/user/email/exist",
            "/css/**",
            "/images/**",
            "/js/**",
            "/i18n/**"
        );
    }

    @Override
    public Map<String, Set<String>> getRoleSettings() {
        return new HashMap<>();
    }

    @Override
    public boolean isMultipleLoginAllowed() {
        return false;
    }

    @Override
    public long getTokenExpirationMinutes() {
        return 15;
    }

    @Override
    public int getFilterOrder() {
        return 1;
    }

    @Override
    public String getSuccessfulLoginRedirection() {
        return "characterselect";
    }

    @Override
    public Optional<String> getLogoutRedirection() {
        return Optional.empty();
    }
}
