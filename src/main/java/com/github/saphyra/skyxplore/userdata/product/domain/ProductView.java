package com.github.saphyra.skyxplore.userdata.product.domain;

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

    private final Long startTime;
    private final Long endTime;
}