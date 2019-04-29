package org.github.saphyra.skyxplore.user.repository.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.github.saphyra.skyxplore.event.AccountDeletedEvent;
import org.github.saphyra.skyxplore.user.domain.SkyXpUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoTest {
    private static final String USER_ID = "user_id";
    private static final String EMAIL = "email";
    private static final String ROLES_STRING = "roles_string";

    @Mock
    private UserRepository userRepository;

    @Mock
    private SkyXpUserConverter skyXpUserConverter;

    @InjectMocks
    private UserDao underTest;

    @Test
    public void testDeleteShouldCallRepositoryAndDao() {
        //WHEN
        underTest.delete(new AccountDeletedEvent(USER_ID));
        //THEN
        verify(userRepository).deleteById(USER_ID);
    }

    @Test
    public void testFindUserByEmailShouldCallRepositoryAndReturn() {
        //GIVEN
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(USER_ID);
        userEntity.setEmail(EMAIL);
        userEntity.setRoles(ROLES_STRING);
        when(userRepository.findByEmail(EMAIL)).thenReturn(userEntity);

        SkyXpUser user = new SkyXpUser();
        user.setUserId(USER_ID);
        user.setEmail(EMAIL);
        when(skyXpUserConverter.convertEntity(userEntity)).thenReturn(user);
        //WHEN
        SkyXpUser result = underTest.findUserByEmail(EMAIL);
        //THEN
        verify(userRepository).findByEmail(EMAIL);
        verify(skyXpUserConverter).convertEntity(userEntity);
        assertThat(result).isEqualTo(user);
    }
}