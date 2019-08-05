package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.data.entity.Battery;
import com.github.saphyra.skyxplore.data.entity.Generator;
import com.github.saphyra.skyxplore.data.subservice.BatteryService;
import com.github.saphyra.skyxplore.data.subservice.GeneratorService;
import com.github.saphyra.skyxplore.game.game.domain.ship.EnergyDetails;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;

@RunWith(MockitoJUnitRunner.class)
public class EnergyDetailsFactoryTest {
    private static final String GENERATOR_ID = "generator_id";
    private static final String BATTERY_ID = "battery-id";
    private static final Integer BATTERY_CAPACITY = 56;
    private static final Integer GENERATOR_RECHARGE = 4235;
    @Mock
    private BatteryService batteryService;

    @Mock
    private GeneratorService generatorService;

    @InjectMocks
    private EnergyDetailsFactory underTest;

    @Mock
    private ShipEquipments shipEquipments;

    @Mock
    private Battery battery;

    @Mock
    private Generator generator;

    @Test
    public void create() {
        //GIVEN
        given(shipEquipments.getConnectorEquipped()).willReturn(Arrays.asList(BATTERY_ID, GENERATOR_ID));

        given(batteryService.get(BATTERY_ID)).willReturn(battery);
        given(battery.getCapacity()).willReturn(BATTERY_CAPACITY);

        given(generatorService.get(GENERATOR_ID)).willReturn(generator);
        given(generator.getEnergyRecharge()).willReturn(GENERATOR_RECHARGE);
        //WHEN
        EnergyDetails result = underTest.create(shipEquipments);
        //THEN
        assertThat(result.getCapacity()).isEqualTo(BATTERY_CAPACITY);
        assertThat(result.getActual()).isEqualTo(BATTERY_CAPACITY);
        assertThat(result.getRecharge()).isEqualTo(GENERATOR_RECHARGE);
    }
}