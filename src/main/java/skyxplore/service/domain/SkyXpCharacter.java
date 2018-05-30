package skyxplore.service.domain;

import lombok.Data;

@Data
public class SkyXpCharacter {
    private Long characterId;
    private String characterName;
    private SkyXpUser user;
}
