package skyxplore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import skyxplore.controller.request.character.CreateLobbyRequest;
import skyxplore.service.LobbyFacade;

import javax.validation.Valid;

import static org.github.saphyra.skyxplore.filter.CustomFilterHelper.COOKIE_CHARACTER_ID;

@RequiredArgsConstructor
@RestController
@Slf4j
//TODO unit test (implementation in progress)
public class LobbyController {
    private static final String CREATE_LOBBY_MAPPING = "lobby";

    private final LobbyFacade lobbyFacade;

    @PutMapping(CREATE_LOBBY_MAPPING)
    public void createLobby(
        @RequestBody @Valid CreateLobbyRequest request,
        @CookieValue(COOKIE_CHARACTER_ID) String characterId) {
        log.info("{} wants to create a lobby with parameters {}", characterId, request);
        lobbyFacade.createLobby(request, characterId);
    }
}
