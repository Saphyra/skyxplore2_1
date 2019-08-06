package com.github.saphyra.skyxplore.userdata.user.repository.user;


import com.github.saphyra.skyxplore.common.ObjectMapperDelegator;
import com.github.saphyra.skyxplore.userdata.user.domain.Role;
import com.github.saphyra.skyxplore.userdata.user.domain.SkyXpUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;


@RunWith(MockitoJUnitRunner.class)
public class SkyXpUserConverterTest {
    private static final String  USER_ID = "user_id";
    private static final String EMAIL = "email";
    private static final Set<Role> ROLES = new HashSet<>(Arrays.asList(Role.ADMIN));
    private static final String ROLES_STRING = "roles_string";

    @Mock
    private ObjectMapperDelegator objectMapperDelegator;

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
        userEntity.setRoles(ROLES_STRING);

        given(objectMapperDelegator.readValue(ROLES_STRING, Role[].class)).willReturn(new ArrayList<>(ROLES));
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
        SkyXpUser user = SkyXpUser.builder()
            .userId(USER_ID)
            .email(EMAIL)
            .roles(ROLES)
            .build();

        given(objectMapperDelegator.writeValueAsString(ROLES)).willReturn(ROLES_STRING);
        //WHEN
        UserEntity result = underTest.convertDomain(user);
        //THEN
        assertThat(result.getUserId()).isEqualTo(USER_ID);
        assertThat(result.getEmail()).isEqualTo(EMAIL);
        assertThat(result.getRoles()).isEqualTo(ROLES_STRING);
    }
}