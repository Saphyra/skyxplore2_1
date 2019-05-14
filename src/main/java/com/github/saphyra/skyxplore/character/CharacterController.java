package com.github.saphyra.skyxplore.character;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.saphyra.skyxplore.character.cache.CharacterNameExistsCache;
import com.github.saphyra.skyxplore.character.domain.CreateCharacterRequest;
import com.github.saphyra.skyxplore.character.domain.RenameCharacterRequest;
import com.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.common.OneStringParamRequest;
import com.github.saphyra.skyxplore.common.domain.character.CharacterView;
import com.github.saphyra.skyxplore.common.domain.character.CharacterViewConverter;
import com.github.saphyra.skyxplore.filter.CustomFilterHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CharacterController {
    private static final String BUY_EQUIPMENTS_MAPPING = "character/equipment";
    private static final String CREATE_CHARACTER_MAPPING = "character";
    private static final String DELETE_CHARACTER_MAPPING = "character/{characterId}";
    private static final String GET_ACTIVE_CHARACTERS_BY_NAME_MAPPING = "character/active/name";
    private static final String GET_CHARACTER_ID_MAPPING = "character/id";
    private static final String GET_CHARACTERS_MAPPING = "character";
    private static final String GET_EQUIPMENTS_OF_CHARACTER = "character/storage";
    private static final String GET_MONEY_OF_CHARACTER_MAPPING = "character/money";
    private static final String IS_CHAR_NAME_EXISTS_MAPPING = "character/name";
    public static final String RENAME_CHARACTER_MAPPING = "character";
    private static final String SELECT_CHARACTER_MAPPING = "character/{characterId}";

    private final BuyItemService buyItemService;
    private final CharacterCreatorService characterCreatorService;
    private final CharacterDeleteService characterDeleteService;
    private final CharacterQueryService characterQueryService;
    private final CharacterRenameService characterRenameService;
    private final CharacterSelectService characterSelectService;
    private final CharacterViewConverter characterViewConverter;
    private final CharacterViewQueryService characterViewQueryService;
    private final CharacterNameExistsCache characterNameExistsCache;

    @PostMapping(BUY_EQUIPMENTS_MAPPING)
    void buyEquipments(
        @RequestBody HashMap<String, Integer> items,
        @CookieValue(CustomFilterHelper.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to buy {}", characterId, items.toString());
        buyItemService.buyItems(items, characterId);
        log.info("Items are bought successfully.");
    }

    @PostMapping(CREATE_CHARACTER_MAPPING)
    CharacterView createCharacter(
        @RequestBody @Valid CreateCharacterRequest request,
        @CookieValue(value = CustomFilterHelper.COOKIE_USER_ID) String userId
    ) {
        log.info("Creating new character with name {}", request.getCharacterName());
        SkyXpCharacter character = characterCreatorService.createCharacter(request, userId);
        log.info("Character created successfully.");

        return characterViewConverter.convertDomain(character);
    }

    @DeleteMapping(DELETE_CHARACTER_MAPPING)
    void deleteCharacter(
        @PathVariable("characterId") String characterId,
        @CookieValue(value = CustomFilterHelper.COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to deleteById {}", userId, characterId);
        characterDeleteService.deleteCharacter(characterId, userId);
        log.info("Character {} is deleted.", characterId);
    }

    @PostMapping(GET_ACTIVE_CHARACTERS_BY_NAME_MAPPING)
    List<CharacterView> getActiveCharactersByName(
        @CookieValue(CustomFilterHelper.COOKIE_CHARACTER_ID) String characterId,
        @RequestBody @Valid OneStringParamRequest request
    ) {
        log.info("{} wants to query active characters with name {}", characterId, request.getValue());
        return characterViewQueryService.getActiveCharactersByName(characterId, request.getValue());
    }

    @GetMapping(GET_CHARACTER_ID_MAPPING)
    String getCharacterId(@CookieValue(CustomFilterHelper.COOKIE_CHARACTER_ID) String characterId) {
        return characterId;
    }

    @GetMapping(GET_CHARACTERS_MAPPING)
    List<CharacterView> getCharacters(@CookieValue(value = CustomFilterHelper.COOKIE_USER_ID) String userId) {
        log.info("{} wants to know his character list.", userId);
        return characterViewQueryService.getCharactersByUserId(userId);
    }

    @GetMapping(GET_EQUIPMENTS_OF_CHARACTER)
    List<String> getEquipmentsOfCharacter(
        @CookieValue(CustomFilterHelper.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to know his equipments.", characterId);
        return characterQueryService.getEquipmentsOfCharacter(characterId);
    }

    @GetMapping(GET_MONEY_OF_CHARACTER_MAPPING)
    Integer getMoney(
        @CookieValue(CustomFilterHelper.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to know his money.", characterId);
        return characterQueryService.getMoneyOfCharacter(characterId);
    }

    @PostMapping(IS_CHAR_NAME_EXISTS_MAPPING)
    boolean isCharNameExists(@RequestBody @Valid OneStringParamRequest request) {
        log.info("Someone wants to know if character with name {} is exists.", request);
        return characterNameExistsCache.get(request.getValue()).orElse(true);
    }

    @PutMapping(RENAME_CHARACTER_MAPPING)
    CharacterView renameCharacter(
        @RequestBody @Valid RenameCharacterRequest request,
        @CookieValue(value = CustomFilterHelper.COOKIE_USER_ID) String userId) {
        log.info("{} wants to rename character {}", userId, request);
        SkyXpCharacter character = characterRenameService.renameCharacter(request, userId);
        characterNameExistsCache.invalidate(request.getNewCharacterName());
        log.info("Character renamed successfully.");
        return characterViewConverter.convertDomain(character);
    }

    @PutMapping(SELECT_CHARACTER_MAPPING)
    void selectCharacter(
        @PathVariable("characterId") String characterId,
        @CookieValue(CustomFilterHelper.COOKIE_USER_ID) String userId,
        HttpServletResponse response
    ) {
        log.info("{} selected character {}", userId, characterId);
        characterSelectService.selectCharacter(characterId, userId, response);
    }
}
