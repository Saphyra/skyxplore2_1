package skyxplore.service.character;

import org.github.saphyra.skyxplore.character.CharacterQueryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.github.saphyra.skyxplore.character.cache.CharacterNameCache;
import org.github.saphyra.skyxplore.character.domain.request.CreateCharacterRequest;
import org.github.saphyra.skyxplore.character.repository.CharacterDao;
import skyxplore.dataaccess.db.EquippedShipDao;
import skyxplore.dataaccess.db.FactoryDao;
import skyxplore.dataaccess.db.SlotDao;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import skyxplore.domain.factory.Factory;
import skyxplore.domain.ship.EquippedShip;
import skyxplore.domain.slot.EquippedSlot;
import skyxplore.exception.CharacterNameAlreadyExistsException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.CHARACTER_NAME;
import static skyxplore.testutil.TestUtils.DEFENSE_SLOT_ID;
import static skyxplore.testutil.TestUtils.EQUIPPED_SHIP_ID;
import static skyxplore.testutil.TestUtils.USER_ID;
import static skyxplore.testutil.TestUtils.WEAPON_SLOT_ID;
import static skyxplore.testutil.TestUtils.createCharacter;
import static skyxplore.testutil.TestUtils.createCreateCharacterRequest;
import static skyxplore.testutil.TestUtils.createEquippedDefenseSlot;
import static skyxplore.testutil.TestUtils.createEquippedShip;
import static skyxplore.testutil.TestUtils.createEquippedWeaponSlot;
import static skyxplore.testutil.TestUtils.createFactory;

@RunWith(MockitoJUnitRunner.class)
public class CharacterCreatorServiceTest {
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
        CreateCharacterRequest request = createCreateCharacterRequest();

        when(characterQueryService.isCharNameExists(CHARACTER_NAME)).thenReturn(true);
        //WHEN
        underTest.createCharacter(request, USER_ID);
    }

    @Test
    public void testCreateCharacterShouldCreateAdSave() {
        //GIVEN
        CreateCharacterRequest request = createCreateCharacterRequest();

        when(characterQueryService.isCharNameExists(CHARACTER_NAME)).thenReturn(false);

        SkyXpCharacter character = createCharacter();
        when(newCharacterGenerator.createCharacter(USER_ID, CHARACTER_NAME)).thenReturn(character);

        EquippedShip ship = createEquippedShip();
        ship.setDefenseSlotId(null);
        ship.setWeaponSlotId(null);
        when(newCharacterGenerator.createShip(CHARACTER_ID_1)).thenReturn(ship);

        EquippedSlot defenseSlot = createEquippedDefenseSlot();
        when(newCharacterGenerator.createDefenseSlot(EQUIPPED_SHIP_ID)).thenReturn(defenseSlot);

        EquippedSlot weaponSlot = createEquippedWeaponSlot();
        when(newCharacterGenerator.createWeaponSlot(EQUIPPED_SHIP_ID)).thenReturn(weaponSlot);

        Factory factory = createFactory();
        when(newCharacterGenerator.createFactory(CHARACTER_ID_1)).thenReturn(factory);
        //WHEN
        SkyXpCharacter result = underTest.createCharacter(request, USER_ID);
        //THEN
        verify(characterQueryService).isCharNameExists(CHARACTER_NAME);
        verify(newCharacterGenerator).createCharacter(USER_ID, CHARACTER_NAME);
        verify(newCharacterGenerator).createDefenseSlot(EQUIPPED_SHIP_ID);
        verify(newCharacterGenerator).createWeaponSlot(EQUIPPED_SHIP_ID);
        verify(newCharacterGenerator).createFactory(CHARACTER_ID_1);
        verify(characterDao).save(character);
        verify(equippedShipDao).save(ship);
        verify(slotDao).save(defenseSlot);
        verify(slotDao).save(weaponSlot);
        verify(factoryDao).save(factory);
        assertEquals(DEFENSE_SLOT_ID, ship.getDefenseSlotId());
        assertEquals(WEAPON_SLOT_ID, ship.getWeaponSlotId());
        assertEquals(character, result);
        verify(characterNameCache).invalidate(CHARACTER_NAME);
    }
}