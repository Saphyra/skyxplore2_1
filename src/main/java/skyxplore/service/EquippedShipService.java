package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.character.CharacterDao;
import skyxplore.dataaccess.equippedship.EquippedShipDao;
import skyxplore.dataaccess.gamedata.GameDataService;
import skyxplore.exception.InvalidAccessException;
import skyxplore.exception.ShipNotFoundException;
import skyxplore.restcontroller.view.EquipmentView;
import skyxplore.restcontroller.view.ShipView;
import skyxplore.restcontroller.view.converter.ShipViewConverter;
import skyxplore.service.domain.EquippedShip;
import skyxplore.service.domain.SkyXpCharacter;

@Service
@Slf4j
@RequiredArgsConstructor
public class EquippedShipService {
    private  final EquippedShipDao equippedShipDao;
    private final CharacterDao characterDao;
    private final GameDataService gameDataService;
    private final ShipViewConverter shipViewConverter;

    public EquipmentView<ShipView> getShipData(Long characterId, Long userId){
        SkyXpCharacter character = characterDao.findById(characterId);
        if(!character.getUser().getUserId().equals(userId)){
            throw new InvalidAccessException("Character with Id " + characterId + " cannot be accessed by user " + userId);
        }

        EquippedShip ship = equippedShipDao.getShipByCharacterId(characterId);
        if(ship == null){
            throw new ShipNotFoundException("No ship found with characterId " + characterId);
        }

        EquipmentView<ShipView> result = new EquipmentView<>();
        result.setInfo(shipViewConverter.convertDomain(ship));
        result.setData(gameDataService.collectEquipmentData(ship));
        return result;
    }
}
