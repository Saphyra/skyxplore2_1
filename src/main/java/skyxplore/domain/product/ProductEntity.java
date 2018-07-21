package skyxplore.domain.product;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "product")
@SequenceGenerator(name = "seq_gen")
public class ProductEntity {
    @Id
    @Column(name = "product_id", nullable = false, length = 50)
    private String productId;

    @Column(name = "factory_id", nullable = false)
    private String factoryId;

    @Column(name = "element_id", nullable = false)
    private String elementId;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "added_at")
    private Long addedAt;

    @Column(name = "construction_time", nullable = false)
    private Integer constructionTime;

    @Column(name = "start_time")
    private Long startTime;

    @Column(name = "end_time")
    private Long endTime;
}
