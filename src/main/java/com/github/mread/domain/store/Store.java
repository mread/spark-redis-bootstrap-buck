package com.github.mread.domain.store;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface Store {
    <T> List<T> all(final String key, final Class<T> clazz);

    <T> Optional<T> findById(final String key, final Class<T> clazz, final String id);

    <T> Optional<T> findBy(final String key, final Class<T> clazz, final Predicate<T> predicate);
}
