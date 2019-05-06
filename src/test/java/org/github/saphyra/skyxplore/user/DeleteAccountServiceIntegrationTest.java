package org.github.saphyra.skyxplore.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.github.saphyra.skyxplore.auth.domain.SkyXpAccessToken;
import org.github.saphyra.skyxplore.auth.repository.AccessTokenDao;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.character.repository.CharacterDao;
import org.github.saphyra.skyxplore.common.DateTimeUtil;
import org.github.saphyra.skyxplore.common.ObjectMapperDelegator;
import org.github.saphyra.skyxplore.community.blockedcharacter.domain.BlockedCharacter;
import org.github.saphyra.skyxplore.community.blockedcharacter.repository.BlockedCharacterDao;
import org.github.saphyra.skyxplore.community.friendship.domain.FriendRequest;
import org.github.saphyra.skyxplore.community.friendship.repository.friendrequest.FriendRequestDao;
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
import com.github.saphyra.encryption.EnableEncryption;
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
    private static final String CHARACTER_ID = "character_id";
    private static final String BLOCKED_CHARACTER_ID = "blocked_character_id";
    private static final String FRIEND_ID = "friend_id";

    @Autowired
    private DeleteAccountService deleteAccountService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CredentialsDao credentialsDao;

    @Autowired
    private AccessTokenDao accessTokenDao;

    @Autowired
    private BlockedCharacterDao blockedCharacterDao;

    @Autowired
    private CharacterDao characterDao;

    @Autowired
    private FriendRequestDao friendRequestDao;

    @Test
    public void testDeleteAccount() {
        //GIVEN
        saveUser();
        saveCredentials();
        saveAccessToken();
        saveCharacter();
        saveBlockedCharacters();
        saveFriendRequests();
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
        assertThat(characterDao.getByUserId(USER_ID)).isEmpty();
        assertThat(blockedCharacterDao.getByCharacterIdOrBlockedCharacterId(CHARACTER_ID, BLOCKED_CHARACTER_ID)).isEmpty();
        assertThat(friendRequestDao.getByCharacterIdOrFriendId(CHARACTER_ID, FRIEND_ID)).isEmpty();
    }

    private void saveFriendRequests() {
        FriendRequest friendRequest1 = FriendRequest.builder()
            .friendRequestId("r1")
            .characterId(CHARACTER_ID)
            .friendId(FRIEND_ID)
            .build();

        FriendRequest friendRequest2 = FriendRequest.builder()
            .friendRequestId("r2")
            .characterId(FRIEND_ID)
            .friendId(CHARACTER_ID)
            .build();
        friendRequestDao.save(friendRequest1);
        friendRequestDao.save(friendRequest2);
    }

    private void saveCharacter() {
        SkyXpCharacter character = SkyXpCharacter.builder()
            .characterId(CHARACTER_ID)
            .userId(USER_ID)
            .characterName("")
            .build();
        characterDao.save(character);
    }

    private void saveBlockedCharacters() {
        BlockedCharacter blockedCharacter1 = BlockedCharacter.builder()
            .blockedCharacterEntityId(1L)
            .characterId(CHARACTER_ID)
            .blockedCharacterId(BLOCKED_CHARACTER_ID)
            .build();

        BlockedCharacter blockedCharacter2 = BlockedCharacter.builder()
            .blockedCharacterEntityId(2L)
            .characterId(BLOCKED_CHARACTER_ID)
            .blockedCharacterId(CHARACTER_ID)
            .build();

        blockedCharacterDao.save(blockedCharacter1);
        blockedCharacterDao.save(blockedCharacter2);
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

    private void saveUser() {
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
        AccessTokenDao.class,
        CharacterDao.class,
        BlockedCharacterDao.class,
        FriendRequestDao.class
    })
    @EnableEncryption
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