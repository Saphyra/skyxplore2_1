package org.github.saphyra.skyxplore.user.repository.user;


import org.github.saphyra.skyxplore.user.domain.Role;
import org.github.saphyra.skyxplore.user.domain.SkyXpUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class SkyXpUserConverterTest {
    private static final String  USER_ID = "user_id";
    private static final String EMAIL = "email";

    @InjectMocks
    private SkyXpUserConverter underTest;

    @Test
    public void testConvertEntityShouldReturnNullWhenNull() {
        //GIVEN
        UserEntity entity = null;
        //WHEN
        SkyXpUser result = underTest.convertEntity(entity);
        //THEN
        assertThat(result).isNull();
    }

    @Test
    public void testConvertEntityShouldConvert() {
        //GIVEN
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(USER_ID);
        userEntity.setEmail(EMAIL);
        HashSet<Role> roles = new HashSet<>();
        roles.add(Role.ADMIN);
        userEntity.setRoles(roles);
        //WHEN
        SkyXpUser result = underTest.convertEntity(userEntity);
        //THEN
        assertThat(result.getUserId()).isEqualTo(USER_ID);
        assertThat(result.getEmail()).isEqualTo(EMAIL);
        assertThat(result.getRoles()).containsOnly(Role.ADMIN);
    }

    @Test
    public void testConvertDomainShouldConvert() {
        //GIVEN
        SkyXpUser user = new SkyXpUser();
        user.setUserId(USER_ID);
        user.setEmail(EMAIL);
        HashSet<Role> roles = new HashSet<>();
        roles.add(Role.ADMIN);
        user.setRoles(roles);
        //WHEN
        UserEntity result = underTest.convertDomain(user);
        //THEN
        assertThat(result.getUserId()).isEqualTo(USER_ID);
        assertThat(result.getEmail()).isEqualTo(EMAIL);
        assertThat(result.getRoles()).containsOnly(Role.ADMIN);
    }
}