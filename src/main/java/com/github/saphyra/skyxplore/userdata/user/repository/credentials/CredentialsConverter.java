package com.github.saphyra.skyxplore.userdata.user.repository.credentials;

import org.springframework.stereotype.Component;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.skyxplore.userdata.user.domain.SkyXpCredentials;

@Component
class CredentialsConverter extends ConverterBase<CredentialsEntity, SkyXpCredentials> {
    @Override
    public SkyXpCredentials processEntityConversion(CredentialsEntity entity) {
        return SkyXpCredentials.builder()
            .userId(entity.getUserId())
            .userName(entity.getUserName())
            .password(entity.getPassword())
            .build();
    }

    @Override
    public CredentialsEntity processDomainConversion(SkyXpCredentials domain) {
        return new CredentialsEntity(domain.getUserId(), domain.getUserName(), domain.getPassword());
    }
}
