package skyxplore.domain.community.friendrequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Table(name = "friend_request")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequestEntity {
    @Id
    @Column(name = "friend_request_id", length = 50)
    private String friendRequestId;

    @Column(name = "character_id", length = 50)
    private String characterId;

    @Column(name = "friend_id", length = 50)
    private String friendId;
}
