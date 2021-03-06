package com.github.mread.render;

import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.template.ClasspathTemplateLoader;
import de.neuland.jade4j.template.JadeTemplate;
import spark.ModelAndView;
import spark.TemplateEngine;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

public final class JadeTemplateEngine extends TemplateEngine {
    private final JadeConfiguration config;

    public JadeTemplateEngine() {
        config = new JadeConfiguration();
        config.setTemplateLoader(new ClasspathTemplateLoader());
    }

    @Override
    public String render(final ModelAndView modelAndView) {
        try {
            final JadeTemplate view = config.getTemplate(Paths.get("views", modelAndView.getViewName() + ".jade").toString());
            final Map<String, Object> model = (Map<String, Object>) modelAndView.getModel();
            return config.renderTemplate(view, model);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
