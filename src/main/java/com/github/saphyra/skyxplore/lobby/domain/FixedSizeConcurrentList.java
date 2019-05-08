package com.github.saphyra.skyxplore.lobby.domain;

import java.util.Vector;

import lombok.Getter;

public class FixedSizeConcurrentList<T> extends Vector<T> {

    @Getter
    private final int maxSize;
    private final Vector<T> delegate = new Vector<>();

    public FixedSizeConcurrentList(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public boolean add(T element) {
        if (delegate.size() == maxSize) {
            throw new ArrayIndexOutOfBoundsException("List has reached max size: " + maxSize);
        }
        return delegate.add(element);
    }
}
