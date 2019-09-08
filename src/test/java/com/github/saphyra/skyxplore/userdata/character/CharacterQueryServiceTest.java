package com.github.saphyra.skyxplore.userdata.character;

import com.github.saphyra.exceptionhandling.exception.ForbiddenException;
import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.skyxplore.common.ErrorCode;
import com.github.saphyra.skyxplore.common.domain.FixedSizeConcurrentList;
import com.github.saphyra.skyxplore.game.lobby.lobby.LobbyContext;
import com.github.saphyra.skyxplore.game.lobby.lobby.LobbyQueryService;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.GameMode;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import com.github.saphyra.skyxplore.platform.auth.domain.SkyXpAccessToken;
import com.github.saphyra.skyxplore.platform.auth.repository.AccessTokenDao;
import com.github.saphyra.skyxplore.userdata.character.cache.CharacterNameLikeCache;
import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.userdata.character.repository.CharacterDao;
import com.github.saphyra.skyxplore.userdata.community.blockedcharacter.domain.BlockedCharacter;
import com.github.saphyra.skyxplore.userdata.community.blockedcharacter.repository.BlockedCharacterDao;
import com.github.saphyra.skyxplore.userdata.community.friendship.service.FriendRequestQueryService;
import com.github.saphyra.skyxplore.userdata.community.friendship.service.FriendshipQueryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.github.saphyra.testing.ExceptionValidator.verifyException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CharacterQueryServiceTest {
    private static final String CHARACTER_ID_1 = "character_id_1";
    private static final String USER_ID_1 = "user_id_1";
    private static final String USER_ID_2 = "user_id_2";
    private static final String FAKE_USER_ID = "fake_user_id";
    private static final String CHARACTER_ID_2 = "character_id_2";
    private static final String CHARACTER_ID_3 = "character_id_3";
    private static final String CHARACTER_ID_5 = "character_id_5";
    private static final String CHARACTER_ID_4 = "character_id_4";
    private static final String CHARACTER_NAME = "character_name";
    private static final String CHARACTER_ID_6 = "character_id_6";
    private static final String CHARACTER_ID_7 = "character_id_7";
    private static final Integer MONEY = 5;
    private static final String EQUIPMENT = "equipment";
    private static final ArrayList<String> EQUIPMENTS;

    static {
        EQUIPMENTS = new ArrayList<>();
        EQUIPMENTS.add(EQUIPMENT);
    }

    @Mock
    private BlockedCharacterDao blockedCharacterDao;

    @Mock
    private CharacterNameLikeCache characterNameLikeCache;

    @Mock
    private CharacterDao characterDao;

    @Mock
    private FriendshipQueryService friendshipQueryService;

    @Mock
    private FriendRequestQueryService friendRequestQueryService;

    @Mock
    private AccessTokenDao accessTokenDao;

    @Mock
    private LobbyQueryService lobbyQueryService;

    @Mock
    private SkyXpAccessToken accessToken;

    @InjectMocks
    private CharacterQueryService underTest;

    @Test
    public void testFindByCharacterIdShouldThrowExceptionWhenNotFound() {
        //GIVEN
        when(characterDao.findById(CHARACTER_ID_1)).thenReturn(Optional.empty());
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.findByCharacterIdValidated(CHARACTER_ID_1));
        //THEN
        verifyException(ex, NotFoundException.class, ErrorCode.CHARACTER_NOT_FOUND);
    }

    @Test
    public void testFindByCharacterIdShouldReturnCharacter() {
        //GIVEN
        SkyXpCharacter character = createCharacter(CHARACTER_ID_1, USER_ID_1);
        when(characterDao.findById(CHARACTER_ID_1)).thenReturn(Optional.of(character));
        //WHEN
        SkyXpCharacter result = underTest.findByCharacterIdValidated(CHARACTER_ID_1);
        //THEN
        assertThat(result).isEqualTo(character);
        verify(characterDao).findById(CHARACTER_ID_1);
    }

    @Test
    public void testFindCharacterByIdAuthorizesShouldThrowExceptionWhenNotFound() {
        //GIVEN
        when(characterDao.findById(CHARACTER_ID_1)).thenReturn(Optional.empty());
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.findCharacterByIdAuthorized(CHARACTER_ID_1, USER_ID_1));
        //THEN
        verifyException(ex, NotFoundException.class, ErrorCode.CHARACTER_NOT_FOUND);
    }

    @Test
    public void testFindCharacterByIdAuthorizesShouldThrowExceptionWhenWrongUserId() {
        //GIVEN
        SkyXpCharacter character = createCharacter(CHARACTER_ID_1, USER_ID_1);
        when(characterDao.findById(CHARACTER_ID_1)).thenReturn(Optional.of(character));
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.findCharacterByIdAuthorized(CHARACTER_ID_1, FAKE_USER_ID));
        //THEN
        verifyException(ex, ForbiddenException.class, ErrorCode.INVALID_CHARACTER_ACCESS);
    }

    @Test
    public void testFindCharacterByIdAuthorizedShouldReturn() {
        //GIVEN
        SkyXpCharacter character = createCharacter(CHARACTER_ID_1, USER_ID_1);
        when(characterDao.findById(CHARACTER_ID_1)).thenReturn(Optional.of(character));
        //WHEN
        SkyXpCharacter result = underTest.findCharacterByIdAuthorized(CHARACTER_ID_1, USER_ID_1);
        //THEN
        assertThat(result).isEqualTo(character);
        verify(characterDao).findById(CHARACTER_ID_1);
    }

    @Test
    public void getActiveCharactersByName() {
        //GIVEN
        SkyXpCharacter ownCharacter = createCharacter(CHARACTER_ID_1, USER_ID_1);
        when(characterDao.findById(CHARACTER_ID_1)).thenReturn(Optional.of(ownCharacter));

        SkyXpCharacter characterBlocked1 = createCharacter(CHARACTER_ID_2, USER_ID_2);
        BlockedCharacter ownBlocked1 = BlockedCharacter.builder()
            .characterId(CHARACTER_ID_1)
            .blockedCharacterId(CHARACTER_ID_2)
            .build();
        when(blockedCharacterDao.getBlockedCharacters(CHARACTER_ID_1)).thenReturn(Arrays.asList(ownBlocked1));

        SkyXpCharacter characterBlocked2 = createCharacter(CHARACTER_ID_3, USER_ID_2);
        BlockedCharacter blocked2BlocksCharacter = BlockedCharacter.builder()
            .characterId(CHARACTER_ID_3)
            .blockedCharacterId(CHARACTER_ID_1)
            .build();
        when(blockedCharacterDao.getBlockedCharacters(CHARACTER_ID_3)).thenReturn(Arrays.asList(blocked2BlocksCharacter));

        SkyXpCharacter notActive = createCharacter(CHARACTER_ID_4, USER_ID_2);
        given(accessTokenDao.findByCharacterId(CHARACTER_ID_4)).willReturn(Optional.empty());

        SkyXpCharacter alreadyInLobby = createCharacter(CHARACTER_ID_5, USER_ID_2);
        given(accessTokenDao.findByCharacterId(CHARACTER_ID_5)).willReturn(Optional.of(accessToken));
        given(lobbyQueryService.findByCharacterId(CHARACTER_ID_5)).willReturn(Optional.of(Lobby.builder()
            .lobbyId(UUID.randomUUID())
            .gameMode(GameMode.ARCADE)
            .ownerId("")
            .lobbyContext(new LobbyContext(null, null))
            .members(new FixedSizeConcurrentList<>(0))
            .build()));

        SkyXpCharacter canBeInvited = createCharacter(CHARACTER_ID_6, USER_ID_2);
        given(accessTokenDao.findByCharacterId(CHARACTER_ID_6)).willReturn(Optional.of(accessToken));
        given(lobbyQueryService.findByCharacterId(CHARACTER_ID_6)).willReturn(Optional.empty());

        given(characterNameLikeCache.get(CHARACTER_NAME)).willReturn(Optional.of(Arrays.asList(
            ownCharacter, characterBlocked1, characterBlocked2, notActive, alreadyInLobby, canBeInvited
        )));
        //WHEN
        List<SkyXpCharacter> result = underTest.getActiveCharactersByName(CHARACTER_ID_1, CHARACTER_NAME);
        //THEN
        assertThat(result).containsExactly(canBeInvited);
    }

    @Test
    public void testGetCharactersCanBeAddresseeShouldReturnFilteredMatchingCharacters() {
        //GIVEN
        SkyXpCharacter character = createCharacter(CHARACTER_ID_1, USER_ID_1);
        when(characterDao.findById(CHARACTER_ID_1)).thenReturn(Optional.of(character));

        SkyXpCharacter ownCharacter = createCharacter(CHARACTER_ID_5, USER_ID_1);

        SkyXpCharacter characterBlocked1 = createCharacter(CHARACTER_ID_2, USER_ID_2);

        SkyXpCharacter characterBlocked2 = createCharacter(CHARACTER_ID_3, USER_ID_2);

        SkyXpCharacter canBeAddressee = createCharacter(CHARACTER_ID_4, USER_ID_2);

        when(characterNameLikeCache.get(CHARACTER_NAME)).thenReturn(Optional.of(Arrays.asList(ownCharacter, characterBlocked1, characterBlocked2, canBeAddressee)));

        BlockedCharacter ownBlocked1 = BlockedCharacter.builder()
            .characterId(CHARACTER_ID_1)
            .blockedCharacterId(CHARACTER_ID_2)
            .build();
        when(blockedCharacterDao.getBlockedCharacters(CHARACTER_ID_1)).thenReturn(Arrays.asList(ownBlocked1));

        BlockedCharacter blocked2BlocksCharacter = BlockedCharacter.builder()
            .characterId(CHARACTER_ID_3)
            .blockedCharacterId(CHARACTER_ID_1)
            .build();
        when(blockedCharacterDao.getBlockedCharacters(CHARACTER_ID_3)).thenReturn(Arrays.asList(blocked2BlocksCharacter));
        //WHEN
        List<SkyXpCharacter> result = underTest.getCharactersCanBeAddressee(CHARACTER_ID_1, CHARACTER_NAME);
        //THEN
        assertThat(result).containsOnly(canBeAddressee);
        verify(characterDao).findById(CHARACTER_ID_1);
        verify(characterNameLikeCache).get(CHARACTER_NAME);
    }

    @Test
    public void testGetCharactersCanBeBlockedShouldReturnFilteredMatchingCharacters() {
        //GIVEN
        SkyXpCharacter character = createCharacter(CHARACTER_ID_1, USER_ID_1);
        when(characterDao.findById(CHARACTER_ID_1)).thenReturn(Optional.of(character));

        SkyXpCharacter ownCharacter = createCharacter(CHARACTER_ID_5, USER_ID_1);

        SkyXpCharacter characterBlocked1 = createCharacter(CHARACTER_ID_2, USER_ID_2);

        SkyXpCharacter characterBlocked2 = createCharacter(CHARACTER_ID_3, USER_ID_2);

        SkyXpCharacter canBeAddressee = createCharacter(CHARACTER_ID_4, USER_ID_2);

        when(characterNameLikeCache.get(CHARACTER_NAME)).thenReturn(Optional.of(Arrays.asList(ownCharacter, characterBlocked1, characterBlocked2, canBeAddressee)));

        BlockedCharacter ownBlocked1 = BlockedCharacter.builder()
            .characterId(CHARACTER_ID_1)
            .blockedCharacterId(CHARACTER_ID_2)
            .build();
        when(blockedCharacterDao.getBlockedCharacters(CHARACTER_ID_1)).thenReturn(Arrays.asList(ownBlocked1));

        BlockedCharacter blocked2BlocksCharacter = BlockedCharacter.builder()
            .characterId(CHARACTER_ID_3)
            .blockedCharacterId(CHARACTER_ID_1)
            .build();
        when(blockedCharacterDao.getBlockedCharacters(CHARACTER_ID_3)).thenReturn(Arrays.asList(blocked2BlocksCharacter));
        //WHEN
        List<SkyXpCharacter> result = underTest.getCharactersCanBeBlocked(CHARACTER_NAME, CHARACTER_ID_1);
        //THEN
        assertThat(result).containsOnly(canBeAddressee);
        verify(characterDao).findById(CHARACTER_ID_1);
        verify(characterNameLikeCache).get(CHARACTER_NAME);
    }

    @Test
    public void testGetCharactersCanBeFriendShouldReturnFilteredMatchingCharacters() {
        //GIVEN
        SkyXpCharacter character = createCharacter(CHARACTER_ID_1, USER_ID_1);
        when(characterDao.findById(CHARACTER_ID_1)).thenReturn(Optional.of(character));

        SkyXpCharacter ownCharacter = createCharacter(CHARACTER_ID_5, USER_ID_1);

        SkyXpCharacter characterBlocked1 = createCharacter(CHARACTER_ID_2, USER_ID_2);

        SkyXpCharacter characterBlocked2 = createCharacter(CHARACTER_ID_3, USER_ID_2);

        SkyXpCharacter canBeAddressee = createCharacter(CHARACTER_ID_4, USER_ID_2);

        SkyXpCharacter friendCharacter = createCharacter(CHARACTER_ID_6, USER_ID_2);

        SkyXpCharacter friendRequestCharacter = createCharacter(CHARACTER_ID_7, USER_ID_2);

        when(characterNameLikeCache.get(CHARACTER_NAME)).thenReturn(Optional.of(Arrays.asList(ownCharacter, characterBlocked1, characterBlocked2, canBeAddressee, friendCharacter, friendRequestCharacter)));

        BlockedCharacter ownBlocked1 = BlockedCharacter.builder()
            .characterId(CHARACTER_ID_1)
            .blockedCharacterId(CHARACTER_ID_2)
            .build();
        when(blockedCharacterDao.getBlockedCharacters(CHARACTER_ID_1)).thenReturn(Arrays.asList(ownBlocked1));

        BlockedCharacter blocked2BlocksCharacter = BlockedCharacter.builder()
            .characterId(CHARACTER_ID_3)
            .blockedCharacterId(CHARACTER_ID_1)
            .build();
        when(blockedCharacterDao.getBlockedCharacters(CHARACTER_ID_3)).thenReturn(Arrays.asList(blocked2BlocksCharacter));

        when(friendshipQueryService.isFriendshipAlreadyExists(CHARACTER_ID_6, CHARACTER_ID_1)).thenReturn(true);
        when(friendRequestQueryService.isFriendRequestAlreadyExists(CHARACTER_ID_7, CHARACTER_ID_1)).thenReturn(true);
        //WHEN
        List<SkyXpCharacter> result = underTest.getCharactersCanBeFriend(CHARACTER_NAME, CHARACTER_ID_1);
        //THEN
        assertThat(result).containsOnly(canBeAddressee);
        verify(characterDao).findById(CHARACTER_ID_1);
        verify(characterNameLikeCache).get(CHARACTER_NAME);
    }

    @Test
    public void testGetCharactersByUserIdShouldCallDaoAndReturn() {
        //GIVEN
        SkyXpCharacter character = createCharacter(CHARACTER_ID_1, USER_ID_1);
        when(characterDao.getByUserId(USER_ID_1)).thenReturn(Arrays.asList(character));
        //WHEN
        List<SkyXpCharacter> result = underTest.getCharactersByUserId(USER_ID_1);
        //THEN
        assertThat(result).containsOnly(character);
        verify(characterDao).getByUserId(USER_ID_1);
    }

    @Test
    public void testGetEquipmentsOfCharacterShouldReturn() {
        //GIVEN
        SkyXpCharacter character = createCharacter(CHARACTER_ID_1, USER_ID_1);
        when(characterDao.findById(CHARACTER_ID_1)).thenReturn(Optional.of(character));

        //WHEN
        List<String> result = underTest.getEquipmentsOfCharacter(CHARACTER_ID_1);
        //THEN
        verify(characterDao).findById(CHARACTER_ID_1);
        assertThat(result).isEqualTo(character.getEquipments());
    }

    @Test
    public void testGetMoneyOfCharacter() {
        //GIVEN
        SkyXpCharacter character = createCharacter(CHARACTER_ID_1, USER_ID_1);
        when(characterDao.findById(CHARACTER_ID_1)).thenReturn(Optional.of(character));
        //WHEN
        Integer result = underTest.getMoneyOfCharacter(CHARACTER_ID_1);
        //THEN
        assertThat(result).isEqualTo(MONEY);
    }

    @Test
    public void testIsCharNameExists_optionalIsPresent() {
        //GIVEN
        SkyXpCharacter character = createCharacter(CHARACTER_ID_1, USER_ID_1);
        when(characterDao.findByCharacterName(CHARACTER_NAME)).thenReturn(Optional.of(character));
        //WHEN
        boolean result = underTest.isCharNameExists(CHARACTER_NAME);
        //THEN
        assertThat(result).isTrue();
    }

    @Test
    public void testIsCharNameExists_optionalIsEmpty() {
        //GIVEN
        when(characterDao.findByCharacterName(CHARACTER_NAME)).thenReturn(Optional.empty());
        //WHEN
        boolean result = underTest.isCharNameExists(CHARACTER_NAME);
        //THEN
        assertThat(result).isFalse();
    }


    private SkyXpCharacter createCharacter(String characterId, String userId) {
        return SkyXpCharacter.builder()
            .characterId(characterId)
            .equipments(EQUIPMENTS)
            .userId(userId)
            .money(MONEY)
            .characterName(CHARACTER_NAME)
            .build();
    }
}