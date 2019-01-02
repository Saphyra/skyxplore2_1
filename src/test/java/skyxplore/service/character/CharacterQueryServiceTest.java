package skyxplore.service.character;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.cache.CharacterNameLikeCache;
import skyxplore.controller.view.View;
import skyxplore.controller.view.equipment.EquipmentViewList;
import skyxplore.dataaccess.db.CharacterDao;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.domain.community.blockedcharacter.BlockedCharacter;
import skyxplore.exception.CharacterNotFoundException;
import skyxplore.exception.InvalidAccessException;
import skyxplore.service.GameDataFacade;
import skyxplore.service.community.BlockedCharacterQueryService;
import skyxplore.service.community.FriendshipQueryService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_2;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_3;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_4;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_5;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_6;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_7;
import static skyxplore.testutil.TestUtils.CHARACTER_MONEY;
import static skyxplore.testutil.TestUtils.CHARACTER_NAME;
import static skyxplore.testutil.TestUtils.USER_FAKE_ID;
import static skyxplore.testutil.TestUtils.USER_ID;
import static skyxplore.testutil.TestUtils.createBlockedCharacter;
import static skyxplore.testutil.TestUtils.createCharacter;
import static skyxplore.testutil.TestUtils.createGeneralDescriptionMap;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
@RunWith(MockitoJUnitRunner.class)
public class CharacterQueryServiceTest {
    @Mock
    private  BlockedCharacterQueryService blockedCharacterQueryService;

    @Mock
    private  CharacterNameLikeCache characterNameLikeCache;

    @Mock
    private  CharacterDao characterDao;

    @Mock
    private  GameDataFacade gameDataFacade;

    @Mock
    private  FriendshipQueryService friendshipQueryService;

    @InjectMocks
    private CharacterQueryService underTest;

    @Test(expected = CharacterNotFoundException.class)
    public void testFindByCharacterIdShouldThrowExceptionWhenNotFound(){
        //GIVEN
        when(characterDao.findById(CHARACTER_ID_1)).thenReturn(Optional.empty());
        //WHEN
        underTest.findByCharacterId(CHARACTER_ID_1);
    }

    @Test
    public void testFindByCharacterIdShouldReturnCharacter(){
        //GIVEN
        SkyXpCharacter character = createCharacter();
        when(characterDao.findById(CHARACTER_ID_1)).thenReturn(Optional.of(character));
        //WHEN
        SkyXpCharacter result = underTest.findByCharacterId(CHARACTER_ID_1);
        //THEN
        assertEquals(character, result);
        verify(characterDao).findById(CHARACTER_ID_1);
    }

    @Test(expected = CharacterNotFoundException.class)
    public void testFindCharacterByIdAuthorizesShouldThrowExceptionWhenNotFound(){
        //GIVEN
        when(characterDao.findById(CHARACTER_ID_1)).thenReturn(Optional.empty());
        //WHEN
        underTest.findCharacterByIdAuthorized(CHARACTER_ID_1, USER_ID);
    }

    @Test(expected = InvalidAccessException.class)
    public void testFindCharacterByIdAuthorizesShouldThrowExceptionWhenWrongUserId(){
        //GIVEN
        SkyXpCharacter character = createCharacter();
        when(characterDao.findById(CHARACTER_ID_1)).thenReturn(Optional.of(character));
        //WHEN
        underTest.findCharacterByIdAuthorized(CHARACTER_ID_1, USER_FAKE_ID);
    }

    @Test
    public void testFindCharacterByIdAuthorizedShouldReturn(){
        //GIVEN
        SkyXpCharacter character = createCharacter();
        when(characterDao.findById(CHARACTER_ID_1)).thenReturn(Optional.of(character));
        //WHEN
        SkyXpCharacter result = underTest.findCharacterByIdAuthorized(CHARACTER_ID_1, USER_ID);
        //THEN
        assertEquals(character, result);
        verify(characterDao).findById(CHARACTER_ID_1);
    }

    @Test
    public void testGetBlockedCharactersShouldReturnList(){
        //GIVEN
        BlockedCharacter blockedCharacter1 = createBlockedCharacter();
        blockedCharacter1.setBlockedCharacterId(CHARACTER_ID_2);

        BlockedCharacter blockedCharacter2 = createBlockedCharacter();
        blockedCharacter2.setBlockedCharacterId(CHARACTER_ID_3);

        when(blockedCharacterQueryService.getBlockedCharactersOf(CHARACTER_ID_1)).thenReturn(Arrays.asList(blockedCharacter1, blockedCharacter2));

        SkyXpCharacter character1 = createCharacter();
        character1.setCharacterId(CHARACTER_ID_2);

        SkyXpCharacter character2 = createCharacter();
        character2.setCharacterId(CHARACTER_ID_3);
        when(characterDao.findById(CHARACTER_ID_2)).thenReturn(Optional.of(character1));
        when(characterDao.findById(CHARACTER_ID_3)).thenReturn(Optional.of(character2));
        //WHEN
        List<SkyXpCharacter> result = underTest.getBlockedCharacters(CHARACTER_ID_1);
        //THEN
        assertTrue(result.contains(character1));
        assertTrue(result.contains(character2));
        verify(blockedCharacterQueryService).getBlockedCharactersOf(CHARACTER_ID_1);
        verify(characterDao).findById(CHARACTER_ID_2);
        verify(characterDao).findById(CHARACTER_ID_3);
    }

    @Test
    public void testGetCharactersCanBeAddresseeShouldReturnFilteredMatchingCharacters() throws ExecutionException {
        //GIVEN
        SkyXpCharacter character = createCharacter();
        when(characterDao.findById(CHARACTER_ID_1)).thenReturn(Optional.of(character));

        SkyXpCharacter ownCharacter = createCharacter();
        ownCharacter.setCharacterId(CHARACTER_ID_5);

        SkyXpCharacter characterBlocked1 = createCharacter();
        characterBlocked1.setCharacterId(CHARACTER_ID_2);
        characterBlocked1.setUserId(null);

        SkyXpCharacter characterBlocked2 = createCharacter();
        characterBlocked2.setCharacterId(CHARACTER_ID_3);
        characterBlocked2.setUserId(null);

        SkyXpCharacter canBeAddressee = createCharacter();
        canBeAddressee.setCharacterId(CHARACTER_ID_4);
        canBeAddressee.setUserId(null);

        when(characterNameLikeCache.get(CHARACTER_NAME)).thenReturn(Arrays.asList(ownCharacter, characterBlocked1, characterBlocked2, canBeAddressee));

        BlockedCharacter ownBlocked1 = new BlockedCharacter();
        ownBlocked1.setCharacterId(CHARACTER_ID_1);
        ownBlocked1.setBlockedCharacterId(CHARACTER_ID_2);
        when(blockedCharacterQueryService.getBlockedCharactersOf(CHARACTER_ID_1)).thenReturn(Arrays.asList(ownBlocked1));

        BlockedCharacter blocked2BlocksCharacter = new BlockedCharacter();
        blocked2BlocksCharacter.setCharacterId(CHARACTER_ID_3);
        blocked2BlocksCharacter.setBlockedCharacterId(CHARACTER_ID_1);
        when(blockedCharacterQueryService.getBlockedCharactersOf(CHARACTER_ID_3)).thenReturn(Arrays.asList(blocked2BlocksCharacter));
        //WHEN
        List<SkyXpCharacter> result = underTest.getCharactersCanBeAddressee(CHARACTER_NAME, CHARACTER_ID_1);
        //THEN
        assertEquals(1, result.size());
        assertTrue(result.contains(canBeAddressee));
        verify(characterDao).findById(CHARACTER_ID_1);
        verify(characterNameLikeCache).get(CHARACTER_NAME);
    }

    @Test
    public void testGetCharactersCanBeBlockedShouldReturnFilteredMatchingCharacters() throws ExecutionException {
        //GIVEN
        SkyXpCharacter character = createCharacter();
        when(characterDao.findById(CHARACTER_ID_1)).thenReturn(Optional.of(character));

        SkyXpCharacter ownCharacter = createCharacter();
        ownCharacter.setCharacterId(CHARACTER_ID_5);

        SkyXpCharacter characterBlocked1 = createCharacter();
        characterBlocked1.setCharacterId(CHARACTER_ID_2);
        characterBlocked1.setUserId(null);

        SkyXpCharacter characterBlocked2 = createCharacter();
        characterBlocked2.setCharacterId(CHARACTER_ID_3);
        characterBlocked2.setUserId(null);

        SkyXpCharacter canBeAddressee = createCharacter();
        canBeAddressee.setCharacterId(CHARACTER_ID_4);
        canBeAddressee.setUserId(null);

        when(characterNameLikeCache.get(CHARACTER_NAME)).thenReturn(Arrays.asList(ownCharacter, characterBlocked1, characterBlocked2, canBeAddressee));

        BlockedCharacter ownBlocked1 = new BlockedCharacter();
        ownBlocked1.setCharacterId(CHARACTER_ID_1);
        ownBlocked1.setBlockedCharacterId(CHARACTER_ID_2);
        when(blockedCharacterQueryService.getBlockedCharactersOf(CHARACTER_ID_1)).thenReturn(Arrays.asList(ownBlocked1));

        BlockedCharacter blocked2BlocksCharacter = new BlockedCharacter();
        blocked2BlocksCharacter.setCharacterId(CHARACTER_ID_3);
        blocked2BlocksCharacter.setBlockedCharacterId(CHARACTER_ID_1);
        when(blockedCharacterQueryService.getBlockedCharactersOf(CHARACTER_ID_3)).thenReturn(Arrays.asList(blocked2BlocksCharacter));
        //WHEN
        List<SkyXpCharacter> result = underTest.getCharactersCanBeBlocked(CHARACTER_NAME, CHARACTER_ID_1);
        //THEN
        assertEquals(1, result.size());
        assertTrue(result.contains(canBeAddressee));
        verify(characterDao).findById(CHARACTER_ID_1);
        verify(characterNameLikeCache).get(CHARACTER_NAME);
    }

    @Test
    public void testGetCharactersCanBeFriendShouldReturnFilteredMatchingCharacters() throws ExecutionException {
        //GIVEN
        SkyXpCharacter character = createCharacter();
        when(characterDao.findById(CHARACTER_ID_1)).thenReturn(Optional.of(character));

        SkyXpCharacter ownCharacter = createCharacter();
        ownCharacter.setCharacterId(CHARACTER_ID_5);

        SkyXpCharacter characterBlocked1 = createCharacter();
        characterBlocked1.setCharacterId(CHARACTER_ID_2);
        characterBlocked1.setUserId(null);

        SkyXpCharacter characterBlocked2 = createCharacter();
        characterBlocked2.setCharacterId(CHARACTER_ID_3);
        characterBlocked2.setUserId(null);

        SkyXpCharacter canBeAddressee = createCharacter();
        canBeAddressee.setCharacterId(CHARACTER_ID_4);
        canBeAddressee.setUserId(null);

        SkyXpCharacter friendCharacter = createCharacter();
        friendCharacter.setCharacterId(CHARACTER_ID_6);
        friendCharacter.setUserId(null);

        SkyXpCharacter friendRequestCharacter = createCharacter();
        friendRequestCharacter.setCharacterId(CHARACTER_ID_7);
        friendRequestCharacter.setUserId(null);

        when(characterNameLikeCache.get(CHARACTER_NAME)).thenReturn(Arrays.asList(ownCharacter, characterBlocked1, characterBlocked2, canBeAddressee, friendCharacter, friendRequestCharacter));

        BlockedCharacter ownBlocked1 = new BlockedCharacter();
        ownBlocked1.setCharacterId(CHARACTER_ID_1);
        ownBlocked1.setBlockedCharacterId(CHARACTER_ID_2);
        when(blockedCharacterQueryService.getBlockedCharactersOf(CHARACTER_ID_1)).thenReturn(Arrays.asList(ownBlocked1));

        BlockedCharacter blocked2BlocksCharacter = new BlockedCharacter();
        blocked2BlocksCharacter.setCharacterId(CHARACTER_ID_3);
        blocked2BlocksCharacter.setBlockedCharacterId(CHARACTER_ID_1);
        when(blockedCharacterQueryService.getBlockedCharactersOf(CHARACTER_ID_3)).thenReturn(Arrays.asList(blocked2BlocksCharacter));

        when(friendshipQueryService.isFriendshipAlreadyExists(CHARACTER_ID_6, CHARACTER_ID_1)).thenReturn(true);
        when(friendshipQueryService.isFriendRequestAlreadyExists(CHARACTER_ID_7, CHARACTER_ID_1)).thenReturn(true);
        //WHEN
        List<SkyXpCharacter> result = underTest.getCharactersCanBeFriend(CHARACTER_NAME, CHARACTER_ID_1);
        //THEN
        assertEquals(1, result.size());
        assertTrue(result.contains(canBeAddressee));
        verify(characterDao).findById(CHARACTER_ID_1);
        verify(characterNameLikeCache).get(CHARACTER_NAME);
    }

    @Test
    public void testGetCharactersByUserIdShouldCallDaoAndReturn(){
        //GIVEN
        SkyXpCharacter character = createCharacter();
        when(characterDao.findByUserId(USER_ID)).thenReturn(Arrays.asList(character));
        //WHEN
        List<SkyXpCharacter> result = underTest.getCharactersByUserId(USER_ID);
        //THEN
        assertTrue(result.contains(character));
        verify(characterDao).findByUserId(USER_ID);
    }

    @Test
    public void testGetEquipmentsOfCharacterShouldReturn(){
        //GIVEN
        SkyXpCharacter character = createCharacter();
        when(characterDao.findById(CHARACTER_ID_1)).thenReturn(Optional.of(character));

        Map<String, GeneralDescription> equipmentDataMap = createGeneralDescriptionMap();
        when(gameDataFacade.collectEquipmentData(character.getEquipments())).thenReturn(equipmentDataMap);
        //WHEN
        View<EquipmentViewList> result = underTest.getEquipmentsOfCharacter(CHARACTER_ID_1);
        //THEN
        verify(characterDao).findById(CHARACTER_ID_1);
        verify(gameDataFacade).collectEquipmentData(character.getEquipments());
        assertEquals(character.getEquipments(), result.getInfo());
        assertEquals(equipmentDataMap, result.getData());
    }

    @Test
    public void testGetMoneyOfCharacter(){
        //GIVEN
        SkyXpCharacter character = createCharacter();
        when(characterDao.findById(CHARACTER_ID_1)).thenReturn(Optional.of(character));
        //WHEN
        Integer result = underTest.getMoneyOfCharacter(CHARACTER_ID_1);
        //THEN
        assertEquals(CHARACTER_MONEY, result);
    }

    @Test
    public void testIsCharNameExists(){
        //GIVEN
        SkyXpCharacter character = createCharacter();
        when(characterDao.findByCharacterName(CHARACTER_NAME)).thenReturn(character);
        //WHEN
        assertTrue(underTest.isCharNameExists(CHARACTER_NAME));
    }
}