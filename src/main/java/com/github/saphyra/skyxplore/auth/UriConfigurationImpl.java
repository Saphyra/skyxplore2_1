package com.github.saphyra.skyxplore.auth;

import com.github.saphyra.authservice.auth.UriConfiguration;
import com.github.saphyra.authservice.auth.domain.AllowedUri;
import com.github.saphyra.authservice.auth.domain.RoleSetting;
import com.github.saphyra.skyxplore.common.PageController;
import com.github.saphyra.skyxplore.common.RequestConstants;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.saphyra.skyxplore.community.NotificationController.GET_NUMBER_OF_FRIEND_REQUESTS_MAPPING;
import static com.github.saphyra.skyxplore.community.NotificationController.GET_NUMBER_OF_NOTIFICATIONS_MAPPING;
import static com.github.saphyra.skyxplore.community.NotificationController.GET_NUMBER_OF_UNREAD_MAILS_MAPPING;
import static com.github.saphyra.skyxplore.lobby.invitation.InvitationController.GET_INVITATIONS_MAPPING;
import static com.github.saphyra.skyxplore.lobby.message.MessageController.GET_MESSAGES_MAPPING;
import static com.github.saphyra.skyxplore.user.UserController.EMAIL_EXISTS_MAPPING;
import static com.github.saphyra.skyxplore.user.UserController.REGISTRATION_MAPPING;
import static com.github.saphyra.skyxplore.user.UserController.USERNAME_EXISTS_MAPPING;

@Component
class UriConfigurationImpl implements UriConfiguration {
    private static final List<AllowedUri> ALLOWED_URIS = Stream.concat(
        Stream.of(
            new AllowedUri("/", HttpMethod.GET),
            new AllowedUri(REGISTRATION_MAPPING, HttpMethod.POST),
            new AllowedUri(EMAIL_EXISTS_MAPPING, HttpMethod.POST),
            new AllowedUri(USERNAME_EXISTS_MAPPING, HttpMethod.POST),
            new AllowedUri(PageController.INDEX_MAPPING, HttpMethod.GET)
        ),
        RequestConstants.PROPERTY_PATHS.stream()
            .map(propertyPath -> new AllowedUri(propertyPath, HttpMethod.GET))
    ).collect(Collectors.toList());

    private static final List<AllowedUri> NON_SESSION_EXTENDING_URIS = Arrays.asList(
        new AllowedUri(GET_NUMBER_OF_FRIEND_REQUESTS_MAPPING, HttpMethod.GET),
        new AllowedUri(GET_NUMBER_OF_UNREAD_MAILS_MAPPING, HttpMethod.GET),
        new AllowedUri(GET_NUMBER_OF_NOTIFICATIONS_MAPPING, HttpMethod.GET),
        new AllowedUri(GET_INVITATIONS_MAPPING, HttpMethod.GET),
        new AllowedUri(GET_MESSAGES_MAPPING, HttpMethod.GET)
    );

    @Override
    public List<AllowedUri> getAllowedUris() {
        return ALLOWED_URIS;
    }

    @Override
    public List<AllowedUri> getNonSessionExtendingUris() {
        return NON_SESSION_EXTENDING_URIS;
    }

    @Override
    public Set<RoleSetting> getRoleSettings() {
        return new HashSet<>();
    }
}
