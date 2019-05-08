package com.github.saphyra.skyxplore.character.repository;

import com.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.event.AccountDeletedEvent;
import com.github.saphyra.skyxplore.event.CharacterDeletedEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CharacterDaoTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String USER_ID = "user_id";
    private static final String CHARACTER_NAME = "character_name";
    private static final String MONEY_STRING = "money_string";
    private static final String EQUIPMENTS_STRING = "equipments_string";
    private static final Integer MONEY = 5;
    private static final String EQUIPMENT = "equipment";

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Mock
    private CharacterConverter characterConverter;

    @Mock
    private CharacterRepository characterRepository;

    @InjectMocks
    private CharacterDao underTest;

    @Test
    public void testDeleteByIdShouldSendEventAndDelete() {
        //WHEN
        underTest.deleteById(CHARACTER_ID);
        //THEN
        ArgumentCaptor<CharacterDeletedEvent> argumentCaptor = ArgumentCaptor.forClass(CharacterDeletedEvent.class);
        verify(applicationEventPublisher).publishEvent(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getCharacterId()).isEqualTo(CHARACTER_ID);
        verify(characterRepository).deleteById(CHARACTER_ID);
    }

    @Test
    public void testDeleteByUserIdShouldDeleteAllCharactersOfUser() {
        //GIVEN
        CharacterEntity entity = createCharacterEntity();
        List<CharacterEntity> entityList = Arrays.asList(entity);
        when(characterRepository.getByUserId(USER_ID)).thenReturn(entityList);

        SkyXpCharacter character = createCharacter();
        List<SkyXpCharacter> characterList = Arrays.asList(character);
        when(characterConverter.convertEntity(entityList)).thenReturn(characterList);
        //WHEN
        underTest.deleteByUserId(new AccountDeletedEvent(USER_ID));
        //THEN
        verify(characterRepository).getByUserId(USER_ID);
        verify(characterRepository).deleteById(CHARACTER_ID);
    }

    @Test
    public void testFindByCharacterNameShouldCallRepositoryAndReturnDomain() {
        //GIVEN
        Optional<CharacterEntity> entity = Optional.of(createCharacterEntity());
        when(characterRepository.findByCharacterName(CHARACTER_NAME)).thenReturn(entity);

        Optional<SkyXpCharacter> character = Optional.of(createCharacter());
        when(characterConverter.convertEntity(entity)).thenReturn(character);
        //WHEN
        Optional<SkyXpCharacter> result = underTest.findByCharacterName(CHARACTER_NAME);
        //THEN
        verify(characterRepository).findByCharacterName(CHARACTER_NAME);
        verify(characterConverter).convertEntity(entity);
        assertThat(result).isEqualTo(character);
    }

    @Test
    public void testGetCharacterByNameLikeShouldCallRepositoryAndReturnDomain() {
        //GIVEN
        CharacterEntity entity = createCharacterEntity();
        List<CharacterEntity> entityList = Arrays.asList(entity);
        when(characterRepository.getByCharacterNameContaining(CHARACTER_NAME)).thenReturn(entityList);

        SkyXpCharacter character = createCharacter();
        List<SkyXpCharacter> characters = Arrays.asList(character);
        when(characterConverter.convertEntity(entityList)).thenReturn(characters);
        //WHEN
        List<SkyXpCharacter> result = underTest.getCharacterByNameLike(CHARACTER_NAME);
        //THEN
        verify(characterRepository).getByCharacterNameContaining(CHARACTER_NAME);
        verify(characterConverter).convertEntity(entityList);
        assertThat(result).isEqualTo(characters);
    }

    @Test
    public void testGetByUserIdShouldCallRepositoryAndReturnDomain() {
        //GIVEN
        CharacterEntity entity = createCharacterEntity();
        List<CharacterEntity> entityList = Arrays.asList(entity);
        when(characterRepository.getByUserId(USER_ID)).thenReturn(entityList);

        SkyXpCharacter character = createCharacter();
        List<SkyXpCharacter> characterList = Arrays.asList(character);
        when(characterConverter.convertEntity(entityList)).thenReturn(characterList);
        //WHEN
        List<SkyXpCharacter> result = underTest.getByUserId(USER_ID);
        //THEN
        verify(characterRepository).getByUserId(USER_ID);
        verify(characterConverter).convertEntity(entityList);
        assertThat(result).isEqualTo(characterList);
    }

    @Test
    public void testSaveShouldCallRepository() {
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

    private CharacterEntity createCharacterEntity() {
        CharacterEntity characterEntity = new CharacterEntity();
        characterEntity.setCharacterId(CHARACTER_ID);
        characterEntity.setUserId(USER_ID);
        characterEntity.setCharacterName(CHARACTER_NAME);
        characterEntity.setMoney(MONEY_STRING);
        characterEntity.setEquipments(EQUIPMENTS_STRING);
        return characterEntity;
    }

    private SkyXpCharacter createCharacter() {
        SkyXpCharacter character = SkyXpCharacter.builder()
            .characterId(CHARACTER_ID)
            .characterName(CHARACTER_NAME)
            .userId(USER_ID)
            .money(MONEY)
            .build();
        character.addEquipment(EQUIPMENT);
        return character;
    }
}
