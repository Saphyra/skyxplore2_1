package skyxplore.dataaccess.db;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.USER_EMAIL;
import static skyxplore.testutil.TestUtils.USER_ID;
import static skyxplore.testutil.TestUtils.createUser;
import static skyxplore.testutil.TestUtils.createUserEntity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import skyxplore.dataaccess.db.repository.UserRepository;
import skyxplore.domain.user.SkyXpUser;
import skyxplore.domain.user.UserConverter;
import skyxplore.domain.user.UserEntity;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserConverter userConverter;

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
        when(userConverter.convertEntity(userEntity)).thenReturn(user);
        //WHEN
        SkyXpUser result = underTest.findUserByEmail(USER_EMAIL);
        //THEN
        verify(userRepository).findByEmail(USER_EMAIL);
        verify(userConverter).convertEntity(userEntity);
        assertEquals(user, result);
    }
}