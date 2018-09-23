package skyxplore.controller;

import com.google.common.cache.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import skyxplore.controller.request.OneStringParamRequest;
import skyxplore.controller.request.character.CharacterDeleteRequest;
import skyxplore.controller.request.character.CreateCharacterRequest;
import skyxplore.controller.request.character.RenameCharacterRequest;
import skyxplore.controller.view.View;
import skyxplore.controller.view.character.CharacterView;
import skyxplore.controller.view.character.CharacterViewConverter;
import skyxplore.controller.view.equipment.EquipmentViewList;
import skyxplore.service.CharacterFacade;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static skyxplore.filter.FilterHelper.COOKIE_CHARACTER_ID;
import static skyxplore.filter.FilterHelper.COOKIE_USER_ID;

@SuppressWarnings({"UnstableApiUsage", "WeakerAccess"})
@RestController
@RequiredArgsConstructor
@Slf4j
public class CharacterController {
    private static final String BUY_EQUIPMENTS_MAPPING = "character/equipment";
    private static final String CREATE_CHARACTER_MAPPING = "character";
    private static final String DELETE_CHARACTER_MAPPING = "character";
    private static final String GET_CHARACTERS_MAPPING = "character/characters";
    private static final String GET_EQUIPMENTS_OF_CHARACTER = "character/equipment";
    private static final String GET_MONEY_OF_CHARACTER_MAPPING = "character/money";
    private static final String IS_CHAR_NAME_EXISTS_MAPPING = "character/name/exists";
    private static final String RENAME_CHARACTER_MAPPING = "character/rename";

    private final CharacterFacade characterFacade;
    private final CharacterViewConverter characterViewConverter;
    private final Cache<String, Boolean> characterNameCache;

    @PutMapping(BUY_EQUIPMENTS_MAPPING)
    public void buyEquipments(
        @RequestBody HashMap<String, Integer> items,
        @CookieValue(COOKIE_CHARACTER_ID) String characterId
    ){
        log.info("{} wants to buy {}",characterId, items.toString());
        characterFacade.buyItems(items, characterId);
        log.info("Items are bought successfully.");
    }

    @PutMapping(CREATE_CHARACTER_MAPPING)
    public void createCharacter(
        @RequestBody @Valid CreateCharacterRequest request,
        @CookieValue(value = COOKIE_USER_ID) String userId
    ) {
        log.info("Creating new character with name {}", request.getCharacterName());
        characterFacade.createCharacter(request, userId);
        log.info("Character created successfully.");
        characterNameCache.invalidate(request.getCharacterName());
    }

    @DeleteMapping(DELETE_CHARACTER_MAPPING)
    public void deleteCharacter(
        @RequestBody @NotNull CharacterDeleteRequest request,
        @CookieValue(value = COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to deleteById {}", userId, request.getCharacterId());
        characterFacade.deleteCharacter(request, userId);
        log.info("Character {} is deleted.", request.getCharacterId());
    }

    @GetMapping(GET_CHARACTERS_MAPPING)
    public List<CharacterView> getCharacters(@CookieValue(value = COOKIE_USER_ID) String userId) {
        log.info("{} wants to know his character list.", userId);
        return characterViewConverter.convertDomain(characterFacade.getCharactersByUserId(userId));
    }

    @GetMapping(GET_EQUIPMENTS_OF_CHARACTER)
    public View<EquipmentViewList> getEquipmentsOfCharacter(
        @CookieValue(COOKIE_CHARACTER_ID) String characterId
        ) {
        log.info("{} wants to know his equipments.", characterId);
        return characterFacade.getEquipmentsOfCharacter(characterId);
    }

    @GetMapping(GET_MONEY_OF_CHARACTER_MAPPING)
    public Integer getMoney(
        @CookieValue(COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to know his money.", characterId);
        return characterFacade.getMoneyOfCharacter(characterId);
    }

    @GetMapping(IS_CHAR_NAME_EXISTS_MAPPING)
    public boolean isCharNameExists(@RequestBody @Valid OneStringParamRequest request) throws ExecutionException {
        log.info("Someone wants to know if character with name {} is exists.", request);
        return characterNameCache.get(request.getValue());
    }

    @PostMapping(RENAME_CHARACTER_MAPPING)
    public void renameCharacter(
        @RequestBody @Valid RenameCharacterRequest request,
        @CookieValue(value = COOKIE_USER_ID) String userId) {
        log.info("{} wants to rename character {}", userId, request);
        characterFacade.renameCharacter(request, userId);
        characterNameCache.invalidate(request.getNewCharacterName());
        log.info("Character renamed successfully.");
    }
}
