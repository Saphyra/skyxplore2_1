package skyxplore.domain.accesstoken;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccessToken {
    private String accessTokenId;
    private String userId;
    private LocalDateTime lastAccess;
    private String characterId;
}
