package org.github.saphyra.skyxplore.user.repository.user;

import java.util.HashSet;
import java.util.List;

import org.github.saphyra.skyxplore.common.ObjectMapperDelegator;
import org.github.saphyra.skyxplore.user.domain.Role;
import org.github.saphyra.skyxplore.user.domain.SkyXpUser;
import org.springframework.stereotype.Component;

import com.github.saphyra.converter.ConverterBase;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class SkyXpUserConverter extends ConverterBase<UserEntity, SkyXpUser> {
    private final ObjectMapperDelegator objectMapper;

    @Override
    public SkyXpUser processEntityConversion(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        SkyXpUser user = new SkyXpUser();
        user.setUserId(entity.getUserId());
        user.setEmail(entity.getEmail());
        List<Role> roles = objectMapper.readValue(entity.getRoles(), Role[].class);
        user.setRoles(new HashSet<>(roles));
        return user;
    }

    @Override
    public UserEntity processDomainConversion(SkyXpUser domain) {
        if (domain == null) {
            throw new IllegalArgumentException("domain must not be null.");
        }

        UserEntity entity = new UserEntity();
        entity.setUserId(domain.getUserId());
        entity.setEmail(domain.getEmail());
        entity.setRoles(objectMapper.writeValueAsString(domain.getRoles()));

        return entity;
    }
}
