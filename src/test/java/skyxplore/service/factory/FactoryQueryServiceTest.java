package skyxplore.service.factory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.dataaccess.db.FactoryDao;
import skyxplore.domain.factory.Factory;
import skyxplore.exception.FactoryNotFoundException;

import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.FACTORY_ID_1;
import static skyxplore.testutil.TestUtils.MATERIAL_AMOUNT;
import static skyxplore.testutil.TestUtils.MATERIAL_KEY;
import static skyxplore.testutil.TestUtils.createFactory;

@RunWith(MockitoJUnitRunner.class)
public class FactoryQueryServiceTest {
    @Mock
    private FactoryDao factoryDao;

    @InjectMocks
    private FactoryQueryService underTest;

    @Test(expected = FactoryNotFoundException.class)
    public void testFindFactoryOfCharacterValidatedShouldThrowExceptionWhenNotFound() {
        //GIVEN
        when(factoryDao.findByCharacterId(CHARACTER_ID_1)).thenReturn(null);
        //WHEN
        underTest.findFactoryOfCharacterValidated(CHARACTER_ID_1);
    }

    @Test
    public void testFindFactoryOfCharacterValidatedShouldReturn() {
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
    public void testGetMaterialsShouldReturn() {
        //GIVEN
        Factory factory = createFactory();
        when(factoryDao.findByCharacterId(CHARACTER_ID_1)).thenReturn(factory);

        //WHEN
        Map<String, Integer> result = underTest.getMaterials(CHARACTER_ID_1);
        //THEN
        verify(factoryDao).findByCharacterId(CHARACTER_ID_1);
        assertEquals(1, result.size());
        assertTrue(result.containsKey(MATERIAL_KEY));
        assertEquals(result.get(MATERIAL_KEY), MATERIAL_AMOUNT);
    }

    @Test
    public void testGetFactoryIdOfCharacterShouldReturn() {
        //GIVEN
        Factory factory = createFactory();
        when(factoryDao.findByCharacterId(CHARACTER_ID_1)).thenReturn(factory);
        //WHEN
        assertEquals(FACTORY_ID_1, underTest.getFactoryIdOfCharacter(CHARACTER_ID_1));
    }

    @Test(expected = FactoryNotFoundException.class)
    public void testFindByFactoryIdShouldThrowExceptionWhenNotFound() {
        //GIVEN
        when(factoryDao.findById(FACTORY_ID_1)).thenReturn(Optional.empty());
        //WHEN
        underTest.findByFactoryId(FACTORY_ID_1);
    }

    @Test
    public void testFindByFactoryIdShouldReturn() {
        //GIVEN
        Factory factory = createFactory();
        when(factoryDao.findById(FACTORY_ID_1)).thenReturn(Optional.of(factory));
        //WHEN
        Factory result = underTest.findByFactoryId(FACTORY_ID_1);
        //THEN
        verify(factoryDao).findById(FACTORY_ID_1);
        assertEquals(factory, result);
    }
}