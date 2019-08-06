package com.github.saphyra.skyxplore.userdata.factory.impl.addtoqueue;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.skyxplore.common.exception.NotEnoughMoneyException;
import com.github.saphyra.skyxplore.userdata.factory.AddToQueueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.saphyra.skyxplore.userdata.character.CharacterQueryService;
import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.userdata.character.repository.CharacterDao;
import com.github.saphyra.skyxplore.userdata.factory.FactoryQueryService;
import com.github.saphyra.skyxplore.userdata.factory.domain.AddToQueueRequest;
import com.github.saphyra.skyxplore.userdata.factory.domain.Factory;
import com.github.saphyra.skyxplore.data.GameDataFacade;
import com.github.saphyra.skyxplore.data.entity.FactoryData;
import com.github.saphyra.skyxplore.userdata.product.ProductFacade;
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
