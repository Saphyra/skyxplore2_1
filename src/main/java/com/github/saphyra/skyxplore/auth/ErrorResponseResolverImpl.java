package com.github.saphyra.skyxplore.auth;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.github.saphyra.authservice.auth.ErrorResponseResolver;
import com.github.saphyra.authservice.auth.domain.AuthContext;
import com.github.saphyra.authservice.auth.domain.RestErrorResponse;

@Component
public class ErrorResponseResolverImpl implements ErrorResponseResolver {
    @Override
    public RestErrorResponse getRestErrorResponse(AuthContext authContext) {
        return new RestErrorResponse(HttpStatus.UNAUTHORIZED, null);
    }

    @Override
    public String getRedirectionPath(AuthContext authContext) {
        return "/";
    }
}
