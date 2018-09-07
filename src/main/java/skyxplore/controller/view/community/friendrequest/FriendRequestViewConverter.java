package skyxplore.controller.view.community.friendrequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.controller.view.AbstractViewConverter;
import skyxplore.domain.community.friendrequest.FriendRequest;
import skyxplore.service.character.CharacterQueryService;

@Component
@RequiredArgsConstructor
public class FriendRequestViewConverter extends AbstractViewConverter<FriendRequest, FriendRequestView> {
    private final CharacterQueryService characterQueryService;

    @Override
    public FriendRequestView convertDomain(FriendRequest domain) {
        if (domain == null) {
            return null;
        }
        FriendRequestView view = new FriendRequestView();
        view.setCharacterId(domain.getCharacterId());
        view.setFriendRequestId(domain.getFriendRequestId());
        view.setFriendId(domain.getFriendId());
        view.setFriendName(characterQueryService.findByCharacterId(domain.getFriendId()).getCharacterName());
        return view;
    }
}
