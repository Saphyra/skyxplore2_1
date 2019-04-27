package skyxplore.service.game;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.controller.request.character.CreateLobbyRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class LobbyCreatorService {

    public void createLobby(CreateLobbyRequest request, String characterId) {
        //TODO implement
        throw new UnsupportedOperationException();
    }
}
