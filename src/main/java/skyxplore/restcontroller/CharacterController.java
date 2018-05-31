package skyxplore.restcontroller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import skyxplore.filter.AuthFilter;
import skyxplore.restcontroller.request.CharacterDeleteRequest;
import skyxplore.restcontroller.request.CreateCharacterRequest;
import skyxplore.restcontroller.view.CharacterView;
import skyxplore.restcontroller.view.converter.CharacterViewConverter;
import skyxplore.service.CharacterService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CharacterController {
    private static final String CREATE_CHARACTER_MAPPING = "character";
    private static final String DELETE_CHARACTER_MAPPING = "character";
    private static final String GET_CHARACTERS_MAPPING = "character/characters";
    private static final String IS_CHAR_NAME_EXISTS_MAPPING = "character/ischarnameexists/{charName}";

    private final CharacterService characterService;
    private final CharacterViewConverter characterViewConverter;

    @DeleteMapping(DELETE_CHARACTER_MAPPING)
    public void deleteCharacter(@RequestBody @NotNull CharacterDeleteRequest request, @CookieValue(value = AuthFilter.COOKIE_USER_ID) Long userId){
        log.info("{} wants to delete {}", userId, request.getCharacterId());
        characterService.deleteCharacter(request, userId);
        log.info("Character {} is deleted.", request.getCharacterId());
    }

    @GetMapping(IS_CHAR_NAME_EXISTS_MAPPING)
    public boolean isCharNameExists(@PathVariable @NotNull String charName){
        log.info("Someone wants to know if character with name {} is exists.", charName);
        return characterService.isCharNameExists(charName);
    }

    @GetMapping(GET_CHARACTERS_MAPPING)
    public List<CharacterView> getCharacters(@CookieValue(value = AuthFilter.COOKIE_USER_ID) Long userId){
        log.info("{} wants to know his character list.", userId);
        return characterViewConverter.convertDomain(characterService.getCharactersByUserId(userId));
    }

    @PutMapping(CREATE_CHARACTER_MAPPING)
    public void createCharacter(@RequestBody @Valid CreateCharacterRequest request, @CookieValue(value = AuthFilter.COOKIE_USER_ID) Long userId){
        log.info("Creating new character with name {}", request.getCharacterName());
        characterService.createCharacter(request, userId);
    }
}
