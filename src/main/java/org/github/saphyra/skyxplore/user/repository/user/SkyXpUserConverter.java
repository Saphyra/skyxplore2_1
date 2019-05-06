package org.github.saphyra.skyxplore.user.repository.user;

import com.github.saphyra.converter.ConverterBase;
import lombok.RequiredArgsConstructor;
import org.github.saphyra.skyxplore.common.ObjectMapperDelegator;
import org.github.saphyra.skyxplore.user.domain.Role;
import org.github.saphyra.skyxplore.user.domain.SkyXpUser;
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
