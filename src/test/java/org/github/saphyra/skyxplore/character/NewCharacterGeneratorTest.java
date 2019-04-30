package org.github.saphyra.skyxplore.character;

import static org.assertj.core.api.Assertions.assertThat;
import static org.github.saphyra.skyxplore.character.NewCharacterGenerator.ARMOR_ID;
import static org.github.saphyra.skyxplore.character.NewCharacterGenerator.BATTERY_ID;
import static org.github.saphyra.skyxplore.character.NewCharacterGenerator.GENERATOR_ID;
import static org.github.saphyra.skyxplore.character.NewCharacterGenerator.LASER_ID;
import static org.github.saphyra.skyxplore.character.NewCharacterGenerator.LAUNCHER_ID;
import static org.github.saphyra.skyxplore.character.NewCharacterGenerator.RIFLE_ID;
import static org.github.saphyra.skyxplore.character.NewCharacterGenerator.SHIELD_ID;
import static org.github.saphyra.skyxplore.character.NewCharacterGenerator.STARTER_SHIP_ID;
import static org.github.saphyra.skyxplore.character.NewCharacterGenerator.STORAGE_ID;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.gamedata.entity.Ship;
import org.github.saphyra.skyxplore.gamedata.entity.Slot;
import org.github.saphyra.skyxplore.gamedata.subservice.MaterialService;
import org.github.saphyra.skyxplore.gamedata.subservice.ShipService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.util.IdGenerator;
import skyxplore.domain.factory.Factory;
import org.github.saphyra.skyxplore.ship.domain.EquippedShip;
import org.github.saphyra.skyxplore.slot.domain.EquippedSlot;

@RunWith(MockitoJUnitRunner.class)
public class NewCharacterGeneratorTest {
    private static final String CHARACTER_ID = "character_id";
    private static final Integer MONEY = 5;
    private static final String USER_ID = "user_id";
    private static final String CHARACTER_NAME = "character_name";
    private static final String EQUIPPED_SHIP_ID = "ship_id";
    private static final Integer COREHULL = 10000;
    private static final Integer CONNECTOR_SLOT = 6;
    private static final String ABILITY = "ability";
    private static final String DEFENSE_SLOT_ID = "defense_slot_id";
    private static final String WEAPON_SLOT_ID = "weapon_slot_id";
    private static final String FACTORY_ID = "factory_id";
    private static final String MATERIAL_ID = "material_id";
    private static final Integer MATERIAL_AMOUNT = 2;

    @Mock
    private CharacterGeneratorConfig config;

    @Mock
    private IdGenerator idGenerator;

    @Mock
    private MaterialService materialService;

    @Mock
    private ShipService shipService;

    @InjectMocks
    private NewCharacterGenerator underTest;

    @Test
    public void testCreateCharacterShouldCreate() {
        //GIVEN
        when(idGenerator.generateRandomId()).thenReturn(CHARACTER_ID);
        when(config.getStartMoney()).thenReturn(MONEY);
        //WHEN
        SkyXpCharacter result = underTest.createCharacter(USER_ID, CHARACTER_NAME);
        //THEN
        assertThat(result.getCharacterId()).isEqualTo(CHARACTER_ID);
        assertThat(result.getCharacterName()).isEqualTo(CHARACTER_NAME);
        assertThat(result.getUserId()).isEqualTo(USER_ID);
        assertThat(result.getMoney()).isEqualTo(MONEY);
    }

    @Test
    public void testCreateShip() {
        //GIVEN
        when(idGenerator.generateRandomId()).thenReturn(EQUIPPED_SHIP_ID);

        Ship ship = createShip();
        ship.setConnector(6);
        when(shipService.get(STARTER_SHIP_ID)).thenReturn(ship);
        //WHEN
        EquippedShip result = underTest.createShip(CHARACTER_ID);
        //THEN
        assertThat(result.getShipId()).isEqualTo(EQUIPPED_SHIP_ID);
        assertThat(result.getCharacterId()).isEqualTo(CHARACTER_ID);
        assertThat(result.getShipType()).isEqualTo(STARTER_SHIP_ID);
        assertThat(result.getConnectorSlot()).isEqualTo(CONNECTOR_SLOT);
        assertThat(result.getCoreHull()).isEqualTo(COREHULL);
        assertThat(result.getConnectorEquipped()).contains(GENERATOR_ID);
        assertThat(result.getConnectorEquipped()).contains(BATTERY_ID);
        assertThat(result.getConnectorEquipped()).contains(STORAGE_ID);
    }

    @Test
    public void testCreateDefenseSlot() {
        //GIVEN
        Ship ship = createShip();
        Slot defenseSlot = new Slot();
        defenseSlot.setFront(4);
        defenseSlot.setSide(2);
        defenseSlot.setBack(2);
        ship.setDefense(defenseSlot);
        when(shipService.get(STARTER_SHIP_ID)).thenReturn(ship);
        when(idGenerator.generateRandomId()).thenReturn(DEFENSE_SLOT_ID);
        //WHEN
        EquippedSlot result = underTest.createDefenseSlot(EQUIPPED_SHIP_ID);
        //THEN
        assertThat(result.getFrontSlot()).isEqualTo(4);
        assertThat(result.getLeftSlot()).isEqualTo(2);
        assertThat(result.getRightSlot()).isEqualTo(2);
        assertThat(result.getBackSlot()).isEqualTo(2);

        assertThat(result.getFrontEquipped())
            .contains(SHIELD_ID)
            .contains(ARMOR_ID);

        assertThat(result.getLeftEquipped())
            .contains(SHIELD_ID)
            .contains(ARMOR_ID);

        assertThat(result.getRightEquipped())
            .contains(SHIELD_ID)
            .contains(ARMOR_ID);

        assertThat(result.getBackEquipped())
            .contains(SHIELD_ID)
            .contains(ARMOR_ID);
    }

    @Test
    public void testCreateWeaponSlot() {
        //GIVEN
        Ship ship = createShip();
        Slot weaponSlot = new Slot();
        weaponSlot.setFront(3);
        weaponSlot.setSide(1);
        weaponSlot.setBack(1);
        ship.setWeapon(weaponSlot);
        when(shipService.get(STARTER_SHIP_ID)).thenReturn(ship);
        when(idGenerator.generateRandomId()).thenReturn(WEAPON_SLOT_ID);
        //WHEN
        EquippedSlot result = underTest.createWeaponSlot(EQUIPPED_SHIP_ID);
        //THEN
        verify(idGenerator).generateRandomId();
        verify(shipService).get(STARTER_SHIP_ID);

        assertThat(result.getFrontSlot()).isEqualTo(3);
        assertThat(result.getLeftSlot()).isEqualTo(1);
        assertThat(result.getRightSlot()).isEqualTo(1);
        assertThat(result.getBackSlot()).isEqualTo(1);

        assertThat(result.getFrontEquipped())
            .contains(LASER_ID)
            .contains(LAUNCHER_ID);

        assertThat(result.getLeftEquipped()).contains(RIFLE_ID);
        assertThat(result.getRightEquipped()).contains(RIFLE_ID);
        assertThat(result.getBackEquipped()).contains(RIFLE_ID);
    }

    @Test
    public void testCreateFactoryShouldCreate() {
        //GIVEN
        when(idGenerator.generateRandomId()).thenReturn(FACTORY_ID);
        Set<String> materialIds = new HashSet<>(Arrays.asList(MATERIAL_ID));
        when(materialService.keySet()).thenReturn(materialIds);
        when(config.getStartMaterials()).thenReturn(MATERIAL_AMOUNT);
        //WHEN
        Factory result = underTest.createFactory(CHARACTER_ID);
        //THEN
        assertThat(result.getFactoryId()).isEqualTo(FACTORY_ID);
        assertThat(result.getCharacterId()).isEqualTo(CHARACTER_ID);
        assertThat(result.getMaterials()).containsKey(MATERIAL_ID);
        assertThat(result.getMaterials().get(MATERIAL_ID)).isEqualTo(MATERIAL_AMOUNT);
    }

    private Ship createShip() {
        Ship ship = new Ship();
        ship.setCoreHull(COREHULL);
        ship.setConnector(CONNECTOR_SLOT);
        ship.setDefense(createDefenseSlot());
        ship.setWeapon(createWeaponSlot());
        ArrayList<String> list = new ArrayList<>();
        list.add(ABILITY);
        ship.setAbility(list);
        return ship;
    }

    private static Slot createDefenseSlot() {
        Slot slot = new Slot();
        Integer SLOT_DEFENSE_FRONT = 2;
        slot.setFront(SLOT_DEFENSE_FRONT);
        Integer SLOT_DEFENSE_SIDE = 3;
        slot.setSide(SLOT_DEFENSE_SIDE);
        Integer slOT_DEFENSE_BACK = 5;
        slot.setBack(slOT_DEFENSE_BACK);
        return slot;
    }

    private static Slot createWeaponSlot() {
        Slot slot = new Slot();
        Integer SLOT_WEAPON_FRONT = 7;
        slot.setFront(SLOT_WEAPON_FRONT);
        Integer SLOT_WEAPON_SIDE = 11;
        slot.setSide(SLOT_WEAPON_SIDE);
        Integer SLOT_WEAPON_BACK = 13;
        slot.setBack(SLOT_WEAPON_BACK);
        return slot;
    }
}