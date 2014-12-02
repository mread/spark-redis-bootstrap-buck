package com.github.mread.config;

import spark.Spark;
import spark.TemplateViewRoute;

import java.nio.file.Paths;

public class Binder {
    private final JadeTemplateEngine jade = new JadeTemplateEngine();

    private String rootPath;

    public Binder(final String rootPath) {
        this.rootPath = rootPath;
    }

    public void get(final String path, final TemplateViewRoute route) {
        final String pathAsString = Paths.get(rootPath, path).toString();
        Spark.get(pathAsString, route, jade);
        System.out.printf("Jade Route: GET  %s%n", pathAsString);
    }

    public void get(final String path, final JsonRoute route) {
        final String pathAsString = Paths.get(rootPath, path).toString();
        Spark.get(pathAsString, route, new GsonTransformer());
        System.out.printf("Json Route: GET  %s%n", pathAsString);
    }

    public void post(final String path, final JsonRoute route) {
        final String pathAsString = Paths.get(rootPath, path).toString();
        Spark.post(pathAsString, route, new GsonTransformer());
        System.out.printf("Json Route: POST %s%n", pathAsString);
    }
}
