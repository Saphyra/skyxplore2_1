package skyxplore.dataaccess.db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.dataaccess.db.repository.CharacterRepository;
import skyxplore.domain.character.CharacterConverter;
import skyxplore.domain.character.CharacterEntity;
import skyxplore.domain.character.SkyXpCharacter;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.CHARACTER_NAME;
import static skyxplore.testutil.TestUtils.USER_ID;
import static skyxplore.testutil.TestUtils.createCharacter;
import static skyxplore.testutil.TestUtils.createCharacterEntity;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
@RunWith(MockitoJUnitRunner.class)
public class CharacterDaoTest {
    @Mock
    private  BlockedCharacterDao blockedCharacterDao;

    @Mock
    private  CharacterConverter characterConverter;

    @Mock
    private  CharacterRepository characterRepository;

    @Mock
    private  EquippedShipDao equippedShipDao;

    @Mock
    private  FactoryDao factoryDao;

    @Mock
    private  FriendRequestDao friendRequestDao;

    @Mock
    private  FriendshipDao friendshipDao;

    @Mock
    private  MailDao mailDao;

    @InjectMocks
    private CharacterDao underTest;

    @Test
    public void testDeleteByIdShouldCallRepositoryAndDaos(){
        //WHEN
        underTest.deleteById(CHARACTER_ID_1);
        //THEN
        verify(equippedShipDao).deleteByCharacterId(CHARACTER_ID_1);
        verify(factoryDao).deleteByCharacterId(CHARACTER_ID_1);
        verify(friendRequestDao).deleteByCharacterId(CHARACTER_ID_1);
        verify(friendshipDao).deleteByCharacterId(CHARACTER_ID_1);
        verify(blockedCharacterDao).deleteByCharacterId(CHARACTER_ID_1);
        verify(mailDao).deleteByCharacterId(CHARACTER_ID_1);
        verify(characterRepository).deleteById(CHARACTER_ID_1);
    }

    @Test
    public void testDeleteByUserIdShouldDeleteAllCharactersOfUser(){
        //GIVEN
        CharacterEntity entity = createCharacterEntity();
        List<CharacterEntity> entityList = Arrays.asList(entity);
        when(characterRepository.findByUserId(USER_ID)).thenReturn(entityList);

        SkyXpCharacter character = createCharacter();
        List<SkyXpCharacter> characterList = Arrays.asList(character);
        when(characterConverter.convertEntity(entityList)).thenReturn(characterList);
        //WHEN
        underTest.deleteByUserId(USER_ID);
        //THEN
        verify(characterRepository).findByUserId(USER_ID);
        verify(characterRepository).deleteById(CHARACTER_ID_1);
    }

    @Test
    public void testFindByCharacterNameShouldCallRepositoryAndReturnDomain(){
        //GIVEN
        CharacterEntity entity = createCharacterEntity();
        when(characterRepository.findByCharacterName(CHARACTER_NAME)).thenReturn(entity);

        SkyXpCharacter character = createCharacter();
        when(characterConverter.convertEntity(entity)).thenReturn(character);
        //WHEN
        SkyXpCharacter result = underTest.findByCharacterName(CHARACTER_NAME);
        //THEN
        verify(characterRepository).findByCharacterName(CHARACTER_NAME);
        verify(characterConverter).convertEntity(entity);
        assertEquals(character, result);
    }

    @Test
    public void testFindCharacterByNameLikeShouldCallRepositoryAndReturnDomain(){
        //GIVEN
        CharacterEntity entity = createCharacterEntity();
        List<CharacterEntity> entityList = Arrays.asList(entity);
        when(characterRepository.findByCharacterNameContaining(CHARACTER_NAME)).thenReturn(entityList);

        SkyXpCharacter character = createCharacter();
        List<SkyXpCharacter> characters = Arrays.asList(character);
        when(characterConverter.convertEntity(entityList)).thenReturn(characters);
        //WHEN
        List<SkyXpCharacter> result = underTest.findCharacterByNameLike(CHARACTER_NAME);
        //THEN
        verify(characterRepository).findByCharacterNameContaining(CHARACTER_NAME);
        verify(characterConverter).convertEntity(entityList);
        assertEquals(characters, result);
    }

    @Test
    public void testFindByUserIdShouldCallRepositoryAndReturnDomain(){
        //GIVEN
        CharacterEntity entity = createCharacterEntity();
        List<CharacterEntity> entityList = Arrays.asList(entity);
        when(characterRepository.findByUserId(USER_ID)).thenReturn(entityList);

        SkyXpCharacter character = createCharacter();
        List<SkyXpCharacter> characterList = Arrays.asList(character);
        when(characterConverter.convertEntity(entityList)).thenReturn(characterList);
        //WHEN
        List<SkyXpCharacter> result = underTest.findByUserId(USER_ID);
        //THEN
        verify(characterRepository).findByUserId(USER_ID);
        verify(characterConverter).convertEntity(entityList);
        assertEquals(characterList, result);
    }

    @Test
    public void testSaveShouldCallRepository(){
        //GIVEN
        SkyXpCharacter character = createCharacter();

        CharacterEntity entity = createCharacterEntity();
        when(characterConverter.convertDomain(character)).thenReturn(entity);
        //WHEN
        underTest.save(character);
        //THEN
        verify(characterConverter).convertDomain(character);
        verify(characterRepository).save(entity);
    }
}
