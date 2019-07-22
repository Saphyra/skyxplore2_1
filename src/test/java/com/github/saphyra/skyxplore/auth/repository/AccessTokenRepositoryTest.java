package com.github.saphyra.skyxplore.auth.repository;

import com.github.saphyra.skyxplore.testing.configuration.DataSourceConfiguration;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AccessTokenRepositoryTest.TestConfig.class)
@ActiveProfiles("int-test")
public class AccessTokenRepositoryTest {
    private static final String ACCESS_TOKEN_ID_1 = "access_token_id_1";
    private static final String ACCESS_TOKEN_ID_2 = "access_token_id_2";
    private static final String ACCESS_TOKEN_ID_3 = "access_token_id_3";
    private static final String USER_ID_1 = "user_id_1";
    private static final String USER_ID_2 = "user_id_2";
    private static final Long LAST_ACCESS = 876L;
    private static final String CHARACTER_ID_1 = "character_id_1";
    private static final String CHARACTER_ID_2 = "character_id_2";

    @Autowired
    private AccessTokenRepository underTest;

    @After
    public void tearDown() {
        underTest.deleteAll();
    }

    @Test
    public void cleanUpCharacterId() {
        //GIVEN
        AccessTokenEntity entity1 = AccessTokenEntity.builder()
            .accessTokenId(ACCESS_TOKEN_ID_1)
            .userId(USER_ID_1)
            .lastAccess(LAST_ACCESS)
            .characterId(CHARACTER_ID_1)
            .build();

        AccessTokenEntity entity2 = AccessTokenEntity.builder()
            .accessTokenId(ACCESS_TOKEN_ID_2)
            .userId(USER_ID_1)
            .lastAccess(LAST_ACCESS)
            .characterId(CHARACTER_ID_2)
            .build();
        underTest.saveAll(Arrays.asList(entity1, entity2));
        //WHEN
        underTest.cleanUpCharacterId(CHARACTER_ID_1);
        //THEN
        assertThat(underTest.findByCharacterId(CHARACTER_ID_1)).isEmpty();
        Optional<AccessTokenEntity> optionalAccessTokenEntity = underTest.findById(ACCESS_TOKEN_ID_1);
        assertThat(optionalAccessTokenEntity).isPresent();
        assertThat(optionalAccessTokenEntity.get().getCharacterId()).isNull();
        assertThat(underTest.findByCharacterId(CHARACTER_ID_2)).contains(entity2);
    }

    @Test
    public void deleteByUserId() {
        //GIVEN
        AccessTokenEntity entity1 = AccessTokenEntity.builder()
            .accessTokenId(ACCESS_TOKEN_ID_1)
            .userId(USER_ID_1)
            .lastAccess(LAST_ACCESS)
            .build();

        AccessTokenEntity entity2 = AccessTokenEntity.builder()
            .accessTokenId(ACCESS_TOKEN_ID_2)
            .userId(USER_ID_1)
            .lastAccess(LAST_ACCESS)
            .build();

        AccessTokenEntity entity3 = AccessTokenEntity.builder()
            .accessTokenId(ACCESS_TOKEN_ID_3)
            .userId(USER_ID_2)
            .lastAccess(LAST_ACCESS)
            .build();

        underTest.saveAll(Arrays.asList(entity1, entity2, entity3));
        //WHEN
        underTest.deleteByUserId(USER_ID_1);
        //THEN
        assertThat(underTest.findAll()).containsOnly(entity3);
    }

    @Test
    public void deleteExpired() {
        //GIVEN
        AccessTokenEntity entity1 = AccessTokenEntity.builder()
            .accessTokenId(ACCESS_TOKEN_ID_1)
            .userId(USER_ID_1)
            .lastAccess(LAST_ACCESS)
            .build();

        AccessTokenEntity entity2 = AccessTokenEntity.builder()
            .accessTokenId(ACCESS_TOKEN_ID_2)
            .userId(USER_ID_1)
            .lastAccess(LAST_ACCESS - 2)
            .build();

        underTest.saveAll(Arrays.asList(entity1, entity2));
        //WHEN
        underTest.deleteExpired(LAST_ACCESS);
        //THEN
        assertThat(underTest.findAll()).containsOnly(entity1);
    }

    @Test
    public void findByCharacterId() {
        //GIVEN
        AccessTokenEntity entity1 = AccessTokenEntity.builder()
            .accessTokenId(ACCESS_TOKEN_ID_1)
            .userId(USER_ID_1)
            .lastAccess(LAST_ACCESS)
            .characterId(CHARACTER_ID_1)
            .build();

        AccessTokenEntity entity2 = AccessTokenEntity.builder()
            .accessTokenId(ACCESS_TOKEN_ID_2)
            .userId(USER_ID_1)
            .lastAccess(LAST_ACCESS)
            .characterId(CHARACTER_ID_2)
            .build();

        AccessTokenEntity entity3 = AccessTokenEntity.builder()
            .accessTokenId(ACCESS_TOKEN_ID_2)
            .userId(USER_ID_1)
            .lastAccess(LAST_ACCESS)
            .build();

        underTest.saveAll(Arrays.asList(entity1, entity2, entity3));
        //WHEN
        Optional<AccessTokenEntity> result = underTest.findByCharacterId(CHARACTER_ID_1);
        //THEN
        assertThat(result).contains(entity1);
    }

    @Test
    public void findByUserId() {
        //GIVEN
        AccessTokenEntity entity1 = AccessTokenEntity.builder()
            .accessTokenId(ACCESS_TOKEN_ID_1)
            .userId(USER_ID_1)
            .lastAccess(LAST_ACCESS)
            .build();

        AccessTokenEntity entity2 = AccessTokenEntity.builder()
            .accessTokenId(ACCESS_TOKEN_ID_2)
            .userId(USER_ID_2)
            .lastAccess(LAST_ACCESS)
            .build();

        underTest.saveAll(Arrays.asList(entity1, entity2));
        //WHEN
        Optional<AccessTokenEntity> result = underTest.findByUserId(USER_ID_1);
        //THEN
        assertThat(result).contains(entity1);
    }

    @TestConfiguration
    @EnableJpaRepositories(basePackageClasses = AccessTokenRepository.class)
    @EntityScan(basePackageClasses = AccessTokenEntity.class)
    @Import(DataSourceConfiguration.class)
    @Profile("int-test")
    static class TestConfig {

    }
}