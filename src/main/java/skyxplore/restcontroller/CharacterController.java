package skyxplore.restcontroller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import skyxplore.service.CharacterService;

import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CharacterController {
    private static final String IS_CHAR_NAME_EXISTS_MAPPING = "character/ischarnameexists/{charName}";

    private final CharacterService characterService;

    @GetMapping(IS_CHAR_NAME_EXISTS_MAPPING)
    public boolean isCharNameExists(@PathVariable @NotNull String charName){
        log.info("Someone wants to know if character with name {} is exists.", charName);
        return characterService.isCharNameExists(charName);
    }
}
