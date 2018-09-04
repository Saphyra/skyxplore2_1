package skyxplore.domain.community.friendship;

import javax.persistence.Entity;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "friendship")
@Data
public class FriendshipEntity {
    @Id
    @Column(name = "friendship_id")
    private String friendshipId;

    @Column(name = "character_id")
    private String characterId;

    @Column(name = "friend_id")
    private String friendId;
}
