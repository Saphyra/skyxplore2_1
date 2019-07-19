package com.github.saphyra.skyxplore.auth;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.github.saphyra.authservice.auth.UriConfiguration;
import com.github.saphyra.authservice.auth.domain.AllowedUri;
import com.github.saphyra.authservice.auth.domain.RoleSetting;

@Component
class PropertySourceImpl implements UriConfiguration {
    @Override
    public List<AllowedUri> getAllowedUris() {
        return Arrays.asList(
            new AllowedUri("/", HttpMethod.GET),
            new AllowedUri("/**/favicon.ico", HttpMethod.GET),
            new AllowedUri("/user", HttpMethod.POST),
            new AllowedUri("/user/name", HttpMethod.POST),
            new AllowedUri("/user/email", HttpMethod.POST),
            new AllowedUri("/css/**", HttpMethod.GET),
            new AllowedUri("/images/**", HttpMethod.GET),
            new AllowedUri("/js/**", HttpMethod.GET),
            new AllowedUri("/i18n/**", HttpMethod.GET)
        );
    }

    @Override
    public List<AllowedUri> getNonSessionExtendingUris() {
        return Arrays.asList(
            new AllowedUri("/notification", HttpMethod.GET),
            new AllowedUri("/notification/*", HttpMethod.GET),
            new AllowedUri("/lobby/invitation", HttpMethod.GET),
            new AllowedUri("/lobby/message", HttpMethod.GET)
        );
    }

    @Override
    public Set<RoleSetting> getRoleSettings() {
        return new HashSet<>();
    }
}
