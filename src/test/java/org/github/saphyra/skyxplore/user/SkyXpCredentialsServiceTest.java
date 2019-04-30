package org.github.saphyra.skyxplore.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.github.saphyra.skyxplore.user.domain.SkyXpCredentials;
import org.github.saphyra.skyxplore.user.repository.credentials.CredentialsDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import skyxplore.exception.BadCredentialsException;

@RunWith(MockitoJUnitRunner.class)
public class SkyXpCredentialsServiceTest {
    private static final String USER_ID = "user_id";
    private static final String USER_NAME = "user_name";
    private static final String PASSWORD = "password";
    public static final SkyXpCredentials CREDENTIALS = new SkyXpCredentials(USER_ID, USER_NAME, PASSWORD);

    @Mock
    private CredentialsDao credentialsDao;

    @InjectMocks
    private CredentialsService underTest;

    @Test(expected = BadCredentialsException.class)
    public void testGetByUserIdShouldThrowExceptionWhenNotFound() {
        //GIVEN
        when(credentialsDao.getByUserId(USER_ID)).thenReturn(null);
        //WHEN
        underTest.getByUserId(USER_ID);
    }

    @Test
    public void testGetByUserIdShouldCallDaoAndReturn() {
        //GIVEN
        when(credentialsDao.getByUserId(USER_ID)).thenReturn(CREDENTIALS);
        //WHEN
        SkyXpCredentials result = underTest.getByUserId(USER_ID);
        //THEN
        verify(credentialsDao).getByUserId(USER_ID);
        assertThat(result).isEqualTo(CREDENTIALS);
    }

    @Test
    public void testIsUserNameExistsShouldReturn() {
        //GIVEN
        when(credentialsDao.getCredentialsByName(USER_NAME)).thenReturn(Optional.of(CREDENTIALS));
        //WHEN
        boolean result = underTest.isUserNameExists(USER_NAME);
        //THEN
        assertThat(result).isTrue();
    }

    @Test
    public void testSaveShouldCallDao() {
        //GIVEN
        //WHEN
        underTest.save(CREDENTIALS);
        //THEN
        verify(credentialsDao).save(CREDENTIALS);
    }
}