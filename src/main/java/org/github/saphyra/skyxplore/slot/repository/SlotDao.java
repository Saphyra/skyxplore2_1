package org.github.saphyra.skyxplore.slot.repository;

import javax.transaction.Transactional;

import org.github.saphyra.skyxplore.slot.domain.EquippedSlot;
import org.springframework.stereotype.Component;

import com.github.saphyra.converter.Converter;
import com.github.saphyra.dao.AbstractDao;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SlotDao extends AbstractDao<SlotEntity, EquippedSlot, String, SlotRepository> {

    public SlotDao(Converter<SlotEntity, EquippedSlot> converter, SlotRepository repository) {
        super(converter, repository);
    }

    @Transactional
    public void deleteByShipId(String shipId) {
        log.info("Deleting slots of {}", shipId);
        repository.deleteByShipId(shipId);
    }

    public EquippedSlot getById(String slotId) {
        return converter.convertEntity(repository.getOne(slotId));
    }
}
