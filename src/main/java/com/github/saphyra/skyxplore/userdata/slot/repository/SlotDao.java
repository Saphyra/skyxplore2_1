package com.github.saphyra.skyxplore.userdata.slot.repository;

import com.github.saphyra.converter.Converter;
import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.userdata.slot.domain.EquippedSlot;
import lombok.extern.slf4j.Slf4j;
import com.github.saphyra.skyxplore.common.event.ShipDeletedEvent;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SlotDao extends AbstractDao<SlotEntity, EquippedSlot, String, SlotRepository> {

    public SlotDao(Converter<SlotEntity, EquippedSlot> converter, SlotRepository repository) {
        super(converter, repository);
    }

    @EventListener
    void deleteByShipId(ShipDeletedEvent event) {
        log.info("Deleting slots of {}", event);
        repository.deleteByShipId(event.getShipId());
    }
}
