package com.github.saphyra.skyxplore.character;

import com.github.saphyra.skyxplore.character.cache.CharacterNameLikeCache;
import com.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.character.repository.CharacterDao;
import com.github.saphyra.skyxplore.common.exception.CharacterNotFoundException;
import com.github.saphyra.skyxplore.common.exception.InvalidAccessException;
import com.github.saphyra.skyxplore.community.blockedcharacter.domain.BlockedCharacter;
import com.github.saphyra.skyxplore.community.blockedcharacter.repository.BlockedCharacterDao;
import com.github.saphyra.skyxplore.community.friendship.FriendshipQueryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CharacterQueryServiceTest {
    private static final String CHARACTER_ID_1 = "character_id_1";
    private static final String USER_ID = "user_id";
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

    @InjectMocks
    private CharacterQueryService underTest;

    @Test(expected = CharacterNotFoundException.class)
    public void testFindByCharacterIdShouldThrowExceptionWhenNotFound() {
        //GIVEN
        when(characterDao.findById(CHARACTER_ID_1)).thenReturn(Optional.empty());
        //WHEN
        underTest.findByCharacterId(CHARACTER_ID_1);
    }

    @Test
    public void testFindByCharacterIdShouldReturnCharacter() {
        //GIVEN
        SkyXpCharacter character = createCharacter(CHARACTER_ID_1);
        when(characterDao.findById(CHARACTER_ID_1)).thenReturn(Optional.of(character));
        //WHEN
        SkyXpCharacter result = underTest.findByCharacterId(CHARACTER_ID_1);
        //THEN
        assertThat(result).isEqualTo(character);
        verify(characterDao).findById(CHARACTER_ID_1);
    }

    @Test(expected = CharacterNotFoundException.class)
    public void testFindCharacterByIdAuthorizesShouldThrowExceptionWhenNotFound() {
        //GIVEN
        when(characterDao.findById(CHARACTER_ID_1)).thenReturn(Optional.empty());
        //WHEN
        underTest.findCharacterByIdAuthorized(CHARACTER_ID_1, USER_ID);
    }

    @Test(expected = InvalidAccessException.class)
    public void testFindCharacterByIdAuthorizesShouldThrowExceptionWhenWrongUserId() {
        //GIVEN
        SkyXpCharacter character = createCharacter(CHARACTER_ID_1);
        when(characterDao.findById(CHARACTER_ID_1)).thenReturn(Optional.of(character));
        //WHEN
        underTest.findCharacterByIdAuthorized(CHARACTER_ID_1, FAKE_USER_ID);
    }

    @Test
    public void testFindCharacterByIdAuthorizedShouldReturn() {
        //GIVEN
        SkyXpCharacter character = createCharacter(CHARACTER_ID_1);
        when(characterDao.findById(CHARACTER_ID_1)).thenReturn(Optional.of(character));
        //WHEN
        SkyXpCharacter result = underTest.findCharacterByIdAuthorized(CHARACTER_ID_1, USER_ID);
        //THEN
        assertThat(result).isEqualTo(character);
        verify(characterDao).findById(CHARACTER_ID_1);
    }

    @Test
    public void testGetCharactersCanBeAddresseeShouldReturnFilteredMatchingCharacters() {
        //GIVEN
        SkyXpCharacter character = createCharacter(CHARACTER_ID_1);
        when(characterDao.findById(CHARACTER_ID_1)).thenReturn(Optional.of(character));

        SkyXpCharacter ownCharacter = createCharacter(CHARACTER_ID_5);

        SkyXpCharacter characterBlocked1 = createCharacter(CHARACTER_ID_2, null);

        SkyXpCharacter characterBlocked2 = createCharacter(CHARACTER_ID_3, null);

        SkyXpCharacter canBeAddressee = createCharacter(CHARACTER_ID_4, null);

        when(characterNameLikeCache.get(CHARACTER_NAME)).thenReturn(Optional.of(Arrays.asList(ownCharacter, characterBlocked1, characterBlocked2, canBeAddressee)));

        BlockedCharacter ownBlocked1 = new BlockedCharacter();
        ownBlocked1.setCharacterId(CHARACTER_ID_1);
        ownBlocked1.setBlockedCharacterId(CHARACTER_ID_2);
        when(blockedCharacterDao.getBlockedCharacters(CHARACTER_ID_1)).thenReturn(Arrays.asList(ownBlocked1));

        BlockedCharacter blocked2BlocksCharacter = new BlockedCharacter();
        blocked2BlocksCharacter.setCharacterId(CHARACTER_ID_3);
        blocked2BlocksCharacter.setBlockedCharacterId(CHARACTER_ID_1);
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
        SkyXpCharacter character = createCharacter(CHARACTER_ID_1);
        when(characterDao.findById(CHARACTER_ID_1)).thenReturn(Optional.of(character));

        SkyXpCharacter ownCharacter = createCharacter(CHARACTER_ID_5);

        SkyXpCharacter characterBlocked1 = createCharacter(CHARACTER_ID_2, null);

        SkyXpCharacter characterBlocked2 = createCharacter(CHARACTER_ID_3, null);

        SkyXpCharacter canBeAddressee = createCharacter(CHARACTER_ID_4, null);

        when(characterNameLikeCache.get(CHARACTER_NAME)).thenReturn(Optional.of(Arrays.asList(ownCharacter, characterBlocked1, characterBlocked2, canBeAddressee)));

        BlockedCharacter ownBlocked1 = new BlockedCharacter();
        ownBlocked1.setCharacterId(CHARACTER_ID_1);
        ownBlocked1.setBlockedCharacterId(CHARACTER_ID_2);
        when(blockedCharacterDao.getBlockedCharacters(CHARACTER_ID_1)).thenReturn(Arrays.asList(ownBlocked1));

        BlockedCharacter blocked2BlocksCharacter = new BlockedCharacter();
        blocked2BlocksCharacter.setCharacterId(CHARACTER_ID_3);
        blocked2BlocksCharacter.setBlockedCharacterId(CHARACTER_ID_1);
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
        SkyXpCharacter character = createCharacter(CHARACTER_ID_1);
        when(characterDao.findById(CHARACTER_ID_1)).thenReturn(Optional.of(character));

        SkyXpCharacter ownCharacter = createCharacter(CHARACTER_ID_5);

        SkyXpCharacter characterBlocked1 = createCharacter(CHARACTER_ID_2, null);

        SkyXpCharacter characterBlocked2 = createCharacter(CHARACTER_ID_3, null);

        SkyXpCharacter canBeAddressee = createCharacter(CHARACTER_ID_4, null);

        SkyXpCharacter friendCharacter = createCharacter(CHARACTER_ID_6, null);

        SkyXpCharacter friendRequestCharacter = createCharacter(CHARACTER_ID_7, null);

        when(characterNameLikeCache.get(CHARACTER_NAME)).thenReturn(Optional.of(Arrays.asList(ownCharacter, characterBlocked1, characterBlocked2, canBeAddressee, friendCharacter, friendRequestCharacter)));

        BlockedCharacter ownBlocked1 = new BlockedCharacter();
        ownBlocked1.setCharacterId(CHARACTER_ID_1);
        ownBlocked1.setBlockedCharacterId(CHARACTER_ID_2);
        when(blockedCharacterDao.getBlockedCharacters(CHARACTER_ID_1)).thenReturn(Arrays.asList(ownBlocked1));

        BlockedCharacter blocked2BlocksCharacter = new BlockedCharacter();
        blocked2BlocksCharacter.setCharacterId(CHARACTER_ID_3);
        blocked2BlocksCharacter.setBlockedCharacterId(CHARACTER_ID_1);
        when(blockedCharacterDao.getBlockedCharacters(CHARACTER_ID_3)).thenReturn(Arrays.asList(blocked2BlocksCharacter));

        when(friendshipQueryService.isFriendshipAlreadyExists(CHARACTER_ID_6, CHARACTER_ID_1)).thenReturn(true);
        when(friendshipQueryService.isFriendRequestAlreadyExists(CHARACTER_ID_7, CHARACTER_ID_1)).thenReturn(true);
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
        SkyXpCharacter character = createCharacter(CHARACTER_ID_1);
        when(characterDao.getByUserId(USER_ID)).thenReturn(Arrays.asList(character));
        //WHEN
        List<SkyXpCharacter> result = underTest.getCharactersByUserId(USER_ID);
        //THEN
        assertThat(result).containsOnly(character);
        verify(characterDao).getByUserId(USER_ID);
    }

    @Test
    public void testGetEquipmentsOfCharacterShouldReturn() {
        //GIVEN
        SkyXpCharacter character = createCharacter(CHARACTER_ID_1);
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
        SkyXpCharacter character = createCharacter(CHARACTER_ID_1);
        when(characterDao.findById(CHARACTER_ID_1)).thenReturn(Optional.of(character));
        //WHEN
        Integer result = underTest.getMoneyOfCharacter(CHARACTER_ID_1);
        //THEN
        assertThat(result).isEqualTo(MONEY);
    }

    @Test
    public void testIsCharNameExists_optionalIsPresent() {
        //GIVEN
        SkyXpCharacter character = createCharacter(CHARACTER_ID_1);
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

    private SkyXpCharacter createCharacter(String characterId) {
        return createCharacter(characterId, USER_ID);
    }

    private SkyXpCharacter createCharacter(String characterId, String userId) {
        return SkyXpCharacter.builder()
            .characterId(characterId)
            .equipments(EQUIPMENTS)
            .userId(userId)
            .money(MONEY)
            .build();
    }
}