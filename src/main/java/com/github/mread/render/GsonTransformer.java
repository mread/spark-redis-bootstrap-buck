package com.github.mread.render;

import com.github.mread.codec.GsonFactory;
import com.google.gson.Gson;
import spark.ResponseTransformer;

public final class GsonTransformer implements ResponseTransformer {
    private final Gson gson = GsonFactory.createGson();

    @Override
    public String render(final Object model) {
        if (model instanceof String) {
            throw new IllegalArgumentException("We shouldn't render String directly - use domain or presentation types for json routes");
        } else {
            return gson.toJson(model);
        }
    }
}
