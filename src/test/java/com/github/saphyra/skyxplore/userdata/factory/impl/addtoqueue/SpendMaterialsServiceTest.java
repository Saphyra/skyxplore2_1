package com.github.saphyra.skyxplore.userdata.factory.impl.addtoqueue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.userdata.factory.domain.Factory;
import com.github.saphyra.skyxplore.userdata.factory.domain.Materials;
import com.github.saphyra.skyxplore.userdata.factory.repository.FactoryDao;
import com.github.saphyra.skyxplore.data.entity.FactoryData;

@RunWith(MockitoJUnitRunner.class)
public class SpendMaterialsServiceTest {
    private static final String MATERIAL_1 = "material_1";
    private static final String MATERIAL_2 = "material_2";
    private static final Integer AMOUNT = 2;

    @Mock
    private FactoryDao factoryDao;

    @Mock
    private FactoryData factoryData;

    @InjectMocks
    private SpendMaterialsService underTest;

    @Mock
    private Factory factory;

    @Test
    public void spendMaterials(){
        //GIVEN
        Materials materials = new Materials();
        materials.addMaterial(MATERIAL_1, 10);
        materials.addMaterial(MATERIAL_2, 8);
        given(factory.getMaterials()).willReturn(materials);


        HashMap<String, Integer> requiredMaterials = new HashMap<>();
        requiredMaterials.put(MATERIAL_1, 4);
        requiredMaterials.put(MATERIAL_2, 4);
        given(factoryData.getMaterials()).willReturn(requiredMaterials);
        //WHEN
        underTest.spendMaterials(factory, factoryData, AMOUNT);
        //THEN
        verify(factoryDao).save(factory);
        assertThat(materials.get(MATERIAL_1)).isEqualTo(2);
        assertThat(materials.get(MATERIAL_2)).isEqualTo(0);
    }
}