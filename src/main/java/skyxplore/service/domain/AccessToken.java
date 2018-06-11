package skyxplore.service.domain;

import lombok.Data;

import java.util.Calendar;

@Data
public class AccessToken {
    private String accessTokenId;
    private String userId;
    private Calendar lastAccess;
}
