package com.github.mread.routes;


import com.github.mread.routes.bind.Binder;
import com.github.mread.routes.bind.Router;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import static java.util.Collections.emptyMap;

public class HomeRoutes implements Router {

    @Override
    public void configure(final Binder binder) {
        binder.get("/", this::home);
    }

    private ModelAndView home(final Request request, final Response response) {
        return new ModelAndView(emptyMap(), "home");
    }
}
