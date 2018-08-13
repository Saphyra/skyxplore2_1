package skyxplore.domain.friend.request;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@Table(name = "friend_request")
public class FriendRequestEntity {
    @Id
    @GeneratedValue
    @Column(name = "friend_request_id")
    private Long friendRequestId;

    @Column(name = "character_id")
    private String characterId;

    @Column(name = "friend_id")
    private String friendId;
}
