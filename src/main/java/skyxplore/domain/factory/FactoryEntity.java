package skyxplore.domain.factory;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "factory")
public class FactoryEntity {
    @Id
    @Column(name = "factory_id", length = 50)
    private String factoryId;

    @Column(name = "character_id", nullable = false)
    private String characterId;

    @Column(name = "materials", nullable = false)
    private String materials;
}
