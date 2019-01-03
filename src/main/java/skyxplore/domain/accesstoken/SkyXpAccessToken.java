package skyxplore.domain.accesstoken;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class SkyXpAccessToken {
    private String accessTokenId;
    private String userId;
    private OffsetDateTime lastAccess;
    private String characterId;
}
