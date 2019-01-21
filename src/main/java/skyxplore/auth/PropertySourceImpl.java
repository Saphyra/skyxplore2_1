package skyxplore.auth;

import static skyxplore.controller.PageController.INDEX_MAPPING;
import static skyxplore.filter.CustomFilterHelper.COOKIE_ACCESS_TOKEN;
import static skyxplore.filter.CustomFilterHelper.COOKIE_USER_ID;
import static skyxplore.filter.CustomFilterHelper.REST_TYPE_REQUEST;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.github.saphyra.authservice.PropertySource;
import com.github.saphyra.authservice.domain.AllowedUri;
import com.github.saphyra.authservice.domain.RoleSetting;
import skyxplore.filter.CustomFilterHelper;

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
    public List<AllowedUri> getAllowedUris() {
        return Arrays.asList(
            new AllowedUri("/", HttpMethod.GET),
            new AllowedUri("/**/favicon.ico", HttpMethod.GET),
            new AllowedUri("/user", HttpMethod.POST),
            new AllowedUri("/user/name/exist", HttpMethod.POST),
            new AllowedUri("/user/email/exist", HttpMethod.POST),
            new AllowedUri("/css/**", HttpMethod.GET),
            new AllowedUri("/images/**", HttpMethod.GET),
            new AllowedUri("/js/**", HttpMethod.GET),
            new AllowedUri("/i18n/**", HttpMethod.GET)
        );
    }

    @Override
    public Set<RoleSetting> getRoleSettings() {
        return new HashSet<>();
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
