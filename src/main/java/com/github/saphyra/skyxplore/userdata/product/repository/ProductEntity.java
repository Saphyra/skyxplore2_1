package com.github.saphyra.skyxplore.userdata.product.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "product")
@SequenceGenerator(name = "seq_gen")
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ProductEntity {
    @Id
    @Column(name = "product_id", nullable = false, length = 50)
    private String productId;

    @Column(name = "factory_id", nullable = false)
    private String factoryId;

    @Column(name = "element_id", nullable = false)
    private String elementId;

    @Column(name = "amount", nullable = false)
    private String amount;

    @Column(name = "added_at", nullable = false)
    private Long addedAt;

    @Column(name = "construction_time", nullable = false)
    private String constructionTime;

    @Column(name = "start_time")
    private Long startTime;

    @Column(name = "end_time")
    private Long endTime;
}
