package skyxplore.domain.friend.blockeduser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Table(name = "blocked_character")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BlockedCharacterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "blocked_character_entity_id")
    private Long blockedCharacterEntityId;

    @Column(name = "character_id")
    private String characterId;

    @Column(name = "blocked_character_id")
    private String blockedCharacterId;
}
