package com.github.saphyra.skyxplore.factory.domain;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddToQueueRequest {
    @NotNull
    private String elementId;

    @NotNull
    @Min(1)
    private Integer amount;
}
