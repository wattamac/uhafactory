package org.uhafactory.jpa;

import java.io.Serializable;

public interface JPAId<T extends Serializable> {
    T getId();

    T createId(Long sequence);
}

