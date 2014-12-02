package com.github.mread.store;

import com.google.gson.Gson;
import redis.clients.jedis.Jedis;

public class SeedData {
    private final Jedis jedis;
    private final Gson gson;

    public SeedData(final Jedis jedis, final Gson gson) {
        this.jedis = jedis;
        this.gson = gson;
    }

    public void setup() {
    }

}