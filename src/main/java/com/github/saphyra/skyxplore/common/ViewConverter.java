package com.github.saphyra.skyxplore.common;

import java.util.List;

public interface ViewConverter<S, T> {
    T convertDomain(S domain);

    List<T> convertDomain(List<S> domain);
}
