package com.github.saphyra.skyxplore.product.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductView {
    private String productId;
    private String factoryId;
    private String elementId;
    private Integer amount;
    private Long addedAt;
    private Integer constructionTime;
    private Long startTime;
    private Long endTime;
}