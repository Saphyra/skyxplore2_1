package skyxplore.service.community;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.controller.request.AddFriendRequest;
import skyxplore.dataaccess.db.FriendDao;

@Service
@Slf4j
@RequiredArgsConstructor
public class FriendService {
    private final FriendDao friendDao;

    public void addFriendRequest(AddFriendRequest request, String userId) {
    }
}
