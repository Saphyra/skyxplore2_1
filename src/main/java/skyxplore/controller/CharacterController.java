package skyxplore.controller;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.cache.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.request.CharacterDeleteRequest;
import skyxplore.controller.request.CreateCharacterRequest;
import skyxplore.controller.request.RenameCharacterRequest;
import skyxplore.controller.view.View;
import skyxplore.controller.view.character.CharacterView;
import skyxplore.controller.view.character.CharacterViewConverter;
import skyxplore.controller.view.equipment.EquipmentViewList;
import skyxplore.filter.AuthFilter;
import skyxplore.service.CharacterFacade;

@SuppressWarnings({"UnstableApiUsage", "WeakerAccess"})
@RestController
@RequiredArgsConstructor
@Slf4j
public class CharacterController {
    private static final String BUY_EQUIPMENTS_MAPPING = "character/equipment/{characterId}";
    private static final String CREATE_CHARACTER_MAPPING = "character";
    private static final String DELETE_CHARACTER_MAPPING = "character";
    private static final String GET_CHARACTERS_MAPPING = "character/characters";
    private static final String GET_EQUIPMENTS_OF_CHARACTER = "character/equipment/{characterId}";
    private static final String GET_MONEY_OF_CHARACTER_MAPPING = "character/money/{characterId}";
    private static final String IS_CHAR_NAME_EXISTS_MAPPING = "character/ischarnameexists/{charName}";
    private static final String RENAME_CHARACTER_MAPPING = "character/rename";

    private final CharacterFacade characterFacade;
    private final CharacterViewConverter characterViewConverter;
    private final Cache<String, Boolean> characterNameCache;

    @PutMapping(BUY_EQUIPMENTS_MAPPING)
    public void buyEquipments(
        @RequestBody HashMap<String, Integer> items,
        @PathVariable(name = "characterId") String characterId,
        @CookieValue(value = AuthFilter.COOKIE_USER_ID) String userId) {
        log.info("{} wants to buy {} for character {}", userId, items.toString(), characterId);
        characterFacade.buyItems(items, characterId, userId);
        log.info("Items are bought successfully.");
    }

    @PutMapping(CREATE_CHARACTER_MAPPING)
    public void createCharacter(
        @RequestBody @Valid CreateCharacterRequest request,
        @CookieValue(value = AuthFilter.COOKIE_USER_ID) String userId
    ) {
        log.info("Creating new character with name {}", request.getCharacterName());
        characterFacade.createCharacter(request, userId);
        log.info("Character created successfully.");
        characterNameCache.invalidate(request.getCharacterName());
    }

    @DeleteMapping(DELETE_CHARACTER_MAPPING)
    public void deleteCharacter(
        @RequestBody @NotNull CharacterDeleteRequest request,
        @CookieValue(value = AuthFilter.COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to delete {}", userId, request.getCharacterId());
        characterFacade.deleteCharacter(request, userId);
        log.info("Character {} is deleted.", request.getCharacterId());
    }

    @GetMapping(GET_CHARACTERS_MAPPING)
    public List<CharacterView> getCharacters(@CookieValue(value = AuthFilter.COOKIE_USER_ID) String userId) {
        log.info("{} wants to know his character list.", userId);
        return characterViewConverter.convertDomain(characterFacade.getCharactersByUserId(userId));
    }

    @GetMapping(GET_EQUIPMENTS_OF_CHARACTER)
    public View<EquipmentViewList> getEquipmentsOfCharacter(
        @PathVariable @NotNull String characterId,
        @CookieValue(AuthFilter.COOKIE_USER_ID) String userId) {
        log.info("{} wants to know the equipment list of character {}", userId, characterId);
        return characterFacade.getEquipmentsOfCharacter(userId, characterId);
    }

    @GetMapping(GET_MONEY_OF_CHARACTER_MAPPING)
    public Integer getMoney(
        @PathVariable(name = "characterId") String characterId,
        @CookieValue(value = AuthFilter.COOKIE_USER_ID) String userId) {
        log.info("{} Queriing money of character {}", userId, characterId);
        return characterFacade.getMoneyOfCharacter(userId, characterId);
    }

    @GetMapping(IS_CHAR_NAME_EXISTS_MAPPING)
    public boolean isCharNameExists(@PathVariable String charName) throws ExecutionException {
        log.info("Someone wants to know if character with name {} is exists.", charName);
        return characterNameCache.get(charName);
    }

    @PostMapping(RENAME_CHARACTER_MAPPING)
    public void renameCharacter(
        @RequestBody @Valid RenameCharacterRequest request,
        @CookieValue(value = AuthFilter.COOKIE_USER_ID) String userId) {
        log.info("{} wants to rename character {}", userId, request);
        characterFacade.renameCharacter(request, userId);
        characterNameCache.invalidate(request.getNewCharacterName());
        log.info("Character renamed successfully.");
    }
}
