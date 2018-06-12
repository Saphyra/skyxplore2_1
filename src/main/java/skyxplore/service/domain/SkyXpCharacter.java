package skyxplore.service.domain;

import lombok.Data;

import java.util.ArrayList;

@Data
public class SkyXpCharacter {
    private String characterId;
    private String characterName;
    private String userId;
    private String shipId;
    private Integer money;
    private ArrayList<String> equipments;
}
