package org.github.saphyra.skyxplore.user.repository.user;

import org.github.saphyra.skyxplore.event.AccountDeletedEvent;
import org.github.saphyra.skyxplore.user.domain.SkyXpUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        Optional<UserEntity> entityOptional = Optional.of(userEntity);
        when(userRepository.findByEmail(EMAIL)).thenReturn(entityOptional);

        SkyXpUser user = SkyXpUser.builder()
            .userId(USER_ID)
            .email(EMAIL)
            .build();
        when(skyXpUserConverter.convertEntity(entityOptional)).thenReturn(Optional.of(user));
        //WHEN
        Optional<SkyXpUser> result = underTest.findUserByEmail(EMAIL);
        //THEN
        verify(userRepository).findByEmail(EMAIL);
        assertThat(result).contains(user);
    }
}