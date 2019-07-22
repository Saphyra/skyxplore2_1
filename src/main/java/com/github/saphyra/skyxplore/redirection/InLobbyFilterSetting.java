package com.github.saphyra.skyxplore.redirection;

import com.github.saphyra.authservice.redirection.RedirectionFilterSettings;
import com.github.saphyra.authservice.redirection.domain.ProtectedUri;
import com.github.saphyra.authservice.redirection.domain.RedirectionContext;
import com.github.saphyra.skyxplore.characterstatus.CharacterStatusQueryService;
import com.github.saphyra.skyxplore.characterstatus.domain.CharacterStatus;
import com.github.saphyra.skyxplore.common.PageController;
import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class InLobbyFilterSetting implements RedirectionFilterSettings {
    private static final ProtectedUri PROTECTED_URI = new ProtectedUri(RequestConstants.WEB_PREFIX + "/**", HttpMethod.GET);
    private static final List<String> ALLOWED_URIS = Arrays.asList(
        PageController.LOBBY_PAGE_MAPPING
    );

    private final CookieUtil cookieUtil;
    private final CharacterStatusQueryService characterStatusQueryService;

    @Override
    public ProtectedUri getProtectedUri() {
        return PROTECTED_URI;
    }

    @Override
    public boolean shouldRedirect(RedirectionContext redirectionContext) {
        boolean shouldRedirect = !redirectionContext.isRest()
            && !ALLOWED_URIS.contains(redirectionContext.getRequestUri())
            && isCharacterInLobby(redirectionContext);
        log.debug("shouldRedirect: {}", shouldRedirect);
        return shouldRedirect;
    }

    private boolean isCharacterInLobby(RedirectionContext redirectionContext) {
        return cookieUtil.getCookie(redirectionContext.getRequest(), RequestConstants.COOKIE_CHARACTER_ID)
            .map(characterStatusQueryService::getCharacterStatus)
            .filter(characterStatus -> characterStatus == CharacterStatus.IN_LOBBY)
            .isPresent();
    }

    @Override
    public String getRedirectionPath(RedirectionContext redirectionContext) {
        return PageController.LOBBY_PAGE_MAPPING;
    }

    @Override
    public Integer getFilterOrder() {
        return 1;
    }

    @Override
    public String toString() {
        return "InLobbyFilterSetting - " + PROTECTED_URI.toString();
    }
}
