package skyxplore.dataaccess.db.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.converter.SlotConverter;
import skyxplore.dataaccess.db.repository.SlotRepository;
import skyxplore.service.domain.EquippedSlot;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Component
@Slf4j
public class SlotDao {
    private final SlotRepository slotRepository;
    private final SlotConverter slotConverter;

    @Transactional
    public void deleteByShipId(String shipId){
        log.info("Deleting slots of {}", shipId);
        slotRepository.deleteByShipId(shipId);
    }

    public EquippedSlot getById(String slotId){
        return slotConverter.convertEntity(slotRepository.getOne(slotId));
    }

    public void save(EquippedSlot slot){
        slotRepository.save(slotConverter.convertDomain(slot));
    }
}
