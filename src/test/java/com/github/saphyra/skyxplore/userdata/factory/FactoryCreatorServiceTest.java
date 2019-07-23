package com.github.saphyra.skyxplore.userdata.factory;

import com.github.saphyra.util.IdGenerator;
import com.github.saphyra.skyxplore.userdata.factory.domain.Factory;
import com.github.saphyra.skyxplore.userdata.factory.repository.FactoryDao;
import com.github.saphyra.skyxplore.data.subservice.MaterialService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FactoryCreatorServiceTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String FACTORY_ID = "factory_id";
    private static final String MATERIAL_ID_1 = "material_id_1";
    private static final String MATERIAL_ID_2 = "material_id_2";
    private static final Integer START_MATERIALS = 3;

    @Mock
    private FactoryConfig config;

    @Mock
    private FactoryDao factoryDao;

    @Mock
    private IdGenerator idGenerator;

    @Mock
    private MaterialService materialService;

    @InjectMocks
    private FactoryCreatorService underTest;

    @Test
    public void createFactory() {
        //GIVEN
        given(idGenerator.generateRandomId()).willReturn(FACTORY_ID);
        given(config.getStartMaterials()).willReturn(START_MATERIALS);

        Set<String> materialIds = new HashSet<>();
        materialIds.add(MATERIAL_ID_1);
        materialIds.add(MATERIAL_ID_2);
        given(materialService.keySet()).willReturn(materialIds);
        //WHEN
        underTest.createFactory(CHARACTER_ID);
        //THEN
        ArgumentCaptor<Factory> argumentCaptor = ArgumentCaptor.forClass(Factory.class);
        verify(factoryDao).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getFactoryId()).isEqualTo(FACTORY_ID);
        assertThat(argumentCaptor.getValue().getCharacterId()).isEqualTo(CHARACTER_ID);
        assertThat(argumentCaptor.getValue().getMaterials().get(MATERIAL_ID_1)).isEqualTo(START_MATERIALS);
        assertThat(argumentCaptor.getValue().getMaterials().get(MATERIAL_ID_2)).isEqualTo(START_MATERIALS);
    }
}