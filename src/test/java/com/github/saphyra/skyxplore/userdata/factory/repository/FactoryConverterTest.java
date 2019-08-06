package com.github.saphyra.skyxplore.userdata.factory.repository;

import com.github.saphyra.skyxplore.userdata.factory.domain.Factory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import com.github.saphyra.skyxplore.userdata.factory.domain.Materials;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FactoryConverterTest {
    private static final String FACTORY_ID = "factory_id";
    private static final String MATERIALS = "materials";
    private static final String CHARACTER_ID = "character_id";

    @Mock
    private MaterialsConverter materialsConverter;

    @InjectMocks
    private FactoryConverter underTest;

    @Test
    public void testConvertEntityShouldReturnNull() {
        //GIVEN
        FactoryEntity entity = null;
        //WHEN
        Factory result = underTest.convertEntity(entity);
        //THEN
        assertThat(result).isNull();
    }

    @Test
    public void testConvertEntityShouldConvert() {
        //GIVEN
        FactoryEntity entity = FactoryEntity.builder()
            .factoryId(FACTORY_ID)
            .characterId(CHARACTER_ID)
            .materials(MATERIALS)
            .build();

        Materials materials = new Materials();
        when(materialsConverter.convertEntity(MATERIALS, FACTORY_ID)).thenReturn(materials);
        //WHEN
        Factory result = underTest.convertEntity(entity);
        //THEN
        assertThat(result.getFactoryId()).isEqualTo(FACTORY_ID);
        assertThat(result.getCharacterId()).isEqualTo(CHARACTER_ID);
        assertThat(result.getMaterials()).isEqualTo(materials);
    }

    @Test
    public void testConvertDomainShouldConvert() {
        //GIVEN
        Factory factory = Factory.builder()
            .characterId(CHARACTER_ID)
            .factoryId(FACTORY_ID)
            .materials(new Materials())
            .build();
        when(materialsConverter.convertDomain(factory.getMaterials(), FACTORY_ID)).thenReturn(MATERIALS);
        //WHEN
        FactoryEntity result = underTest.convertDomain(factory);
        //THEN
        assertThat(result.getFactoryId()).isEqualTo(FACTORY_ID);
        assertThat(result.getCharacterId()).isEqualTo(CHARACTER_ID);
        assertThat(result.getMaterials()).isEqualTo(MATERIALS);
    }
}
