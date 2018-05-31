package skyxplore.service.domain;

import lombok.Data;

import java.util.Calendar;

@Data
public class AccessToken {
    private String accessTokenId;
    private Long userId;
    private Calendar lastAccess;
}