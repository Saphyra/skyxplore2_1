package skyxplore.domain.character;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.domain.ConverterBase;

@Component
@RequiredArgsConstructor
public class CharacterConverter extends ConverterBase<CharacterEntity, SkyXpCharacter> {

    @Override
    public SkyXpCharacter convertEntity(CharacterEntity entity){
        if(entity == null){
            return null;
        }
        SkyXpCharacter domain = new SkyXpCharacter();
        domain.setCharacterId(entity.getCharacterId());
        domain.setCharacterName(entity.getCharacterName());
        domain.setShipId(entity.getShipId());
        domain.setUserId(entity.getUserId());
        domain.setMoney(entity.getMoney());
        domain.setEquipments(entity.getEquipments());
        return domain;
    }

    @Override
    public CharacterEntity convertDomain(SkyXpCharacter domain){
        CharacterEntity entity = new CharacterEntity();
        entity.setCharacterId(domain.getCharacterId());
        entity.setCharacterName(domain.getCharacterName());
        entity.setUserId(domain.getUserId());
        entity.setShipId(domain.getShipId());
        entity.setMoney(domain.getMoney());
        entity.setEquipments(domain.getEquipments());
        return entity;
    }
}
