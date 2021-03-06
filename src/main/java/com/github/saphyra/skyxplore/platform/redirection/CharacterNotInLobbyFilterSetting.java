package com.github.saphyra.skyxplore.platform.redirection;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.github.saphyra.authservice.redirection.RedirectionFilterSettings;
import com.github.saphyra.authservice.redirection.domain.ProtectedUri;
import com.github.saphyra.authservice.redirection.domain.RedirectionContext;
import com.github.saphyra.skyxplore.userdata.characterstatus.CharacterStatusQueryService;
import com.github.saphyra.skyxplore.userdata.characterstatus.domain.CharacterStatus;
import com.github.saphyra.skyxplore.common.PageController;
import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class CharacterNotInLobbyFilterSetting implements RedirectionFilterSettings {
    private static final ProtectedUri PROTECTED_URI = new ProtectedUri(PageController.LOBBY_PAGE_MAPPING, HttpMethod.GET);

    private final CharacterStatusQueryService characterStatusQueryService;
    private final CookieUtil cookieUtil;

    @Override
    public ProtectedUri getProtectedUri() {
        return PROTECTED_URI;
    }

    @Override
    public boolean shouldRedirect(RedirectionContext redirectionContext) {
        boolean shouldRedirect = cookieUtil.getCookie(redirectionContext.getRequest(), RequestConstants.COOKIE_CHARACTER_ID)
            .map(characterId -> characterStatusQueryService.getCharacterStatus(characterId) != CharacterStatus.IN_LOBBY)
            .orElse(true);

        log.debug("shouldRedirect: {}", shouldRedirect);
        return shouldRedirect;
    }

    @Override
    public String getRedirectionPath(RedirectionContext redirectionContext) {
        return PageController.OVERVIEW_MAPPING;
    }

    @Override
    public Integer getFilterOrder() {
        return 1;
    }

    @Override
    public String toString() {
        return "CharacterNotInLobbyFilterSetting - " + PROTECTED_URI.toString();
    }
}
