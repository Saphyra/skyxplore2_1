package com.github.saphyra.skyxplore.userdata.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

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
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.saphyra.encryption.EnableEncryption;
import com.github.saphyra.encryption.impl.PasswordService;
import com.github.saphyra.skyxplore.platform.auth.domain.SkyXpAccessToken;
import com.github.saphyra.skyxplore.platform.auth.repository.AccessTokenDao;
import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.userdata.character.repository.CharacterDao;
import com.github.saphyra.skyxplore.common.DateTimeUtil;
import com.github.saphyra.skyxplore.common.ObjectMapperDelegator;
import com.github.saphyra.skyxplore.userdata.community.blockedcharacter.domain.BlockedCharacter;
import com.github.saphyra.skyxplore.userdata.community.blockedcharacter.repository.BlockedCharacterDao;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.FriendRequest;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.Friendship;
import com.github.saphyra.skyxplore.userdata.community.friendship.repository.friendrequest.FriendRequestDao;
import com.github.saphyra.skyxplore.userdata.community.friendship.repository.friendship.FriendshipDao;
import com.github.saphyra.skyxplore.userdata.community.mail.domain.Mail;
import com.github.saphyra.skyxplore.userdata.community.mail.repository.MailDao;
import com.github.saphyra.skyxplore.userdata.ship.domain.EquippedShip;
import com.github.saphyra.skyxplore.userdata.ship.repository.EquippedShipDao;
import com.github.saphyra.skyxplore.userdata.slot.domain.EquippedSlot;
import com.github.saphyra.skyxplore.userdata.slot.repository.SlotDao;
import com.github.saphyra.testing.configuration.DataSourceConfiguration;
import com.github.saphyra.skyxplore.userdata.user.domain.AccountDeleteRequest;
import com.github.saphyra.skyxplore.userdata.user.domain.SkyXpCredentials;
import com.github.saphyra.skyxplore.userdata.user.domain.SkyXpUser;
import com.github.saphyra.skyxplore.userdata.user.repository.credentials.CredentialsDao;
import com.github.saphyra.skyxplore.userdata.user.repository.user.UserDao;
import com.github.saphyra.util.IdGenerator;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DeleteAccountServiceIntegrationTest.TestConfig.class)
@ActiveProfiles("int-test")
public class DeleteAccountServiceIntegrationTest {
    private static final String PASSWORD = "password";
    private static final String USER_ID = "user_id";
    private static final String ACCESS_TOKEN_ID = "access_token_id";
    private static final OffsetDateTime NOW = OffsetDateTime.now(ZoneOffset.UTC);
    private static final String CHARACTER_ID = "character_id";
    private static final String BLOCKED_CHARACTER_ID = "blocked_character_id";
    private static final String FRIEND_ID = "friend_id";
    private static final String SHIP_ID = "ship_id";
    private static final String SLOT_ID_1 = "slot_id_1";
    private static final String SLOT_ID_2 = "slot_id_2";

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

    @Autowired
    private FriendshipDao friendshipDao;

    @Autowired
    private MailDao mailDao;

    @Autowired
    private EquippedShipDao equippedShipDao;

    @Autowired
    private SlotDao slotDao;

    @Test
    public void testDeleteAccount() {
        //GIVEN
        saveUser();
        saveCredentials();
        saveAccessToken();
        saveCharacter();
        saveBlockedCharacters();
        saveFriendRequests();
        saveFriendships();
        saveMails();
        saveShip();
        saveSlots();

        assertThat(userDao.findById(USER_ID)).isNotEmpty();
        assertThat(credentialsDao.findById(USER_ID)).isNotEmpty();
        assertThat(accessTokenDao.findByUserId(USER_ID)).isNotEmpty();
        assertThat(characterDao.getByUserId(USER_ID)).isNotEmpty();
        assertThat(blockedCharacterDao.getByCharacterIdOrBlockedCharacterId(CHARACTER_ID, BLOCKED_CHARACTER_ID)).isNotEmpty();
        assertThat(friendRequestDao.getByCharacterIdOrFriendId(CHARACTER_ID, FRIEND_ID)).isNotEmpty();
        assertThat(friendshipDao.getByCharacterIdOrFriendId(CHARACTER_ID, FRIEND_ID)).isNotEmpty();
        assertThat(mailDao.getMails(CHARACTER_ID)).isNotEmpty();
        assertThat(mailDao.getSentMails(CHARACTER_ID)).isNotEmpty();
        assertThat(equippedShipDao.findShipByCharacterId(CHARACTER_ID)).isNotEmpty();
        assertThat(slotDao.findById(SLOT_ID_1)).isNotEmpty();
        assertThat(slotDao.findById(SLOT_ID_2)).isNotEmpty();

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
        assertThat(friendshipDao.getByCharacterIdOrFriendId(CHARACTER_ID, FRIEND_ID)).isEmpty();
        assertThat(mailDao.getMails(CHARACTER_ID)).isEmpty();
        assertThat(mailDao.getSentMails(CHARACTER_ID)).isEmpty();
        assertThat(equippedShipDao.findShipByCharacterId(CHARACTER_ID)).isEmpty();
        assertThat(slotDao.findById(SLOT_ID_1)).isEmpty();
        assertThat(slotDao.findById(SLOT_ID_2)).isEmpty();
    }

    private void saveSlots() {
        EquippedSlot slot1 = EquippedSlot.builder()
            .slotId(SLOT_ID_1)
            .shipId(SHIP_ID)
            .leftSlot(0)
            .frontSlot(0)
            .rightSlot(0)
            .backSlot(0)
            .build();

        EquippedSlot slot2 = EquippedSlot.builder()
            .slotId(SLOT_ID_2)
            .shipId(SHIP_ID)
            .leftSlot(0)
            .frontSlot(0)
            .rightSlot(0)
            .backSlot(0)
            .build();

        slotDao.save(slot1);
        slotDao.save(slot2);
    }

    private void saveShip() {
        EquippedShip ship = EquippedShip.builder()
            .shipId(SHIP_ID)
            .characterId(CHARACTER_ID)
            .defenseSlotId("")
            .weaponSlotId("")
            .coreHull(0)
            .connectorSlot(0)
            .shipType("")
            .build();

        equippedShipDao.save(ship);
    }

    private void saveMails() {
        Mail mail1 = Mail.builder()
            .mailId("m1")
            .from(CHARACTER_ID)
            .to(FRIEND_ID)
            .sendTime(NOW)
            .subject("")
            .message("")
            .build();

        Mail mail2 = Mail.builder()
            .mailId("m2")
            .from(FRIEND_ID)
            .to(CHARACTER_ID)
            .sendTime(NOW)
            .subject("")
            .message("")
            .build();

        mailDao.save(mail1);
        mailDao.save(mail2);
    }

    private void saveFriendships() {
        Friendship friendship1 = Friendship.builder()
            .friendshipId("f1")
            .characterId(CHARACTER_ID)
            .friendId(FRIEND_ID)
            .build();

        Friendship friendship2 = Friendship.builder()
            .friendshipId("f2")
            .characterId(FRIEND_ID)
            .friendId(CHARACTER_ID)
            .build();

        friendshipDao.save(friendship1);
        friendshipDao.save(friendship2);
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
        SkyXpCredentials credentials = SkyXpCredentials.builder()
            .userName("")
            .userId(USER_ID)
            .password(PASSWORD)
            .build();
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
    @EnableJpaRepositories(basePackages = "com.github.saphyra.skyxplore")
    @EnableTransactionManagement
    @EntityScan(basePackages = "com.github.saphyra.skyxplore")
    @Import(DataSourceConfiguration.class)
    @ComponentScan(basePackageClasses = {
        UserDao.class,
        CredentialsDao.class,
        AccessTokenDao.class,
        CharacterDao.class,
        BlockedCharacterDao.class,
        FriendRequestDao.class,
        FriendshipDao.class,
        MailDao.class,
        EquippedShipDao.class,
        SlotDao.class
    })
    @EnableEncryption
    @Profile("int-test")
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
            SkyXpCredentials credentials = SkyXpCredentials.builder()
                .userId(USER_ID)
                .userName("")
                .password(PASSWORD)
                .build();
            given(credentialsService.findByUserId(USER_ID)).willReturn(credentials);
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