package com.github.mread.store;


import com.github.mread.domain.store.Store;
import com.google.gson.Gson;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;

public class RedisStore implements Store {
    private final Jedis jedis;
    private final Gson gson = new Gson();

    public RedisStore() {
        jedis = new Jedis("localhost");
    }

    public void maybeSetupData() {
        System.out.printf("We say: PING, redis says: %s%n", jedis.ping());
        jedis.flushAll();
        new SeedData(jedis, gson).setup();
    }

    @Override
    public <T> List<T> all(final String key, final Class<T> clazz) {
        return fetchAllFromJson(key, clazz).collect(Collectors.toList());
    }

    @Override
    public <T> Optional<T> findById(final String key, final Class<T> clazz, final String id) {
        return ofNullable(gson.fromJson(jedis.hget(key, id), clazz));
    }

    @Override
    public <T> Optional<T> findBy(final String key, final Class<T> clazz, final Predicate<T> predicate) {
        return fetchAllFromJson(key, clazz).filter(predicate).findFirst();
    }

    private <T> Stream<T> fetchAllFromJson(final String key, final Class<T> clazz) {
        return jedis.hgetAll(key).values().stream().map(s -> gson.fromJson(s, clazz));
    }

}
