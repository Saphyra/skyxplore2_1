package com.github.saphyra.skyxplore.auth;

import java.util.HashSet;

import org.springframework.stereotype.Component;

import com.github.saphyra.authservice.auth.domain.Credentials;
import com.github.saphyra.authservice.auth.domain.User;
import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.skyxplore.user.domain.SkyXpCredentials;
import com.github.saphyra.skyxplore.user.domain.SkyXpUser;
import com.github.saphyra.skyxplore.user.repository.credentials.CredentialsDao;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class UserConverter extends ConverterBase<SkyXpUser, User> {
    private final CredentialsDao credentialsDao;

    @Override
    public User processEntityConversion(SkyXpUser entity) {
        SkyXpCredentials skyXpCredentials = credentialsDao.findById(entity.getUserId())
            .orElseThrow(() -> new NotFoundException("Credentials not found for user " + entity.getUserId()));
        return User.builder()
            .userId(entity.getUserId())
            .credentials(
                Credentials.builder()
                    .userName(skyXpCredentials.getUserName())
                    .password(skyXpCredentials.getPassword())
                    .build()
            )
            .roles(new HashSet<>())
            .build();
    }

    @Override
    protected SkyXpUser processDomainConversion(User domain) {
        throw new UnsupportedOperationException("User cannot be converted to SkyXpUser.");
    }
}
