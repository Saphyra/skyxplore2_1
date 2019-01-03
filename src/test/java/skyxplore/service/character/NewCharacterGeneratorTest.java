package skyxplore.service.character;

import com.github.saphyra.util.IdGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.configuration.CharacterGeneratorConfig;
import skyxplore.dataaccess.gamedata.entity.Ship;
import skyxplore.dataaccess.gamedata.entity.Slot;
import skyxplore.dataaccess.gamedata.subservice.MaterialService;
import skyxplore.dataaccess.gamedata.subservice.ShipService;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.domain.factory.Factory;
import skyxplore.domain.ship.EquippedShip;
import skyxplore.domain.slot.EquippedSlot;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.service.character.NewCharacterGenerator.ARMOR_ID;
import static skyxplore.service.character.NewCharacterGenerator.BATTERY_ID;
import static skyxplore.service.character.NewCharacterGenerator.GENERATOR_ID;
import static skyxplore.service.character.NewCharacterGenerator.LASER_ID;
import static skyxplore.service.character.NewCharacterGenerator.LAUNCHER_ID;
import static skyxplore.service.character.NewCharacterGenerator.RIFLE_ID;
import static skyxplore.service.character.NewCharacterGenerator.SHIELD_ID;
import static skyxplore.service.character.NewCharacterGenerator.STARTER_SHIP_ID;
import static skyxplore.service.character.NewCharacterGenerator.STORAGE_ID;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.CHARACTER_MONEY;
import static skyxplore.testutil.TestUtils.CHARACTER_NAME;
import static skyxplore.testutil.TestUtils.DATA_SHIP_COREHULL;
import static skyxplore.testutil.TestUtils.DEFENSE_SLOT_ID;
import static skyxplore.testutil.TestUtils.EQUIPPED_SHIP_ID;
import static skyxplore.testutil.TestUtils.FACTORY_ID_1;
import static skyxplore.testutil.TestUtils.MATERIAL_ID;
import static skyxplore.testutil.TestUtils.MATERIAL_MATERIAL_AMOUNT;
import static skyxplore.testutil.TestUtils.USER_ID;
import static skyxplore.testutil.TestUtils.WEAPON_SLOT_ID;
import static skyxplore.testutil.TestUtils.createShip;

@RunWith(MockitoJUnitRunner.class)
public class NewCharacterGeneratorTest {
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
        when(idGenerator.generateRandomId()).thenReturn(CHARACTER_ID_1);
        when(config.getStartMoney()).thenReturn(CHARACTER_MONEY);
        //WHEN
        SkyXpCharacter result = underTest.createCharacter(USER_ID, CHARACTER_NAME);
        //THEN
        verify(idGenerator).generateRandomId();
        verify(config).getStartMoney();
        assertEquals(CHARACTER_ID_1, result.getCharacterId());
        assertEquals(CHARACTER_NAME, result.getCharacterName());
        assertEquals(USER_ID, result.getUserId());
        assertEquals(CHARACTER_MONEY, result.getMoney());
        assertEquals(CHARACTER_ID_1, result.getCharacterId());
    }

    @Test
    public void testCreateShip() {
        //GIVEN
        when(idGenerator.generateRandomId()).thenReturn(EQUIPPED_SHIP_ID);

        Ship ship = createShip();
        ship.setConnector(6);
        when(shipService.get(STARTER_SHIP_ID)).thenReturn(ship);
        //WHEN
        EquippedShip result = underTest.createShip(CHARACTER_ID_1);
        //THEN
        verify(idGenerator).generateRandomId();
        verify(shipService).get(STARTER_SHIP_ID);
        assertEquals(EQUIPPED_SHIP_ID, result.getShipId());
        assertEquals(CHARACTER_ID_1, result.getCharacterId());
        assertEquals(STARTER_SHIP_ID, result.getShipType());
        assertEquals((Integer) 6, result.getConnectorSlot());
        assertEquals(DATA_SHIP_COREHULL, result.getCoreHull());
        assertTrue(result.getConnectorEquipped().contains(GENERATOR_ID));
        assertTrue(result.getConnectorEquipped().contains(BATTERY_ID));
        assertTrue(result.getConnectorEquipped().contains(STORAGE_ID));
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
        verify(idGenerator).generateRandomId();
        verify(shipService).get(STARTER_SHIP_ID);

        assertEquals((Integer) 4, result.getFrontSlot());
        assertEquals((Integer) 2, result.getLeftSlot());
        assertEquals((Integer) 2, result.getRightSlot());
        assertEquals((Integer) 2, result.getBackSlot());

        assertTrue(result.getFrontEquipped().contains(SHIELD_ID));
        assertTrue(result.getFrontEquipped().contains(ARMOR_ID));

        assertTrue(result.getLeftEquipped().contains(SHIELD_ID));
        assertTrue(result.getLeftEquipped().contains(ARMOR_ID));

        assertTrue(result.getRightEquipped().contains(SHIELD_ID));
        assertTrue(result.getRightEquipped().contains(ARMOR_ID));

        assertTrue(result.getBackEquipped().contains(SHIELD_ID));
        assertTrue(result.getBackEquipped().contains(ARMOR_ID));
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

        assertEquals((Integer) 3, result.getFrontSlot());
        assertEquals((Integer) 1, result.getLeftSlot());
        assertEquals((Integer) 1, result.getRightSlot());
        assertEquals((Integer) 1, result.getBackSlot());

        assertTrue(result.getFrontEquipped().contains(LASER_ID));
        assertTrue(result.getFrontEquipped().contains(LAUNCHER_ID));

        assertTrue(result.getLeftEquipped().contains(RIFLE_ID));
        assertTrue(result.getRightEquipped().contains(RIFLE_ID));
        assertTrue(result.getBackEquipped().contains(RIFLE_ID));
    }

    @Test
    public void testCreateFactoryShouldCreate() {
        //GIVEN
        when(idGenerator.generateRandomId()).thenReturn(FACTORY_ID_1);
        Set<String> materialIds = new HashSet<>(Arrays.asList(MATERIAL_ID));
        when(materialService.keySet()).thenReturn(materialIds);
        when(config.getStartMaterials()).thenReturn(MATERIAL_MATERIAL_AMOUNT);
        //WHEN
        Factory result = underTest.createFactory(CHARACTER_ID_1);
        //THEN
        verify(idGenerator).generateRandomId();
        verify(materialService).keySet();
        verify(config).getStartMaterials();
        assertEquals(FACTORY_ID_1, result.getFactoryId());
        assertEquals(CHARACTER_ID_1, result.getCharacterId());
        assertTrue(result.getMaterials().containsKey(MATERIAL_ID));
        assertEquals(MATERIAL_MATERIAL_AMOUNT, result.getMaterials().get(MATERIAL_ID));
    }
}