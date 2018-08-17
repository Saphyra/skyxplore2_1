package skyxplore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import skyxplore.controller.request.CreateLobbyRequest;
import skyxplore.filter.AuthFilter;
import skyxplore.service.GameFacade;

@RequiredArgsConstructor
@RestController
@Slf4j
//TODO unit test (implementation in progress)
public class GameController {
    private static final String CREATE_LOBBY_MAPPING = "game/createlobby/{characterId}";

    private final GameFacade gameFacade;

    @PostMapping(CREATE_LOBBY_MAPPING)
    public void createLobby(@RequestBody CreateLobbyRequest request, @PathVariable(value = "characterId") String characterId, @CookieValue(AuthFilter.COOKIE_USER_ID) String userId){
        log.info("{} wants to create a lobby with parameters {} for character {}", userId, request, characterId);
        gameFacade.createLobby(request, userId, characterId);
    }
}
