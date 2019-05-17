package com.github.saphyra.skyxplore.ship;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.ship.domain.SlotView;
import com.github.saphyra.skyxplore.slot.domain.EquippedSlot;

@RunWith(MockitoJUnitRunner.class)
public class SlotViewConverterTest {
    private static final int FRONT_SLOT = 1;
    private static final int LEFT_SLOT = 2;
    private static final int RIGHT_SLOT = 3;
    private static final int BACK_SLOT = 4;
    private static final String FRONT_EQUIPPED = "front_equipped";
    private static final String LEFT_EQUIPPED = "left_equipped";
    private static final String RIGHT_EQUIPPED = "right_equipped";
    private static final String BACK_EQUIPPED = "back_equipped";

    @InjectMocks
    private SlotViewConverter underTest;

    @Test
    public void tesConvertShouldReturnView() {
        EquippedSlot slot = EquippedSlot.builder()
            .slotId("")
            .shipId("")
            .frontSlot(FRONT_SLOT)
            .leftSlot(LEFT_SLOT)
            .rightSlot(RIGHT_SLOT)
            .backSlot(BACK_SLOT)
            .frontEquipped(Arrays.asList(FRONT_EQUIPPED))
            .leftEquipped(Arrays.asList(LEFT_EQUIPPED))
            .rightEquipped(Arrays.asList(RIGHT_EQUIPPED))
            .backEquipped(Arrays.asList(BACK_EQUIPPED))
            .build();
        //WHEN
        SlotView result = underTest.convertDomain(slot);
        //THEN
        assertThat(result.getFrontSlot()).isEqualTo(FRONT_SLOT);
        assertThat(result.getLeftSlot()).isEqualTo(LEFT_SLOT);
        assertThat(result.getRightSlot()).isEqualTo(RIGHT_SLOT);
        assertThat(result.getBackSlot()).isEqualTo(BACK_SLOT);

        assertThat(result.getFrontEquipped()).containsExactly(FRONT_EQUIPPED);
        assertThat(result.getLeftEquipped()).containsExactly(LEFT_EQUIPPED);
        assertThat(result.getRightEquipped()).containsExactly(RIGHT_EQUIPPED);
        assertThat(result.getBackEquipped()).containsExactly(BACK_EQUIPPED);
    }
}
