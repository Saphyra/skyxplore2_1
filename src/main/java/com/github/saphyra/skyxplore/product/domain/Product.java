package com.github.saphyra.skyxplore.product.domain;

import java.time.OffsetDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Product {
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

    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
}
