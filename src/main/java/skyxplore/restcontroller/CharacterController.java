package skyxplore.restcontroller;

import com.google.common.cache.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import skyxplore.filter.AuthFilter;
import skyxplore.restcontroller.request.CharacterDeleteRequest;
import skyxplore.restcontroller.request.CreateCharacterRequest;
import skyxplore.restcontroller.request.RenameCharacterRequest;
import skyxplore.restcontroller.view.CharacterView;
import skyxplore.restcontroller.view.converter.CharacterViewConverter;
import skyxplore.service.CharacterService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CharacterController {
    private static final String BUY_EQUIPMENTS_MAPPING = "character/equipment/{characterId}";
    private static final String CREATE_CHARACTER_MAPPING = "character";
    private static final String DELETE_CHARACTER_MAPPING = "character";
    private static final String GET_CHARACTERS_MAPPING = "character/characters";
    private static final String GET_MONEY_OF_CHARACTER_MAPPING = "character/money/{characterId}";
    private static final String IS_CHAR_NAME_EXISTS_MAPPING = "character/ischarnameexists/{charName}";
    private static final String RENAME_CHARACTER_MAPPING = "character/rename";

    private final CharacterService characterService;
    private final CharacterViewConverter characterViewConverter;
    private final Cache<String, Boolean> characterNameCache;

    @PutMapping(BUY_EQUIPMENTS_MAPPING)
    public void buyEquipments(
            @RequestBody HashMap<String, Integer> items,
            @PathVariable(name = "characterId") String characterId,
            @CookieValue(value = AuthFilter.COOKIE_USER_ID) String userId){
        log.info("{} wants to buy {} for character {}", userId, items.toString(), characterId);
        characterService.buyItems(items, characterId, userId);
        log.info("Items are bought successfully.");
    }

    @PutMapping(CREATE_CHARACTER_MAPPING)
    public void createCharacter(@RequestBody @Valid CreateCharacterRequest request, @CookieValue(value = AuthFilter.COOKIE_USER_ID) String userId){
        log.info("Creating new character with name {}", request.getCharacterName());
        characterService.createCharacter(request, userId);
        log.info("Character created successfully.");
        characterNameCache.invalidate(request.getCharacterName());
    }

    @DeleteMapping(DELETE_CHARACTER_MAPPING)
    public void deleteCharacter(@RequestBody @NotNull CharacterDeleteRequest request, @CookieValue(value = AuthFilter.COOKIE_USER_ID) String userId){
        log.info("{} wants to delete {}", userId, request.getCharacterId());
        characterService.deleteCharacter(request, userId);
        log.info("Character {} is deleted.", request.getCharacterId());
    }

    @GetMapping(IS_CHAR_NAME_EXISTS_MAPPING)
    public boolean isCharNameExists(@PathVariable @NotNull String charName) throws ExecutionException {
        log.info("Someone wants to know if character with name {} is exists.", charName);
        return characterNameCache.get(charName);
    }

    @GetMapping(GET_CHARACTERS_MAPPING)
    public List<CharacterView> getCharacters(@CookieValue(value = AuthFilter.COOKIE_USER_ID) String userId){
        log.info("{} wants to know his character list.", userId);
        return characterViewConverter.convertDomain(characterService.getCharactersByUserId(userId));
    }

    @GetMapping(GET_MONEY_OF_CHARACTER_MAPPING)
    public Integer getMoney(@PathVariable(name = "characterId") @NotNull String characterId, @CookieValue(value = AuthFilter.COOKIE_USER_ID) String userId){
        log.info("{} Queriing money of character {}", userId, characterId);
        return characterService.getMoneyOfCharacter(userId, characterId);
    }

    @PostMapping(RENAME_CHARACTER_MAPPING)
    public void renameCharacter(@RequestBody @Valid RenameCharacterRequest request, @CookieValue(value = AuthFilter.COOKIE_USER_ID) String userId){
        log.info("{} wants to rename character {}", userId, request);
        characterService.renameCharacter(request, userId);
        characterNameCache.invalidate(request.getNewCharacterName());
        log.info("Character renamed successfully.");
    }
}
