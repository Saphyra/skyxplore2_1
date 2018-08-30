package skyxplore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import skyxplore.controller.request.character.CreateLobbyRequest;
import skyxplore.filter.CharacterAuthFilter;
import skyxplore.service.GameFacade;

@RequiredArgsConstructor
@RestController
@Slf4j
//TODO unit test (implementation in progress)
public class GameController {
    private static final String CREATE_LOBBY_MAPPING = "game/createlobby";

    private final GameFacade gameFacade;

    @PostMapping(CREATE_LOBBY_MAPPING)
    public void createLobby(
        @RequestBody CreateLobbyRequest request,
        @CookieValue(CharacterAuthFilter.COOKIE_CHARACTER_ID) String characterId){
        log.info("{} wants to create a lobby with parameters {}", characterId,  request);
        gameFacade.createLobby(request, characterId);
    }
}
