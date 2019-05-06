package org.github.saphyra.skyxplore.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.github.saphyra.skyxplore.auth.domain.SkyXpAccessToken;
import org.github.saphyra.skyxplore.auth.repository.AccessTokenDao;
import org.github.saphyra.skyxplore.common.DateTimeUtil;
import org.github.saphyra.skyxplore.common.ObjectMapperDelegator;
import org.github.saphyra.skyxplore.testing.configuration.DataSourceConfiguration;
import org.github.saphyra.skyxplore.user.domain.AccountDeleteRequest;
import org.github.saphyra.skyxplore.user.domain.SkyXpCredentials;
import org.github.saphyra.skyxplore.user.domain.SkyXpUser;
import org.github.saphyra.skyxplore.user.repository.credentials.CredentialsDao;
import org.github.saphyra.skyxplore.user.repository.user.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.saphyra.encryption.impl.PasswordService;
import com.github.saphyra.util.IdGenerator;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DeleteAccountServiceIntegrationTest.TestConfig.class)
//TODO extend with other daos
public class DeleteAccountServiceIntegrationTest {
    private static final String PASSWORD = "password";
    private static final String USER_ID = "user_id";
    private static final String ACCESS_TOKEN_ID = "access_token_id";
    private static final OffsetDateTime NOW = OffsetDateTime.now(ZoneOffset.UTC);


    @Autowired
    private DeleteAccountService deleteAccountService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CredentialsDao credentialsDao;

    @Autowired
    private AccessTokenDao accessTokenDao;

    @Test
    public void testDeleteAccount() {
        //GIVEN
        saveCharacter();
        saveCredentials();
        saveAccessToken();
        //TODO blockedcharacter
        //TODO friendrequest
        //TODO friendsip
        //TODO mail
        //TODO ship
        //TODO slot

        AccountDeleteRequest accountDeleteRequest = new AccountDeleteRequest(PASSWORD);
        //WHEN
        deleteAccountService.deleteAccount(accountDeleteRequest, USER_ID);
        //THEN
        assertThat(userDao.findById(USER_ID)).isEmpty();
        assertThat(credentialsDao.findById(USER_ID)).isEmpty();
        assertThat(accessTokenDao.findByUserId(USER_ID)).isEmpty();
    }

    private void saveAccessToken() {
        SkyXpAccessToken accessToken = SkyXpAccessToken.builder()
            .userId(USER_ID)
            .accessTokenId(ACCESS_TOKEN_ID)
            .lastAccess(NOW)
            .build();
        accessTokenDao.save(accessToken);
    }

    private void saveCredentials() {
        SkyXpCredentials credentials = new SkyXpCredentials();
        credentials.setUserId(USER_ID);
        credentials.setPassword(PASSWORD);
        credentials.setUserName("");
        credentialsDao.save(credentials);
    }

    private void saveCharacter() {
        SkyXpUser user = SkyXpUser.builder()
            .userId(USER_ID)
            .email("")
            .build();
        userDao.save(user);
    }

    @TestConfiguration
    @EnableJpaRepositories(basePackages = "org.github.saphyra.skyxplore")
    @EnableTransactionManagement
    @EntityScan(basePackages = "org.github.saphyra.skyxplore")
    @Import(DataSourceConfiguration.class)
    @ComponentScan(basePackageClasses = {
        UserDao.class,
        CredentialsDao.class,
        AccessTokenDao.class
    })
    static class TestConfig {
        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper();
        }

        @Bean
        public ObjectMapperDelegator objectMapperDelegator(ObjectMapper objectMapper) {
            return new ObjectMapperDelegator(objectMapper);
        }

        @Bean
        public DateTimeUtil dateTimeUtil() {
            return new DateTimeUtil();
        }

        @Bean
        public DeleteAccountService deleteAccountService(
            PasswordService passwordService,
            CredentialsService credentialsService,
            ApplicationEventPublisher applicationEventPublisher
        ) {
            return new DeleteAccountService(passwordService, credentialsService, applicationEventPublisher);
        }

        @Bean
        public CredentialsService credentialsService() {
            CredentialsService credentialsService = Mockito.mock(CredentialsService.class);
            given(credentialsService.findByUserId(USER_ID)).willReturn(new SkyXpCredentials(USER_ID, "", PASSWORD));
            return credentialsService;
        }

        @Bean
        public PasswordService passwordService() {
            PasswordService passwordService = mock(PasswordService.class);
            given(passwordService.authenticate(anyString(), anyString())).willReturn(true);
            return passwordService;
        }

        @SuppressWarnings("unused")
        @MockBean
        private IdGenerator idGenerator;
    }
}