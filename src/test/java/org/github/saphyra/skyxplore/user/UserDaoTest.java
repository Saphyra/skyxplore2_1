package org.github.saphyra.skyxplore.user;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.USER_EMAIL;
import static skyxplore.testutil.TestUtils.USER_ID;
import static skyxplore.testutil.TestUtils.createUser;
import static skyxplore.testutil.TestUtils.createUserEntity;

import org.github.saphyra.skyxplore.user.CredentialsDao;
import org.github.saphyra.skyxplore.user.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.github.saphyra.skyxplore.user.UserRepository;
import org.github.saphyra.skyxplore.user.domain.user.SkyXpUser;
import org.github.saphyra.skyxplore.user.domain.user.SkyXpUserConverter;
import org.github.saphyra.skyxplore.user.domain.user.UserEntity;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private SkyXpUserConverter skyXpUserConverter;

    @Mock
    private CredentialsDao credentialsDao;

    @InjectMocks
    private UserDao underTest;

    @Test
    public void testDeleteShouldCallRepositoryAndDao() {
        //WHEN
        underTest.delete(USER_ID);
        //THEN
        verify(userRepository).deleteById(USER_ID);
        verify(credentialsDao).deleteById(USER_ID);
    }

    @Test
    public void testFindUserByEmailShouldCallRepositoryAndReturn() {
        //GIVEN
        UserEntity userEntity = createUserEntity();
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(userEntity);

        SkyXpUser user = createUser();
        when(skyXpUserConverter.convertEntity(userEntity)).thenReturn(user);
        //WHEN
        SkyXpUser result = underTest.findUserByEmail(USER_EMAIL);
        //THEN
        verify(userRepository).findByEmail(USER_EMAIL);
        verify(skyXpUserConverter).convertEntity(userEntity);
        assertEquals(user, result);
    }
}