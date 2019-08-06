package com.github.saphyra.skyxplore.userdata.character.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.common.ObjectMapperDelegator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.encryption.impl.IntegerEncryptor;
import com.github.saphyra.encryption.impl.StringEncryptor;

@RunWith(MockitoJUnitRunner.class)
public class CharacterConverterTest {
    private static final String CHARACTER_ID = "character_id";
    private static final Integer MONEY = 5;
    private static final String ENCRYPTED_MONEY = "encrypted_money";
    private static final String USER_ID = "user_id";
    private static final String CHARACTER_NAME = "character_name";
    private static final String ENCRYPTED_EQUIPMENTS = "encrypted_equipments";
    private static final String EQUIPMENTS = "equipments";
    private static final String EQUIPMENT = "equipment";

    @Mock
    private IntegerEncryptor integerEncryptor;

    @Mock
    private ObjectMapperDelegator objectMapperDelegator;

    @Mock
    private StringEncryptor stringEncryptor;

    @InjectMocks
    private CharacterConverter underTest;

    @Test
    public void testConvertEntityShouldReturnNullWhenNull() {
        //GIVEN
        CharacterEntity entity = null;
        //WHEN
        SkyXpCharacter result = underTest.convertEntity(entity);
        //THEN
        assertThat(result).isNull();
    }

    @Test
    public void testConvertEntityShouldConvertAndDecrypt() {
        //GIVEN
        CharacterEntity entity = createCharacterEntity();
        when(integerEncryptor.decryptEntity(ENCRYPTED_MONEY, CHARACTER_ID)).thenReturn(MONEY);

        when(stringEncryptor.decryptEntity(ENCRYPTED_EQUIPMENTS, CHARACTER_ID)).thenReturn(EQUIPMENTS);

        String[] equipmentArray = new String[]{EQUIPMENT};
        List<String> equipmentList = Arrays.asList(equipmentArray);
        when(objectMapperDelegator.readValue(EQUIPMENTS, String[].class)).thenReturn(equipmentList);
        //WHEN
        SkyXpCharacter result = underTest.convertEntity(entity);
        //THEN
        assertThat(result.getCharacterId()).isEqualTo(CHARACTER_ID);
        assertThat(result.getCharacterName()).isEqualTo(CHARACTER_NAME);
        assertThat(result.getUserId()).isEqualTo(USER_ID);
        assertThat(result.getMoney()).isEqualTo(MONEY);
        assertThat(result.getEquipments()).isEqualTo(equipmentList);
    }

    @Test
    public void testConvertDomainShouldEncryptAndConvert() {
        //GIVEN
        SkyXpCharacter character = createCharacter();

        when(integerEncryptor.encryptEntity(MONEY, CHARACTER_ID)).thenReturn(ENCRYPTED_MONEY);

        when(objectMapperDelegator.writeValueAsString(character.getEquipments())).thenReturn(EQUIPMENTS);
        when(stringEncryptor.encryptEntity(EQUIPMENTS, CHARACTER_ID)).thenReturn(ENCRYPTED_EQUIPMENTS);
        //WHEN
        CharacterEntity result = underTest.convertDomain(character);
        //THEN
        assertThat(result.getCharacterId()).isEqualTo(CHARACTER_ID);
        assertThat(result.getCharacterName()).isEqualTo(CHARACTER_NAME);
        assertThat(result.getUserId()).isEqualTo(USER_ID);
        assertThat(result.getMoney()).isEqualTo(ENCRYPTED_MONEY);
        assertThat(result.getEquipments()).isEqualTo(ENCRYPTED_EQUIPMENTS);
    }

    private CharacterEntity createCharacterEntity() {
        CharacterEntity characterEntity = new CharacterEntity();
        characterEntity.setCharacterId(CHARACTER_ID);
        characterEntity.setUserId(USER_ID);
        characterEntity.setCharacterName(CHARACTER_NAME);
        characterEntity.setMoney(ENCRYPTED_MONEY);
        characterEntity.setEquipments(ENCRYPTED_EQUIPMENTS);
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
