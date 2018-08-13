package skyxplore.domain.friend.blockeduser;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Builder
@Table(name = "blocked_user")
@Entity
public class BlockedUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "blocked_user_entity_id")
    private Long blockedUserEntityId;

    @Column(name = "character_id")
    private String characterId;

    @Column(name = "blocked_user_id")
    private String blockedUserId;
}
