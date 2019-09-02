package com.github.saphyra.skyxplore.platform.auth;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.authservice.auth.domain.AccessStatus;
import com.github.saphyra.skyxplore.common.ErrorCode;

@RunWith(MockitoJUnitRunner.class)
public class ErrorCodeResolverTest {
    @InjectMocks
    private ErrorCodeResolver underTest;

    @Test
    public void getErrorCode_loginFailed() {
        //WHEN
        ErrorCode result = underTest.getErrorCode(AccessStatus.LOGIN_FAILED);
        //THEN
        assertThat(result).isEqualTo(ErrorCode.BAD_CREDENTIALS);
    }

    @Test
    public void getErrorCode_accessTokenExpired() {
        //WHEN
        ErrorCode result = underTest.getErrorCode(AccessStatus.ACCESS_TOKEN_EXPIRED);
        //THEN
        assertThat(result).isEqualTo(ErrorCode.SESSION_EXPIRED);
    }

    @Test
    public void getErrorCode_accessTokenNotFound() {
        //WHEN
        ErrorCode result = underTest.getErrorCode(AccessStatus.ACCESS_TOKEN_NOT_FOUND);
        //THEN
        assertThat(result).isEqualTo(ErrorCode.SESSION_EXPIRED);
    }

    @Test
    public void getErrorCode_cookieNotFound() {
        //WHEN
        ErrorCode result = underTest.getErrorCode(AccessStatus.COOKIE_NOT_FOUND);
        //THEN
        assertThat(result).isEqualTo(ErrorCode.SESSION_EXPIRED);
    }

    @Test
    public void getErrorCode_invalidUserId() {
        //WHEN
        ErrorCode result = underTest.getErrorCode(AccessStatus.INVALID_USER_ID);
        //THEN
        assertThat(result).isEqualTo(ErrorCode.SESSION_EXPIRED);
    }

    @Test
    public void getErrorCode_userNotFound() {
        //WHEN
        ErrorCode result = underTest.getErrorCode(AccessStatus.USER_NOT_FOUND);
        //THEN
        assertThat(result).isEqualTo(ErrorCode.SESSION_EXPIRED);
    }

    @Test
    public void getErrorCode_generalError() {
        //WHEN
        ErrorCode result = underTest.getErrorCode(AccessStatus.FORBIDDEN);
        //THEN
        assertThat(result).isEqualTo(ErrorCode.GENERAL_ERROR);
    }
}