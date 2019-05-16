package com.github.saphyra.skyxplore.product.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class ProductView {
    @NonNull
    private final String productId;

    @NonNull
    private final String factoryId;

    @NonNull
    private final String elementId;

    @NonNull
    private final Integer amount;

    @NonNull
    private final Long addedAt;

    @NonNull
    private final Integer constructionTime;

    @NonNull
    private final Long startTime;

    @NonNull
    private final Long endTime;
}