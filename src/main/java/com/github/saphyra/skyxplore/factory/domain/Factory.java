package com.github.saphyra.skyxplore.factory.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Factory {
    private String factoryId;
    private String characterId;
    private Materials materials;
}
