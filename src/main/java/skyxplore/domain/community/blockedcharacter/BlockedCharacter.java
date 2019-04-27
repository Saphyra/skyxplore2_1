package skyxplore.domain.community.blockedcharacter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlockedCharacter {
    private Long blockedCharacterEntityId;
    private String characterId;
    private String blockedCharacterId;

    public BlockedCharacter(String characterId, String blockedCharacterId) {
        this.characterId = characterId;
        this.blockedCharacterId = blockedCharacterId;
    }
}
