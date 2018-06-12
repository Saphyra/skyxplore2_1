package skyxplore.dataaccess.db.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "skyxp_character")
@Data
public class CharacterEntity {

    @Id
    @Column(name = "character_id", length = 50)
    private String characterId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "ship_id", nullable = false)
    private String shipId;

    @Column(name = "character_name", nullable = false)
    private String characterName;

    @Column(name = "money", nullable = false)
    private Integer money;

    @Column(name = "equipments", nullable = false)
    private ArrayList<String> equipments;
}
