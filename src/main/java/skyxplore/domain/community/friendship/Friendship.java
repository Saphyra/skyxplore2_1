package skyxplore.domain.community.friendship;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Friendship {
    private String friendshipId;
    private String characterId;
    private String friendId;
}