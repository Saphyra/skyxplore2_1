package com.github.saphyra.skyxplore.redirection;

import com.github.saphyra.authservice.redirection.RedirectionFilterSettings;
import com.github.saphyra.authservice.redirection.domain.ProtectedUri;
import com.github.saphyra.authservice.redirection.domain.RedirectionContext;
import com.github.saphyra.skyxplore.characterstatus.CharacterStatusCache;
import com.github.saphyra.skyxplore.characterstatus.domain.CharacterStatus;
import com.github.saphyra.skyxplore.common.PageController;
import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
//TODO unit test
public class CharacterSelectedFilterSetting implements RedirectionFilterSettings {
    private static final ProtectedUri PROTECTED_URI = new ProtectedUri(RequestConstants.WEB_PREFIX + "/**", HttpMethod.GET);
    private static final List<String> ALLOWED_URIS = Arrays.asList(
        PageController.COMMUNITY_MAPPING,
        PageController.EQUIPMENT_MAPPING,
        PageController.FACTORY_MAPPING,
        PageController.HANGAR_MAPPING,
        PageController.OVERVIEW_MAPPING,
        PageController.SHOP_MAPPING
    );

    private final AntPathMatcher antPathMatcher;
    private final CookieUtil cookieUtil;
    private final CharacterStatusCache characterStatusCache;

    @Override
    public ProtectedUri getProtectedUri() {
        return PROTECTED_URI;
    }

    @Override
    public boolean shouldRedirect(RedirectionContext redirectionContext) {
        return
            !redirectionContext.isRest()
                && !ALLOWED_URIS.contains(redirectionContext.getRequestUri())
                && !isPropertyPath(redirectionContext.getRequestUri())
                && isCharacterSelected(redirectionContext);
    }

    private boolean isPropertyPath(String requestUri) {
        return RequestConstants.PROPERTY_PATHS.stream()
            .anyMatch(propertyPath -> antPathMatcher.match(propertyPath, requestUri));
    }

    private boolean isCharacterSelected(RedirectionContext redirectionContext) {
        return cookieUtil.getCookie(redirectionContext.getRequest(), RequestConstants.COOKIE_CHARACTER_ID)
            .flatMap(characterStatusCache::get)
            .filter(characterStatus -> characterStatus == CharacterStatus.ACTIVE)
            .isPresent();
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
        return "CharacterSelectedFilterSetting - " + PROTECTED_URI.toString();
    }
}
