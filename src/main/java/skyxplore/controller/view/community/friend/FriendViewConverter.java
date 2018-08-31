package skyxplore.controller.view.community.friend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.controller.view.AbstractViewConverter;
import skyxplore.domain.community.friendship.Friendship;
import skyxplore.service.AccessTokenFacade;
import skyxplore.service.character.CharacterQueryService;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class FriendViewConverter extends AbstractViewConverter<Friendship, FriendView> {
    private final AccessTokenFacade accessTokenFacade;
    private final CharacterQueryService characterQueryService;

    @Override
    public FriendView convertDomain(Friendship domain) {
        FriendView view = new FriendView();
        view.setFriendshipId(domain.getFriendshipId());
        view.setFriendId(domain.getFriendId());
        view.setFriendName(characterQueryService.findByCharacterId(domain.getFriendId()).getCharacterName());
        view.setActive(accessTokenFacade.isCharacterActive(domain.getFriendId()));
        return view;
    }

    public List<FriendView> convertDomain(List<Friendship> domain, String characterId) {
        return domain
            .stream()
            .map(f -> checkIds(f, characterId))
            .map(this::convertDomain)
            .collect(Collectors.toList());
    }

    private Friendship checkIds(Friendship friendship, String characterId) {
        if (friendship.getFriendId().equals(characterId)) {
            swapIds(friendship);
        }
        return friendship;
    }

    private void swapIds(Friendship friendship) {
        String characterId = friendship.getCharacterId();
        friendship.setCharacterId(friendship.getFriendId());
        friendship.setFriendId(characterId);
    }
}
