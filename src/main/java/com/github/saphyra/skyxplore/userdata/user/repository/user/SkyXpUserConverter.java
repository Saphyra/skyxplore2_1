package com.github.saphyra.skyxplore.userdata.user.repository.user;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.skyxplore.userdata.user.domain.Role;
import com.github.saphyra.skyxplore.userdata.user.domain.SkyXpUser;
import lombok.RequiredArgsConstructor;
import com.github.saphyra.skyxplore.common.ObjectMapperDelegator;

import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@RequiredArgsConstructor
class SkyXpUserConverter extends ConverterBase<UserEntity, SkyXpUser> {
    private final ObjectMapperDelegator objectMapperDelegator;

    @Override
    public SkyXpUser processEntityConversion(UserEntity entity) {
        return SkyXpUser.builder()
            .userId(entity.getUserId())
            .email(entity.getEmail())
            .roles(new HashSet<>(objectMapperDelegator.readValue(entity.getRoles(), Role[].class)))
            .build();
    }

    @Override
    public UserEntity processDomainConversion(SkyXpUser domain) {
        return UserEntity.builder()
            .userId(domain.getUserId())
            .email(domain.getEmail())
            .roles(objectMapperDelegator.writeValueAsString(domain.getRoles()))
            .build();
    }
}
