package com.github.mread.routes.bind;


import com.github.mread.render.GsonTransformer;
import com.github.mread.render.JadeTemplateEngine;
import org.apache.log4j.Logger;
import spark.Spark;
import spark.TemplateViewRoute;

import java.nio.file.Paths;

public final class Binder {
    private static final Logger LOGGER = Logger.getLogger(Binder.class);

    private final JadeTemplateEngine jade = new JadeTemplateEngine();
    private final String rootPath;

    public Binder(final String rootPath) {
        this.rootPath = rootPath;
    }

    public void get(final String path, final TemplateViewRoute route) {
        final String pathAsString = Paths.get(rootPath, path).toString();
        Spark.get(pathAsString, route, jade);
        LOGGER.info(String.format("Jade Route: GET  %s", pathAsString));
    }

    public void get(final String path, final JsonRoute route) {
        final String pathAsString = Paths.get(rootPath, path).toString();
        Spark.get(pathAsString, route, new GsonTransformer());
        LOGGER.info(String.format("Json Route: GET  %s", pathAsString));
    }

    public void postNoAuth(final String path, final JsonRoute route) {
        final String pathAsString = Paths.get(rootPath, path).toString();
        Spark.post(pathAsString, route, new GsonTransformer());
        LOGGER.info(String.format("Json Route: POST %s", pathAsString));
    }

    public void post(final String path, final JsonRoute route) {
        postNoAuth(path, route);
//        requireAuthenticationWithoutRedirect(path);
    }

/*
    public void requireAuthenticationWithoutRedirect(final String path)
    {
        final String pathAsString = Paths.get(rootPath, path).toString();
        Spark.before(pathAsString, (request, response) -> {
            if (!SessionSecurity.isAuthenticated(request.session()))
            {
                throw new AuthenticationException("Authentication is required for " + request.pathInfo());
            }
        });
    }

    public void requireAuthentication(final String path)
    {
        final String pathAsString = Paths.get(rootPath, path).toString();
        Spark.before(pathAsString, (request, response) -> {
            if (!SessionSecurity.isAuthenticated(request.session()))
            {
                response.redirect("/ui/login");
            }
        });
    }
*/

/*
    private TemplateViewRoute setupCommonModel(final TemplateViewRoute route)
    {
        return (Request request, Response response) -> {
            final ModelAndView modelAndView = route.handle(request, response);
            final Map<String, Object> model = new HashMap<>();
            if (SessionSecurity.isAuthenticated(request.session()))
            {
                model.put("username", SessionSecurity.getPrincipal(request.session()).getUsername());
            }
            ((Map<String, ?>)modelAndView.getModel()).forEach(model::put);
            return new ModelAndView(model, modelAndView.getViewName());
        };
    }
*/
}
