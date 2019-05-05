package org.github.saphyra.skyxplore.slot;

import org.github.saphyra.skyxplore.common.exception.EquippedSlotNotFoundException;
import org.github.saphyra.skyxplore.slot.domain.EquippedSlot;
import org.github.saphyra.skyxplore.slot.repository.SlotDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class SlotQueryServiceTest {
    private static final String SLOT_ID = "slot_id";
    @Mock
    private SlotDao slotDao;

    @InjectMocks
    private SlotQueryService underTest;

    @Test(expected = EquippedSlotNotFoundException.class)
    public void findBySlotId_notFound(){
        //GIVEN
        given(slotDao.findById(SLOT_ID)).willReturn(Optional.empty());
        //WHEN
        underTest.findSlotById(SLOT_ID);
    }

    @Test
    public void findBySlotId(){
        //GIVEN
        EquippedSlot slot = EquippedSlot.builder().build();
        given(slotDao.findById(SLOT_ID)).willReturn(Optional.of(slot));
        //WHEN
        EquippedSlot result = underTest.findSlotById(SLOT_ID);
        //THEN
        assertThat(result).isEqualTo(slot);
    }

}