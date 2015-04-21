package com.github.mread.codec;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class PathTypeAdapter extends TypeAdapter<Path> {
    @Override
    public void write(final JsonWriter out, final Path value) throws IOException {
        out.value(value.toString());
    }

    @Override
    public Path read(final JsonReader in) throws IOException {
        return Paths.get(in.nextString());
    }
}
