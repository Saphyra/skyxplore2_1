package skyxplore.dataaccess.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.domain.Converter;
import skyxplore.domain.slot.SlotConverter;
import skyxplore.dataaccess.db.repository.SlotRepository;
import skyxplore.domain.slot.EquippedSlot;
import skyxplore.domain.slot.SlotEntity;

import javax.transaction.Transactional;

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
