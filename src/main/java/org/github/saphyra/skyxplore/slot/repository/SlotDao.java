package org.github.saphyra.skyxplore.slot.repository;

import com.github.saphyra.converter.Converter;
import com.github.saphyra.dao.AbstractDao;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.event.ShipDeletedEvent;
import org.github.saphyra.skyxplore.slot.domain.EquippedSlot;
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

    public EquippedSlot getById(String slotId) {
        return converter.convertEntity(repository.getOne(slotId));
    }
}
