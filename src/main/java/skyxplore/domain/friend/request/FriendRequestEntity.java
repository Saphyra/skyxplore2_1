package skyxplore.domain.friend.request;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Builder
@Table(name = "friend_request")
@Entity
public class FriendRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "friend_request_id")
    private Long friendRequestId;

    @Column(name = "character_id")
    private String characterId;

    @Column(name = "friend_id")
    private String friendId;
}
