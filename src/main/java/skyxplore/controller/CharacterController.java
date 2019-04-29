package skyxplore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.github.saphyra.skyxplore.character.cache.CharacterNameCache;
import org.github.saphyra.skyxplore.common.OneStringParamRequest;
import skyxplore.controller.request.character.CreateCharacterRequest;
import skyxplore.controller.request.character.RenameCharacterRequest;
import skyxplore.controller.view.character.CharacterView;
import skyxplore.controller.view.character.CharacterViewConverter;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import skyxplore.service.CharacterFacade;
import org.github.saphyra.skyxplore.common.CookieUtil;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

import static org.github.saphyra.skyxplore.filter.CustomFilterHelper.COOKIE_CHARACTER_ID;
import static org.github.saphyra.skyxplore.filter.CustomFilterHelper.COOKIE_USER_ID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CharacterController {
    private static final String BUY_EQUIPMENTS_MAPPING = "character/equipment";
    private static final String CREATE_CHARACTER_MAPPING = "character";
    private static final String DELETE_CHARACTER_MAPPING = "character/{characterId}";
    private static final String GET_CHARACTERS_MAPPING = "character";
    private static final String GET_EQUIPMENTS_OF_CHARACTER = "character/storage";
    private static final String GET_MONEY_OF_CHARACTER_MAPPING = "character/money";
    private static final String IS_CHAR_NAME_EXISTS_MAPPING = "character/name";
    public static final String RENAME_CHARACTER_MAPPING = "character";
    private static final String SELECT_CHARACTER_MAPPING = "character/{characterId}";

    private final CharacterFacade characterFacade;
    private final CharacterViewConverter characterViewConverter;
    private final CharacterNameCache characterNameCache;
    private final CookieUtil cookieUtil;

    @PostMapping(BUY_EQUIPMENTS_MAPPING)
    void buyEquipments(
        @RequestBody HashMap<String, Integer> items,
        @CookieValue(COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to buy {}", characterId, items.toString());
        characterFacade.buyItems(items, characterId);
        log.info("Items are bought successfully.");
    }

    @PostMapping(CREATE_CHARACTER_MAPPING)
    CharacterView createCharacter(
        @RequestBody @Valid CreateCharacterRequest request,
        @CookieValue(value = COOKIE_USER_ID) String userId
    ) {
        log.info("Creating new character with name {}", request.getCharacterName());
        SkyXpCharacter character = characterFacade.createCharacter(request, userId);
        log.info("Character created successfully.");

        return characterViewConverter.convertDomain(character);
    }

    @DeleteMapping(DELETE_CHARACTER_MAPPING)
    void deleteCharacter(
        @PathVariable("characterId") String characterId,
        @CookieValue(value = COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to deleteById {}", userId, characterId);
        characterFacade.deleteCharacter(characterId, userId);
        log.info("Character {} is deleted.", characterId);
    }

    @GetMapping(GET_CHARACTERS_MAPPING)
    List<CharacterView> getCharacters(@CookieValue(value = COOKIE_USER_ID) String userId) {
        log.info("{} wants to know his character list.", userId);
        return characterViewConverter.convertDomain(characterFacade.getCharactersByUserId(userId));
    }

    @GetMapping(GET_EQUIPMENTS_OF_CHARACTER)
    List<String> getEquipmentsOfCharacter(
        @CookieValue(COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to know his equipments.", characterId);
        return characterFacade.getEquipmentsOfCharacter(characterId);
    }

    @GetMapping(GET_MONEY_OF_CHARACTER_MAPPING)
    Integer getMoney(
        @CookieValue(COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to know his money.", characterId);
        return characterFacade.getMoneyOfCharacter(characterId);
    }

    @PostMapping(IS_CHAR_NAME_EXISTS_MAPPING)
    boolean isCharNameExists(@RequestBody @Valid OneStringParamRequest request) {
        log.info("Someone wants to know if character with name {} is exists.", request);
        return characterNameCache.get(request.getValue()).orElse(true);
    }

    @PutMapping(RENAME_CHARACTER_MAPPING)
    CharacterView renameCharacter(
        @RequestBody @Valid RenameCharacterRequest request,
        @CookieValue(value = COOKIE_USER_ID) String userId) {
        log.info("{} wants to rename character {}", userId, request);
        SkyXpCharacter character = characterFacade.renameCharacter(request, userId);
        characterNameCache.invalidate(request.getNewCharacterName());
        log.info("Character renamed successfully.");
        return characterViewConverter.convertDomain(character);
    }

    @PutMapping(SELECT_CHARACTER_MAPPING)
    void selectCharacter(
        @PathVariable("characterId") String characterId,
        @CookieValue(COOKIE_USER_ID) String userId,
        HttpServletResponse response
    ) {
        log.info("{} selected character {}", userId, characterId);
        characterFacade.selectCharacter(characterId, userId);
        cookieUtil.setCookie(response, COOKIE_CHARACTER_ID, characterId);
    }
}
