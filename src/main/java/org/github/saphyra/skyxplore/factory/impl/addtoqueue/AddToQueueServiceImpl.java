package org.github.saphyra.skyxplore.factory.impl.addtoqueue;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.character.CharacterQueryService;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.character.repository.CharacterDao;
import org.github.saphyra.skyxplore.common.exception.NotEnoughMoneyException;
import org.github.saphyra.skyxplore.factory.AddToQueueService;
import org.github.saphyra.skyxplore.factory.FactoryQueryService;
import org.github.saphyra.skyxplore.factory.domain.AddToQueueRequest;
import org.github.saphyra.skyxplore.factory.domain.Factory;
import org.github.saphyra.skyxplore.gamedata.GameDataFacade;
import org.github.saphyra.skyxplore.gamedata.entity.FactoryData;
import org.github.saphyra.skyxplore.product.ProductFacade;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
class AddToQueueServiceImpl implements AddToQueueService {
    private final CharacterDao characterDao;
    private final CharacterQueryService characterQueryService;
    private final FactoryQueryService factoryQueryService;
    private final GameDataFacade gameDataFacade;
    private final MaterialsValidator materialsValidator;
    private final ProductFacade productFacade;
    private final SpendMaterialsService spendMaterialsService;

    @Transactional
    @Override
    public void addToQueue(String characterId, AddToQueueRequest request) {
        SkyXpCharacter character = characterQueryService.findByCharacterId(characterId);
        Factory factory = factoryQueryService.findFactoryOfCharacterValidated(characterId);
        FactoryData elementData = gameDataFacade.getFactoryData(request.getElementId());

        if(!elementData.isBuildable()){
            throw new BadRequestException("Element with id " + request.getElementId() + " is not buildable.");
        }

        int price = elementData.getBuildPrice() * request.getAmount();
        if (character.getMoney() < price) {
            throw new NotEnoughMoneyException("Not enough money. Needed: " + price + ", have: " + character.getMoney());
        }
        materialsValidator.validateMaterials(factory.getMaterials(), elementData, request.getAmount());

        productFacade.createAndSave(factory.getFactoryId(), elementData, request.getAmount());
        character.spendMoney(price);
        characterDao.save(character);

        spendMaterialsService.spendMaterials(factory, elementData, request.getAmount());
    }
}
