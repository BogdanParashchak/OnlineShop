package com.parashchak.onlineshop.presentation;

import freemarker.template.*;

import java.io.*;
import java.util.Map;


public class PageGenerator {

    private static PageGenerator pageGenerator;

    private String htmlTemplatesPath = "src/main/resources";
    private final Configuration configuration;

    void setHtmlTemplatesPath(String htmlTemplatesPath) {
        this.htmlTemplatesPath = htmlTemplatesPath;
    }

    private PageGenerator() {
        configuration = new Configuration(Configuration.VERSION_2_3_31);
        configuration.setTemplateUpdateDelayMilliseconds(0);
    }

    public static PageGenerator instance() {
        if (pageGenerator == null)
            pageGenerator = new PageGenerator();
        return pageGenerator;
    }

    public String getPage(String templateName, Map<String, Object> pageData) {
        try (Writer writer = new StringWriter()) {
            configuration.setDirectoryForTemplateLoading(new File(htmlTemplatesPath));
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