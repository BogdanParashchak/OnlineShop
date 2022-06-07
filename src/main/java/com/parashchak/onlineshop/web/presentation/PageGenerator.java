package com.parashchak.onlineshop.web.presentation;

import freemarker.cache.NullCacheStorage;
import freemarker.template.*;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Map;

@Slf4j
public class PageGenerator {

    private final Configuration configuration;

    private PageGenerator(String templatesPath) {
        configuration = new Configuration(Configuration.VERSION_2_3_31);
        configuration.setCacheStorage(new NullCacheStorage());
        configuration.setClassForTemplateLoading(PageGenerator.class, templatesPath);
    }

    public String getPage(String templateName, Map<String, Object> pageData) {
        try (Writer writer = new StringWriter()) {
            Template template = configuration.getTemplate(templateName);
            template.process(pageData, writer);
            log.info("Template " + templateName + " processed");
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException("Unable to generate page", e);
        }
    }

    public String getPage(String templateName) {
        return getPage(templateName, null);
    }
}