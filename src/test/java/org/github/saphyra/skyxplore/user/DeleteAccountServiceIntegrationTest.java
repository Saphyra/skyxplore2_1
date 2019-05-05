package org.github.saphyra.skyxplore.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.saphyra.encryption.impl.PasswordService;
import com.github.saphyra.util.IdGenerator;
import org.github.saphyra.skyxplore.common.ObjectMapperDelegator;
import org.github.saphyra.skyxplore.testing.configuration.DataSourceConfiguration;
import org.github.saphyra.skyxplore.user.domain.AccountDeleteRequest;
import org.github.saphyra.skyxplore.user.domain.SkyXpCredentials;
import org.github.saphyra.skyxplore.user.domain.SkyXpUser;
import org.github.saphyra.skyxplore.user.repository.credentials.CredentialsDao;
import org.github.saphyra.skyxplore.user.repository.user.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DeleteAccountServiceIntegrationTest.TestConfig.class)
//TODO extend with other daos
public class DeleteAccountServiceIntegrationTest {
    private static final String PASSWORD = "password";
    private static final String USER_ID = "user_id";

    @MockBean
    private PasswordService passwordService;

    @Autowired
    private DeleteAccountService deleteAccountService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CredentialsDao credentialsDao;

    @Test
    public void testDeleteAccount() {
        //GIVEN
        SkyXpUser user = SkyXpUser.builder()
            .userId(USER_ID)
            .email("")
            .build();
        userDao.save(user);

        SkyXpCredentials credentials = new SkyXpCredentials();
        credentials.setUserId(USER_ID);
        credentials.setPassword(PASSWORD);
        credentials.setUserName("");
        credentialsDao.save(credentials);

        given(passwordService.authenticate(anyString(), anyString())).willReturn(true);

        AccountDeleteRequest accountDeleteRequest = new AccountDeleteRequest(PASSWORD);
        //WHEN
        deleteAccountService.deleteAccount(accountDeleteRequest, USER_ID);
        //THEN
        assertThat(userDao.findById(USER_ID)).isEmpty();
        assertThat(credentialsDao.findById(USER_ID)).isEmpty();
    }

    @TestConfiguration
    @ComponentScan(basePackageClasses = DeleteAccountService.class)
    @EnableJpaRepositories(basePackages = "org.github.saphyra.skyxplore.user")
    @EnableTransactionManagement
    @EntityScan(basePackages = "org.github.saphyra.skyxplore.user")
    @Import(DataSourceConfiguration.class)
    static class TestConfig {
        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper();
        }

        @Bean
        public ObjectMapperDelegator objectMapperDelegator(ObjectMapper objectMapper) {
            return new ObjectMapperDelegator(objectMapper);
        }

        @SuppressWarnings("unused")
        @MockBean
        private IdGenerator idGenerator;
    }
}