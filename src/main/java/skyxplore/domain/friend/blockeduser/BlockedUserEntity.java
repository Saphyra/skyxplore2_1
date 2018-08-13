package skyxplore.domain.friend.blockeduser;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@Table(name = "blocked_user")
public class BlockedUserEntity {
    @Id
    @GeneratedValue
    @Column(name = "blocked_user_entity_id")
    private Long blockedUserEntityId;

    @Column(name = "character_id")
    private String characterId;

    @Column(name = "blocked_user_id")
    private String blockedUserId;
}
