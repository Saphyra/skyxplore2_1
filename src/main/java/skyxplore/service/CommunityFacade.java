package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.controller.request.AddFriendRequest;
import skyxplore.service.community.FriendService;

@Component
@Slf4j
@RequiredArgsConstructor
public class CommunityFacade {
    private final FriendService friendService;

    public void addFriendRequest(AddFriendRequest request, String userId) {
        friendService.addFriendRequest(request, userId);
    }
}
