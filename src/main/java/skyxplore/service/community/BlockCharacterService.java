package skyxplore.service.community;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.request.BlockCharacterRequest;
import skyxplore.dataaccess.db.BlockedCharacterDao;

@Service
@Slf4j
@RequiredArgsConstructor
public class BlockCharacterService {
    private final BlockedCharacterDao blockedCharacterDao;

    public void blockCharacter(BlockCharacterRequest request, String userId) {
    }
}
