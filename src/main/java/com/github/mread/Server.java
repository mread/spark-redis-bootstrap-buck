package com.github.mread;


import com.github.mread.domain.store.Store;
import com.github.mread.routes.HomeRoutes;
import com.github.mread.store.RedisStore;

import static com.github.mread.routes.SparkDsl.use;
import static spark.SparkBase.staticFileLocation;

public class Server {
    public static void main(String[] args) {
        Store store = configureRedisStore();
        configureWeb(store);
    }

    private static void configureWeb(final Store store) {
        staticFileLocation("public");
        use("/", new HomeRoutes());
    }

    private static Store configureRedisStore() {
        final RedisStore redisStore = new RedisStore();
        redisStore.maybeSetupData();
        return redisStore;
    }

}