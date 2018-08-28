package skyxplore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import skyxplore.controller.view.character.CharacterView;
import skyxplore.controller.view.character.CharacterViewConverter;
import skyxplore.filter.AuthFilter;
import skyxplore.service.MailFacade;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
//TODO unit test
public class MailController {
    private static final String GET_ADDRESSEES_MAPPING = "mail/addressee/{characterId}/{name}";

    private final CharacterViewConverter characterViewConverter;
    private final MailFacade mailFacade;

    @GetMapping(GET_ADDRESSEES_MAPPING)
    public List<CharacterView> getAddressees(
        @PathVariable("characterId") String characterId,
        @PathVariable("name") String name,
        @CookieValue(AuthFilter.COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to know his possible addressees", characterId);
        return characterViewConverter.convertDomain(mailFacade.getAddressees(characterId, userId, name));
    }
}
