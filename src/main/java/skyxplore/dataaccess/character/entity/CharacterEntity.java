package skyxplore.dataaccess.character.entity;

import lombok.Data;
import skyxplore.dataaccess.user.entity.UserEntity;

import javax.persistence.*;

@Entity
@Table(name = "skyxp_character")
@Data
public class CharacterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "character_id")
    private Long characterId;

    @Column(name = "character_name", nullable = false)
    private String characterName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
