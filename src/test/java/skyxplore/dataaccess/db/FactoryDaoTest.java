package skyxplore.dataaccess.db;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.FACTORY_ID_1;
import static skyxplore.testutil.TestUtils.createFactory;
import static skyxplore.testutil.TestUtils.createFactoryEntity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import skyxplore.dataaccess.db.repository.FactoryRepository;
import skyxplore.domain.factory.Factory;
import skyxplore.domain.factory.FactoryConverter;
import skyxplore.domain.factory.FactoryEntity;

@RunWith(MockitoJUnitRunner.class)
public class FactoryDaoTest {
    @Mock
    private FactoryRepository factoryRepository;

    @Mock
    private FactoryConverter factoryConverter;

    @Mock
    private ProductDao productDao;

    @InjectMocks
    private FactoryDao underTest;

    @Test
    public void testDeleteByCharacterIdShouldCallDeleteMethods(){
        //GIVEN
        FactoryEntity factoryEntity = createFactoryEntity();
        when(factoryRepository.findByCharacterId(CHARACTER_ID_1)).thenReturn(factoryEntity);
        //WHEN
        underTest.deleteByCharacterId(CHARACTER_ID_1);
        //THEN
        verify(productDao).deleteByFactoryId(FACTORY_ID_1);
        verify(factoryRepository).deleteByCharacterId(CHARACTER_ID_1);
    }

    @Test
    public void testFindByCharacterIdShouldCallRepositoryAndReturnDomain(){
        //GIVEN
        FactoryEntity entity = createFactoryEntity();
        when(factoryRepository.findByCharacterId(CHARACTER_ID_1)).thenReturn(entity);

        Factory factory = createFactory();
        when(factoryConverter.convertEntity(entity)).thenReturn(factory);
        //WHEN
        Factory result = underTest.findByCharacterId(CHARACTER_ID_1);
        //THEN
        verify(factoryRepository).findByCharacterId(CHARACTER_ID_1);
        verify(factoryConverter).convertEntity(entity);
        assertEquals(factory, result);
    }
}
