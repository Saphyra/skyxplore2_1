package skyxplore.domain.product;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "factory")
public class ProductEntity {
    @Id
    @Column(name = "product_id", nullable = false, length = 50)
    private String productId;

    @Column(name = "factory_id", nullable = false)
    private String factoryId;

    @Column(name = "element_id", nullable = false)
    private String elementId;

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "order")
    private Long order;

    @Column(name = "construction_time", nullable = false)
    private Integer constructionTime;

    @Column(name = "start_time")
    private Long startTime;
}
