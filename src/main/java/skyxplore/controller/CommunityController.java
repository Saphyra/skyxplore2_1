package skyxplore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import skyxplore.controller.request.AddFriendRequest;
import skyxplore.filter.AuthFilter;
import skyxplore.service.CommunityFacade;

@SuppressWarnings("unused")
@RestController
@Slf4j
@RequiredArgsConstructor
public class CommunityController {
    private static final String ADD_FRIEND_MAPPING = "friend/add";

    private final CommunityFacade communityFacade;

    @PostMapping(ADD_FRIEND_MAPPING)
    public void addFriend(@RequestBody AddFriendRequest request, @CookieValue(AuthFilter.COOKIE_USER_ID) String userId) {
        log.info("{} wants to add {} as a friend.", request.getCharacterId(), request.getFriendId());
        communityFacade.addFriendRequest(request, userId);
    }
}
