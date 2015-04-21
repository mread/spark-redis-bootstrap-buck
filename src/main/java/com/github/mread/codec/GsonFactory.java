package com.github.mread.codec;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.nio.file.Path;

public final class GsonFactory {
    private GsonFactory() {
    }

    // note that we happen to use the same codec for json presentation and for model persistence
    // todo: don't do that anymore and put them in appropriate modules. do we still need an adapter for Path?
    public static Gson createGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Path.class, new PathTypeAdapter().nullSafe())
                .create();
    }
}
