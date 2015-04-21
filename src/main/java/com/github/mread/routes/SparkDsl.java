package com.github.mread.routes;

import com.github.mread.routes.bind.Binder;
import com.github.mread.routes.bind.Router;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import spark.Spark;

import java.util.Arrays;

public final class SparkDsl {
    public static void use(final String rootPath, final Router router) {
        router.configure(new Binder(rootPath));
    }

    public static void redirect(final String path, final String target) {
        Spark.get(path, (request, response) -> {
            response.redirect(target);
            return "";
        });
    }

    @SafeVarargs
    public static void internalServerError(final Class<? extends Exception>... errorTypes) {
        mapExceptions(500, errorTypes);
    }

    @SafeVarargs
    public static void badRequest(final Class<? extends Exception>... errorTypes) {
        mapExceptions(400, errorTypes);
    }

    @SafeVarargs
    public static void forbidden(final Class<? extends Exception>... errorTypes) {
        mapExceptions(403, errorTypes);
    }

    @SafeVarargs
    private static void mapExceptions(final int statusCode, final Class<? extends Exception>... errorTypes) {
        // todo: consider better response body - via template?
        Arrays.stream(errorTypes).forEach(errorType -> {
            Spark.exception(errorType, (e, request, response) -> {
                response.status(statusCode);
                final boolean isJsonRequest = request.headers("Accept").contains("application/json");
                response.body(isJsonRequest ? getJsonBody(e) : e.getMessage());
            });
        });
    }

    private static String getJsonBody(final Exception e) {
        JsonObject error = new JsonObject();
        error.addProperty("errorMessage", e.getMessage());
        return new Gson().toJson(error);
    }
}
