package skyxplore.restcontroller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import skyxplore.filter.AuthFilter;
import skyxplore.restcontroller.request.CreateCharacterRequest;
import skyxplore.service.CharacterService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CharacterController {
    private static final String IS_CHAR_NAME_EXISTS_MAPPING = "character/ischarnameexists/{charName}";
    private static final String CREATE_CHARACTER_MAPPING = "character/createcharacter";
    private final CharacterService characterService;

    @GetMapping(IS_CHAR_NAME_EXISTS_MAPPING)
    public boolean isCharNameExists(@PathVariable @NotNull String charName){
        log.info("Someone wants to know if character with name {} is exists.", charName);
        return characterService.isCharNameExists(charName);
    }

    @PutMapping(CREATE_CHARACTER_MAPPING)
    public void createCharacter(@RequestBody @Valid CreateCharacterRequest request, @CookieValue(value = AuthFilter.COOKIE_USER_ID) Long userId){
        log.info("Creating new character with name {}", request.getCharacterName());
        characterService.createCharacter(request, userId);
    }
}
