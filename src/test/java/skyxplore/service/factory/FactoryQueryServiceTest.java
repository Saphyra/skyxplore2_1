package skyxplore.service.factory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.controller.view.material.MaterialView;
import skyxplore.dataaccess.db.FactoryDao;
import skyxplore.dataaccess.gamedata.entity.Material;
import skyxplore.dataaccess.gamedata.subservice.MaterialService;
import skyxplore.domain.factory.Factory;
import skyxplore.exception.FactoryNotFoundException;
import skyxplore.service.character.CharacterQueryService;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class FactoryQueryServiceTest {
    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private FactoryDao factoryDao;

    @Mock
    private MaterialService materialService;

    @InjectMocks
    private FactoryQueryService underTest;

    @Test(expected = FactoryNotFoundException.class)
    public void testFindFactoryOfCharacterValidatedShouldThrowExceptionWhenNotFound(){
        //GIVEN
        when(factoryDao.findByCharacterId(CHARACTER_ID_1)).thenReturn(null);
        //WHEN
        underTest.findFactoryOfCharacterValidated(CHARACTER_ID_1);
    }

    @Test
    public void testFindFactoryOfCharacterValidatedShouldReturn(){
        //GIVEN
        Factory factory = createFactory();
        when(factoryDao.findByCharacterId(CHARACTER_ID_1)).thenReturn(factory);
        //WHEN
        Factory result = underTest.findFactoryOfCharacterValidated(CHARACTER_ID_1);
        //THEN
        verify(factoryDao).findByCharacterId(CHARACTER_ID_1);
        assertEquals(factory, result);
    }

    @Test
    public void testGetMaterialsShouldReturn(){
        //GIVEN
        Factory factory = createFactory();
        when(factoryDao.findByCharacterId(CHARACTER_ID_1)).thenReturn(factory);

        Set<String> keySet = new HashSet<>();
        keySet.add(MATERIAL_ID);
        when(materialService.keySet()).thenReturn(keySet);
        Material material = createMaterial();
        when(materialService.get(MATERIAL_ID)).thenReturn(material);
        //WHEN
        Map<String, MaterialView> result = underTest.getMaterials(CHARACTER_ID_1);
        //THEN
        verify(characterQueryService).findByCharacterId(CHARACTER_ID_1);
        verify(factoryDao).findByCharacterId(CHARACTER_ID_1);
        verify(materialService).get(MATERIAL_KEY);
        assertEquals(1, result.size());
        assertTrue(result.containsKey(MATERIAL_ID));
        assertEquals(MATERIAL_ID, result.get(MATERIAL_ID).getMaterialId());
        assertEquals(MATERIAL_NAME, result.get(MATERIAL_ID).getName());
        assertEquals(MATERIAL_DESCRIPTION, result.get(MATERIAL_ID).getDescription());
        assertEquals(MATERIAL_AMOUNT, result.get(MATERIAL_ID).getAmount());
    }

    @Test
    public void testGetFactoryIdOfCharacterShouldReturn(){
        //GIVEN
        Factory factory = createFactory();
        when(factoryDao.findByCharacterId(CHARACTER_ID_1)).thenReturn(factory);
        //WHEN
        assertEquals(FACTORY_ID_1, underTest.getFactoryIdOfCharacter(CHARACTER_ID_1));
    }
}