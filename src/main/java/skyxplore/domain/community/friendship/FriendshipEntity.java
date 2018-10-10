package skyxplore.domain.community.friendship;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "friendship")
@Data
public class FriendshipEntity {
    @Id
    @Column(name = "friendship_id", length = 50)
    private String friendshipId;

    @Column(name = "character_id", length = 50)
    private String characterId;

    @Column(name = "friend_id", length = 50)
    private String friendId;
}
