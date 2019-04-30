package org.github.saphyra.skyxplore.ship.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;

import org.github.saphyra.skyxplore.ship.domain.EquippedShip;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.saphyra.encryption.impl.IntegerEncryptor;
import com.github.saphyra.encryption.impl.StringEncryptor;

@RunWith(MockitoJUnitRunner.class)
public class EquippedShipConverterTest {
    private static final String EQUIPPED_SHIP_ID = "equipped_ship_id";
    private static final String ENCRYPTED_SHIP_TYPE = "encrypted_ship_type";
    private static final String SHIP_TYPE = "ship_type";
    private static final String ENCRYPTED_COREHULL = "encrypted_corehull";
    private static final Integer COREHULL = 10000;
    private static final String ENCRYPTED_CONNECTOR_SLOT = "encrypted_connector_slot";
    private static final Integer CONNECTOR_SLOT = 6;
    private static final String ENCRYPTED_CONNECTOR_EQUIPPED = "encrypted_connector_equipped";
    private static final String CONNECTOR_EQUIPPED = "connector_equipped";
    private static final String CHARACTER_ID = "character_id";
    private static final String DEFENSE_SLOT_ID = "defense_slot_id";
    private static final String WEAPON_SLOT_ID = "weapon_slot_id";
    @Mock
    private IntegerEncryptor integerEncryptor;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private StringEncryptor stringEncryptor;

    @InjectMocks
    private EquippedShipConverter underTest;

    @Test
    public void testConvertEntityShouldReturnNull() {
        //GIVEN
        EquippedShipEntity entity = null;
        //WHEN
        EquippedShip result = underTest.convertEntity(entity);
        //THEN
        assertThat(result).isNull();
    }

    @Test
    public void testConvertEntityShouldDecryptAndConvert() throws IOException {
        //GIVEN
        EquippedShipEntity equippedShipEntity = EquippedShipEntity.builder()
            .shipId(EQUIPPED_SHIP_ID)
            .characterId(CHARACTER_ID)
            .shipType(ENCRYPTED_SHIP_TYPE)
            .coreHull(ENCRYPTED_COREHULL)
            .connectorSlot(ENCRYPTED_CONNECTOR_SLOT)
            .connectorEquipped(ENCRYPTED_CONNECTOR_EQUIPPED)
            .defenseSlotId(DEFENSE_SLOT_ID)
            .weaponSlotId(WEAPON_SLOT_ID)
            .build();

        when(stringEncryptor.decryptEntity(ENCRYPTED_SHIP_TYPE, EQUIPPED_SHIP_ID)).thenReturn(SHIP_TYPE);
        when(integerEncryptor.decryptEntity(ENCRYPTED_COREHULL, EQUIPPED_SHIP_ID)).thenReturn(COREHULL);
        when(integerEncryptor.decryptEntity(ENCRYPTED_CONNECTOR_SLOT, EQUIPPED_SHIP_ID)).thenReturn(CONNECTOR_SLOT);
        when(stringEncryptor.decryptEntity(ENCRYPTED_CONNECTOR_EQUIPPED, EQUIPPED_SHIP_ID)).thenReturn(CONNECTOR_EQUIPPED);
        String[] connectorsArray = new String[]{CONNECTOR_EQUIPPED};
        when(objectMapper.readValue(CONNECTOR_EQUIPPED, String[].class)).thenReturn(connectorsArray);
        //WHEN
        EquippedShip result = underTest.convertEntity(equippedShipEntity);
        //THEN
        verify(stringEncryptor).decryptEntity(ENCRYPTED_SHIP_TYPE, EQUIPPED_SHIP_ID);
        verify(integerEncryptor).decryptEntity(ENCRYPTED_COREHULL, EQUIPPED_SHIP_ID);
        verify(integerEncryptor).decryptEntity(ENCRYPTED_CONNECTOR_SLOT, EQUIPPED_SHIP_ID);
        verify(stringEncryptor).decryptEntity(ENCRYPTED_CONNECTOR_EQUIPPED, EQUIPPED_SHIP_ID);
        verify(objectMapper).readValue(CONNECTOR_EQUIPPED, String[].class);
        assertThat(result.getShipId()).isEqualTo(EQUIPPED_SHIP_ID);
        assertThat(result.getCharacterId()).isEqualTo(CHARACTER_ID);
        assertThat(result.getShipType()).isEqualTo(SHIP_TYPE);
        assertThat(result.getCoreHull()).isEqualTo(COREHULL);
        assertThat(result.getConnectorSlot()).isEqualTo(CONNECTOR_SLOT);
        assertThat(result.getConnectorEquipped()).containsOnly(CONNECTOR_EQUIPPED);
        assertThat(result.getDefenseSlotId()).isEqualTo(DEFENSE_SLOT_ID);
        assertThat(result.getWeaponSlotId()).isEqualTo(WEAPON_SLOT_ID);
    }

    @Test
    public void testConvertDomainShouldEncryptAndConvert() throws JsonProcessingException {
        //GIVEN
        EquippedShip equippedShip = EquippedShip.builder()
            .shipId(EQUIPPED_SHIP_ID)
            .characterId(CHARACTER_ID)
            .shipType(SHIP_TYPE)
            .coreHull(COREHULL)
            .connectorSlot(CONNECTOR_SLOT)
            .defenseSlotId(DEFENSE_SLOT_ID)
            .weaponSlotId(WEAPON_SLOT_ID)
            .build();

        when(stringEncryptor.encryptEntity(SHIP_TYPE, EQUIPPED_SHIP_ID)).thenReturn(ENCRYPTED_SHIP_TYPE);
        when(integerEncryptor.encryptEntity(COREHULL, EQUIPPED_SHIP_ID)).thenReturn(ENCRYPTED_COREHULL);
        when(integerEncryptor.encryptEntity(CONNECTOR_SLOT, EQUIPPED_SHIP_ID)).thenReturn(ENCRYPTED_CONNECTOR_SLOT);
        when(objectMapper.writeValueAsString(any(ArrayList.class))).thenReturn(CONNECTOR_EQUIPPED);
        when(stringEncryptor.encryptEntity(CONNECTOR_EQUIPPED, EQUIPPED_SHIP_ID)).thenReturn(ENCRYPTED_CONNECTOR_EQUIPPED);
        //WHEN
        EquippedShipEntity result = underTest.convertDomain(equippedShip);
        //THEN
        verify(objectMapper).writeValueAsString(any(ArrayList.class));
        assertThat(result.getShipId()).isEqualTo(EQUIPPED_SHIP_ID);
        assertThat(result.getCharacterId()).isEqualTo(CHARACTER_ID);
        assertThat(result.getShipType()).isEqualTo(ENCRYPTED_SHIP_TYPE);
        assertThat(result.getCoreHull()).isEqualTo(ENCRYPTED_COREHULL);
        assertThat(result.getConnectorSlot()).isEqualTo(ENCRYPTED_CONNECTOR_SLOT);
        assertThat(result.getConnectorEquipped()).isEqualTo(ENCRYPTED_CONNECTOR_EQUIPPED);
        assertThat(result.getDefenseSlotId()).isEqualTo(DEFENSE_SLOT_ID);
        assertThat(result.getWeaponSlotId()).isEqualTo(WEAPON_SLOT_ID);
    }
}
