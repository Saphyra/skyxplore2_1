package skyxplore.domain.community.blockedcharacter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import skyxplore.controller.request.community.BlockCharacterRequest;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlockedCharacter {
    private Long blockedCharacterEntityId;
    private String characterId;
    private String blockedCharacterId;

    public BlockedCharacter(BlockCharacterRequest request){
        this.characterId = request.getCharacterId();
        this.blockedCharacterId = request.getBlockedCharacterId();
    }
}
