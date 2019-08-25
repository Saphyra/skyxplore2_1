package com.github.saphyra.skyxplore.userdata.factory.impl.addtoqueue;

import com.github.saphyra.skyxplore.userdata.factory.domain.Materials;
import com.github.saphyra.skyxplore.data.entity.FactoryData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class MaterialsValidatorTest {
    private static final int AMOUNT = 2;
    private static final String MATERIAL_1 = "material_1";
    private static final String MATERIAL_2 = "material_2";

    @InjectMocks
    private MaterialsValidator underTest;

    @Mock
    private FactoryData factoryData;

    @Test(expected = NotEnoughMaterialsException.class)
    public void validateMaterials_notEnoughMaterials(){
        //GIVEN
        Materials materials = new Materials();
        materials.addMaterial(MATERIAL_1, 10);
        materials.addMaterial(MATERIAL_2, 2);

        HashMap<String, Integer> requiredMaterials = new HashMap<>();
        requiredMaterials.put(MATERIAL_1, 5);
        requiredMaterials.put(MATERIAL_2, 3);
        given(factoryData.getMaterials()).willReturn(requiredMaterials);
        //WHEN
        underTest.validateMaterials(materials, factoryData, AMOUNT);
    }

    @Test
    public void validateMaterials(){
        //GIVEN
        Materials materials = new Materials();
        materials.addMaterial(MATERIAL_1, 10);
        materials.addMaterial(MATERIAL_2, 6);

        HashMap<String, Integer> requiredMaterials = new HashMap<>();
        requiredMaterials.put(MATERIAL_1, 5);
        requiredMaterials.put(MATERIAL_2, 3);
        given(factoryData.getMaterials()).willReturn(requiredMaterials);
        //WHEN
        underTest.validateMaterials(materials, factoryData, AMOUNT);
    }
}