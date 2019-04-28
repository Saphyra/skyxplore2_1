package org.github.saphyra.skyxplore.user;

import org.github.saphyra.skyxplore.user.domain.SkyXpCredentials;
import org.github.saphyra.skyxplore.user.repository.credentials.CredentialsDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.exception.BadCredentialsException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.USER_ID;
import static skyxplore.testutil.TestUtils.USER_NAME;
import static skyxplore.testutil.TestUtils.createCredentials;

@RunWith(MockitoJUnitRunner.class)
public class SkyXpCredentialsServiceTest {
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
        SkyXpCredentials skyXpCredentials = createCredentials();
        when(credentialsDao.getByUserId(USER_ID)).thenReturn(skyXpCredentials);
        //WHEN
        SkyXpCredentials result = underTest.getByUserId(USER_ID);
        //THEN
        verify(credentialsDao).getByUserId(USER_ID);
        assertThat(result).isEqualTo(skyXpCredentials);
    }

    @Test
    public void testIsUserNameExistsShouldReturn() {
        //GIVEN
        when(credentialsDao.getCredentialsByName(USER_NAME)).thenReturn(Optional.of(createCredentials()));
        //WHEN
        boolean result = underTest.isUserNameExists(USER_NAME);
        //THEN
        assertThat(result).isTrue();
        verify(credentialsDao).getCredentialsByName(USER_NAME);
    }

    @Test
    public void testSaveShouldCallDao() {
        //GIVEN
        SkyXpCredentials skyXpCredentials = createCredentials();
        //WHEN
        underTest.save(skyXpCredentials);
        //THEN
        verify(credentialsDao).save(skyXpCredentials);
    }
}