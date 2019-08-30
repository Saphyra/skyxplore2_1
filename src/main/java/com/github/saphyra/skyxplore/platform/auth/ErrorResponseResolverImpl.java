package com.github.saphyra.skyxplore.platform.auth;

import com.github.saphyra.authservice.auth.ErrorResponseResolver;
import com.github.saphyra.authservice.auth.domain.AccessStatus;
import com.github.saphyra.authservice.auth.domain.AuthContext;
import com.github.saphyra.authservice.auth.domain.RestErrorResponse;
import com.github.saphyra.exceptionhandling.ErrorTranslationAdapter;
import com.github.saphyra.exceptionhandling.domain.ErrorResponse;
import com.github.saphyra.skyxplore.common.ErrorCode;
import com.github.saphyra.skyxplore.common.PageController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class ErrorResponseResolverImpl implements ErrorResponseResolver {
    private static final String GENERAL_ERROR_KEY = "generalErrorKey";
    private static final String MESSAGE_UNKNOWN_ACCESS_STATUS = "Unknown accessStatus: %s";

    private final ErrorTranslationAdapter errorTranslationAdapter;

    @Override
    public RestErrorResponse getRestErrorResponse(AuthContext authContext) {
        ErrorResponse errorResponse = createErrorResponse(authContext.getRequest(), authContext.getAccessStatus());
        return new RestErrorResponse(HttpStatus.UNAUTHORIZED, errorResponse);
    }

    private ErrorResponse createErrorResponse(HttpServletRequest request, AccessStatus accessStatus) {
        ErrorCode errorCode = getErrorCode(accessStatus);
        HashMap<String, String> params = new HashMap<>();
        params.put(GENERAL_ERROR_KEY, String.format(MESSAGE_UNKNOWN_ACCESS_STATUS, accessStatus.name()));
        return ErrorResponse.builder()
            .httpStatus(HttpStatus.UNAUTHORIZED.value())
            .errorCode(errorCode.name())
            .localizedMessage(errorTranslationAdapter.translateMessage(request, errorCode.name(), params))
            .params(params)
            .build();
    }

    private ErrorCode getErrorCode(AccessStatus accessStatus) {
        switch (accessStatus) {
            case LOGIN_FAILED:
                return ErrorCode.BAD_CREDENTIALS;
            case ACCESS_TOKEN_EXPIRED:
            case ACCESS_TOKEN_NOT_FOUND:
            case COOKIE_NOT_FOUND:
            case INVALID_USER_ID:
            case USER_NOT_FOUND:
                return ErrorCode.SESSION_EXPIRED;
            default:
                return ErrorCode.GENERAL_ERROR;
        }
    }

    @Override
    public String getRedirectionPath(AuthContext authContext) {
        return PageController.INDEX_MAPPING;
    }
}
