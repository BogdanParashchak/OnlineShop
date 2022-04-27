package com.parashchak.onlineshop.web.presentation;

import freemarker.cache.NullCacheStorage;
import freemarker.template.*;

import java.io.*;
import java.util.Map;


public class PageGenerator {

    private final static String HTML_TEMPLATES_PATH = "/templates";
    private static PageGenerator pageGenerator;

    private final Configuration configuration;

    private PageGenerator() {
        configuration = new Configuration(Configuration.VERSION_2_3_31);
        configuration.setCacheStorage(new NullCacheStorage());
        configuration.setClassForTemplateLoading(PageGenerator.class, HTML_TEMPLATES_PATH);
    }

    private PageGenerator(String templatePath) throws IOException {
        this();
        configuration.setDirectoryForTemplateLoading(new File(templatePath));
    }

    public static PageGenerator instance() {
        if (pageGenerator == null)
            pageGenerator = new PageGenerator();
        return pageGenerator;
    }

    public static PageGenerator instance(String templatePath) throws IOException {
        if (pageGenerator == null)
            pageGenerator = new PageGenerator(templatePath);
        return pageGenerator;
    }


    public String getPage(String templateName, Map<String, Object> pageData) {
        try (Writer writer = new StringWriter()) {
            Template template = configuration.getTemplate(templateName);
            template.process(pageData, writer);
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException("Unable to generate page", e);
        }
    }

    public String getPage(String templateName) {
        return getPage(templateName, null);
    }
}