package skyxplore.dataaccess.db;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class FriendshipDao {
//TODO implement repository and domain

    public List<String> getByCharacterIdAndFriendId(String characterId, String friendId) {
        return Collections.emptyList();
    }
}
