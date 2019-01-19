package skyxplore.domain.accesstoken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkyXpAccessToken {
    private String accessTokenId;
    private String userId;
    private OffsetDateTime lastAccess;
    private String characterId;
}
