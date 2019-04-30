package org.github.saphyra.skyxplore.character;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.github.saphyra.skyxplore.character.cache.CharacterNameCache;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.character.domain.request.CreateCharacterRequest;
import org.github.saphyra.skyxplore.character.repository.CharacterDao;
import org.github.saphyra.skyxplore.ship.domain.EquippedShip;
import org.github.saphyra.skyxplore.ship.repository.EquippedShipDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import skyxplore.dataaccess.db.FactoryDao;
import skyxplore.dataaccess.db.SlotDao;
import skyxplore.domain.factory.Factory;
import skyxplore.domain.slot.EquippedSlot;
import skyxplore.exception.CharacterNameAlreadyExistsException;

@RunWith(MockitoJUnitRunner.class)
public class CharacterCreatorServiceTest {
    private static final String CHARACTER_NAME = "character_name";
    private static final CreateCharacterRequest CREATE_CHARACTER_REQUEST = new CreateCharacterRequest(CHARACTER_NAME);
    private static final String USER_ID = "user_id";
    private static final String CHARACTER_ID = "character_id";
    private static final String EQUIPPED_SHIP_ID = "equipped_ship_id";
    private static final String  DEFENSE_SLOT_ID = "defense_slot_id";
    private static final String WEAPON_SLOT_ID = "weapon_slot_id";

    @Mock
    private CharacterDao characterDao;

    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private EquippedShipDao equippedShipDao;

    @Mock
    private FactoryDao factoryDao;

    @Mock
    private NewCharacterGenerator newCharacterGenerator;

    @Mock
    private SlotDao slotDao;

    @Mock
    private CharacterNameCache characterNameCache;

    @InjectMocks
    private CharacterCreatorService underTest;

    @Test(expected = CharacterNameAlreadyExistsException.class)
    public void testCreateCharacterShouldThrowExceptionWhenCharacterNameExists() {
        //GIVEN
        when(characterQueryService.isCharNameExists(CHARACTER_NAME)).thenReturn(true);
        //WHEN
        underTest.createCharacter(CREATE_CHARACTER_REQUEST, USER_ID);
    }

    @Test
    public void testCreateCharacterShouldCreateAdSave() {
        //GIVEN
        when(characterQueryService.isCharNameExists(CHARACTER_NAME)).thenReturn(false);

        SkyXpCharacter character = SkyXpCharacter.builder()
            .characterId(CHARACTER_ID)
            .build();
        when(newCharacterGenerator.createCharacter(USER_ID, CHARACTER_NAME)).thenReturn(character);

        EquippedShip ship = EquippedShip.builder()
            .shipId(EQUIPPED_SHIP_ID)
            .build();
        when(newCharacterGenerator.createShip(CHARACTER_ID)).thenReturn(ship);

        EquippedSlot defenseSlot = EquippedSlot.builder()
            .shipId(EQUIPPED_SHIP_ID)
            .slotId(DEFENSE_SLOT_ID)
            .build();
        when(newCharacterGenerator.createDefenseSlot(EQUIPPED_SHIP_ID)).thenReturn(defenseSlot);

        EquippedSlot weaponSlot = EquippedSlot.builder()
            .shipId(EQUIPPED_SHIP_ID)
            .slotId(WEAPON_SLOT_ID)
            .build();
        when(newCharacterGenerator.createWeaponSlot(EQUIPPED_SHIP_ID)).thenReturn(weaponSlot);

        Factory factory = Factory.builder()
            .build();
        when(newCharacterGenerator.createFactory(CHARACTER_ID)).thenReturn(factory);
        //WHEN
        SkyXpCharacter result = underTest.createCharacter(CREATE_CHARACTER_REQUEST, USER_ID);
        //THEN
        verify(characterDao).save(character);
        verify(equippedShipDao).save(ship);
        verify(slotDao).save(defenseSlot);
        verify(slotDao).save(weaponSlot);
        verify(factoryDao).save(factory);
        assertThat(ship.getDefenseSlotId()).isEqualTo(DEFENSE_SLOT_ID);
        assertThat(ship.getWeaponSlotId()).isEqualTo(WEAPON_SLOT_ID);
        assertThat(result).isEqualTo(character);
        verify(characterNameCache).invalidate(CHARACTER_NAME);
    }
}