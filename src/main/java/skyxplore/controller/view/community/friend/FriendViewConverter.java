package skyxplore.controller.view.community.friend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.controller.view.AbstractViewConverter;
import skyxplore.domain.community.friendship.Friendship;
import skyxplore.service.character.CharacterQueryService;

@Component
@RequiredArgsConstructor
//TODO unit test
public class FriendViewConverter extends AbstractViewConverter<Friendship, FriendView> {
    private final CharacterQueryService characterQueryService;

    @Override
    public FriendView convertDomain(Friendship domain) {
        FriendView view = new FriendView();
        view.setFriendshipId(domain.getFriendshipId());
        view.setFriendId(domain.getFriendId());
        view.setFriendName(characterQueryService.findByCharacterId(domain.getFriendId()).getCharacterName());
        return view;
    }
}
