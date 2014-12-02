package com.github.mread.config;

import com.google.gson.Gson;
import spark.ResponseTransformer;

public class GsonTransformer implements ResponseTransformer {
    private Gson gson = new Gson();

    @Override
    public String render(Object model) {
        if (model instanceof String) {
            return (String) model;
        } else {
            return gson.toJson(model);
        }
    }

}
