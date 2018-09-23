package skyxplore.domain.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.FACTORY_ID_1;
import static skyxplore.testutil.TestUtils.FACTORY_MATERIALS;
import static skyxplore.testutil.TestUtils.createFactory;
import static skyxplore.testutil.TestUtils.createFactoryEntity;
import static skyxplore.testutil.TestUtils.createMaterials;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import skyxplore.domain.materials.Materials;
import skyxplore.domain.materials.MaterialsConverter;

@RunWith(MockitoJUnitRunner.class)
public class FactoryConverterTest {
    @Mock
    private MaterialsConverter materialsConverter;

    @InjectMocks
    private FactoryConverter underTest;

    @Test
    public void testConvertEntityShouldReturnNull(){
        //GIVEN
        FactoryEntity entity = null;
        //WHEN
        Factory result = underTest.convertEntity(entity);
        //THEN
        assertNull(result);
    }

    @Test
    public void testConvertEntityShouldConvert(){
        //GIVEN
        FactoryEntity entity = createFactoryEntity();

        Materials materials = createMaterials();
        when(materialsConverter.convertEntity(FACTORY_MATERIALS, FACTORY_ID_1)).thenReturn(materials);
        //WHEN
        Factory result = underTest.convertEntity(entity);
        //THEN
        assertEquals(FACTORY_ID_1, result.getFactoryId());
        assertEquals(CHARACTER_ID_1, result.getCharacterId());
        assertEquals(materials, result.getMaterials());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertDomainShouldThrowExceptionWhenNull(){
        //GIVEN
        Factory factory = null;
        //WHEN
        underTest.convertDomain(factory);
    }

    @Test
    public void testConvertDomainShouldConvert(){
        //GIVEN
        Factory factory = createFactory();
        when(materialsConverter.convertDomain(factory.getMaterials(), FACTORY_ID_1)).thenReturn(FACTORY_MATERIALS);
        //WHEN
        FactoryEntity result = underTest.convertDomain(factory);
        //THEN
        assertEquals(FACTORY_ID_1, result.getFactoryId());
        assertEquals(CHARACTER_ID_1, result.getCharacterId());
        assertEquals(FACTORY_MATERIALS, result.getMaterials());
    }
}
