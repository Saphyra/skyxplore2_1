package com.github.saphyra.skyxplore.redirection;

import com.github.saphyra.authservice.auth.AuthService;
import com.github.saphyra.authservice.auth.domain.AccessStatus;
import com.github.saphyra.authservice.redirection.RedirectionFilterSettings;
import com.github.saphyra.authservice.redirection.domain.ProtectedUri;
import com.github.saphyra.authservice.redirection.domain.RedirectionContext;
import com.github.saphyra.skyxplore.common.PageController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoggedInFilterSetting implements RedirectionFilterSettings {
    private static final ProtectedUri PROTECTED_URI = new ProtectedUri(PageController.INDEX_MAPPING, HttpMethod.GET);

    private final AuthService authService;

    @Override
    public ProtectedUri getProtectedUri() {
        return PROTECTED_URI;
    }

    @Override
    public boolean shouldRedirect(RedirectionContext redirectionContext) {
        return redirectionContext.getAccessTokenId().isPresent()
            && redirectionContext.getUserId().isPresent()
            && getAccessStatus(redirectionContext) == AccessStatus.GRANTED;
    }

    private AccessStatus getAccessStatus(RedirectionContext redirectionContext) {
        return authService.canAccess(
            redirectionContext.getRequestUri(),
            redirectionContext.getRequestMethod(),
            redirectionContext.getUserId().get(),
            redirectionContext.getAccessTokenId().get()
        );
    }

    @Override
    public String getRedirectionPath(RedirectionContext redirectionContext) {
        return PageController.CHARACTER_SELECT_MAPPING;
    }

    @Override
    public Integer getFilterOrder() {
        return 0;
    }

    @Override
    public String toString() {
        return "LoggedInFilterSetting - " + PROTECTED_URI.toString();
    }
}
