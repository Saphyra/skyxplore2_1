package org.github.saphyra.skyxplore.user.repository.credentials;

import org.github.saphyra.skyxplore.user.domain.SkyXpCredentials;
import org.springframework.stereotype.Component;

import com.github.saphyra.converter.ConverterBase;

@Component
class CredentialsConverter extends ConverterBase<CredentialsEntity, SkyXpCredentials> {
    @Override
    public SkyXpCredentials processEntityConversion(CredentialsEntity entity) {
        if (entity == null) {
            return null;
        }
        return new SkyXpCredentials(entity.getUserId(), entity.getUserName(), entity.getPassword());
    }

    @Override
    public CredentialsEntity processDomainConversion(SkyXpCredentials domain) {
        if (domain == null) {
            throw new IllegalArgumentException("domain must not be null.");
        }
        return new CredentialsEntity(domain.getUserId(), domain.getUserName(), domain.getPassword());
    }
}
