package com.github.saphyra.skyxplore.platform.auth;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.github.saphyra.authservice.auth.ErrorResponseResolver;
import com.github.saphyra.authservice.auth.domain.AuthContext;
import com.github.saphyra.authservice.auth.domain.RestErrorResponse;
import com.github.saphyra.exceptionhandling.ErrorTranslationAdapter;
import com.github.saphyra.exceptionhandling.domain.ErrorResponse;
import com.github.saphyra.skyxplore.common.ErrorCode;
import com.github.saphyra.skyxplore.common.PageController;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ErrorResponseResolverImpl implements ErrorResponseResolver {
    private final ErrorTranslationAdapter errorTranslationAdapter;

    @Override
    public RestErrorResponse getRestErrorResponse(AuthContext authContext) {
        return new RestErrorResponse(HttpStatus.UNAUTHORIZED, createErrorResponse(authContext.getRequest()));
    }

    private ErrorResponse createErrorResponse(HttpServletRequest request) {
        return ErrorResponse.builder()
            .httpStatus(HttpStatus.UNAUTHORIZED.value())
            .errorCode(ErrorCode.BAD_CREDENTIALS.name())
            .localizedMessage(errorTranslationAdapter.translateMessage(request, ErrorCode.BAD_CREDENTIALS.name(), new HashMap<>()))
            .params(new HashMap<>())
            .build();
    }

    @Override
    public String getRedirectionPath(AuthContext authContext) {
        return PageController.INDEX_MAPPING;
    }
}
